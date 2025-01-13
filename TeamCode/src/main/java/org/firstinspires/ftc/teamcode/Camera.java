package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.openftc.easyopencv.*;

import java.util.ArrayList;
import java.util.List;

public class Camera {
    private final OpenCvPipeline pipeline = new RectPipeline();
    private OpenCvCamera camera;
    private volatile RotatedRect result = null;
    // Factor to estimate a polygon with - shouldn't be too low because noise will impact
    // the image more, and shouldn't be too high, or otherwise detail is lost
    private final double APPROX_FACTOR = 0.06;
    // Makes sure that the rectangle is large enough and that it isn't noise
    private final double MIN_RECT = 1000;
    // How many pixels should be blended together
    private final int GAUSSIAN_BLUR = 5;
    // Used for camera streaming - make sure this matches the camera's resolution
    private final int CAMERA_WIDTH = 320;
    private final int CAMERA_HEIGHT = 240;
    // HSV Bounds for red, yellow, and blue
    private final Scalar LOWER_RED = new Scalar(0, 100, 100);
    private final Scalar UPPER_RED = new Scalar(10, 255, 255);
    private final Scalar LOWER_YELLOW = new Scalar(20, 100, 100);
    private final Scalar UPPER_YELLOW = new Scalar(30, 255, 255);
    private final Scalar LOWER_BLUE = new Scalar(100, 100, 100);
    private final Scalar UPPER_BLUE = new Scalar(130, 255, 255);

    // OpenCV image processing
    class RectPipeline extends OpenCvPipeline {
        private Mat hsv = new Mat();
        private Mat blurredImage = new Mat();
        private Mat mask = new Mat();
        private Mat hierarchy = new Mat();
        private MatOfPoint2f approx = new MatOfPoint2f();
        private MatOfPoint2f contour2f = new MatOfPoint2f();

        @Override
        public Mat processFrame(Mat input) {
            result = null;

            // Convert to HSV
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            // Reduce noise
            Imgproc.GaussianBlur(hsv, blurredImage, new Size(GAUSSIAN_BLUR, GAUSSIAN_BLUR), 0);

            // Detect and process each color
            processColor(blurredImage, LOWER_RED, UPPER_RED, new Scalar(255, 0, 0), input);
            processColor(blurredImage, LOWER_YELLOW, UPPER_YELLOW, new Scalar(0, 255, 255), input);
            processColor(blurredImage, LOWER_BLUE, UPPER_BLUE, new Scalar(0, 0, 255), input);

            return input;
        }

        private void processColor(Mat hsvImage, Scalar lower, Scalar upper, Scalar drawColor, Mat input) {
            // Create mask for color
            Core.inRange(hsvImage, lower, upper, mask);

            // Find contours
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            for (MatOfPoint contour : contours) {
                // Approximate polygon
                contour2f.fromArray(contour.toArray());
                double epsilon = APPROX_FACTOR * Imgproc.arcLength(contour2f, true);
                Imgproc.approxPolyDP(contour2f, approx, epsilon, true);

                // Calculate area
                Moments moments = Imgproc.moments(contour);
                double area = moments.get_m00();

                if (area >= MIN_RECT) {
                    // Draw polygon on the frame
                    List<MatOfPoint> polygon = new ArrayList<MatOfPoint>() {{
                        add(new MatOfPoint(approx.toArray()));
                    }};
                    Imgproc.polylines(input, polygon, true, drawColor, 4);

                    // Store the rotated rectangle
                    RotatedRect next = Imgproc.minAreaRect(contour2f);
                    if (result == null || next.size.area() > result.size.area()) result = next;
                }
            }
        }
    }

    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("send help (error code): ", errorCode);
                telemetry.update();
            }
        });
        camera.setPipeline(pipeline);
    }

    public OpenCvCamera getCamera() {
        return camera;
    }

    public RotatedRect getResult() {
        return result;
    }
}