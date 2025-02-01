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
    private OpenCvCamera camera;
    private RotatedRect result = null;
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
    private final Scalar LOWER_RED = new Scalar(0, 80, 25);
    private final Scalar UPPER_RED = new Scalar(15, 255, 255);
    private final Scalar LOWER_YELLOW = new Scalar(20, 80, 25);
    private final Scalar UPPER_YELLOW = new Scalar(30, 255, 255);
    private final Scalar LOWER_BLUE = new Scalar(100, 80, 25);
    private final Scalar UPPER_BLUE = new Scalar(130, 255, 255);
    double angle = -1;

    // OpenCV image processing
    class RectPipeline extends OpenCvPipeline {
        private Mat hsv = new Mat();
        private Mat blurredImage = new Mat();
        private Mat blurredImage2 = new Mat();
        private Mat mask = new Mat();
        private Mat hierarchy = new Mat();
        private MatOfPoint2f approx = new MatOfPoint2f();
        private MatOfPoint2f contour2f = new MatOfPoint2f();
        private final Telemetry telemetry;

        public RectPipeline(Telemetry tele) {
            telemetry = tele;
        }

        @Override
        public Mat processFrame(Mat input) {
            synchronized (Camera.this) {
                result = null;
                angle = -1;

                // Convert to HSV
                Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

                // Reduce noise
                Imgproc.GaussianBlur(hsv, blurredImage, new Size(GAUSSIAN_BLUR, GAUSSIAN_BLUR), 0);

                blurredImage.convertTo(blurredImage2, -1, 0.9, 0);

                // Detect and process each color
                processColor(blurredImage2, LOWER_RED, UPPER_RED, new Scalar(255, 0, 0), input);
                processColor(blurredImage2, LOWER_YELLOW, UPPER_YELLOW, new Scalar(255, 255, 0), input);
                processColor(blurredImage2, LOWER_BLUE, UPPER_BLUE, new Scalar(0, 0, 255), input);

                telemetry.addData("rect angle", result != null ? result.angle : "nothing");
                telemetry.update();
                return input;
            }
        }
        /* Angle Normalization: Adjust angles for "tall" rectangles by adding 90 degrees if the width is smaller than the height.
        Ensure Angles are within 0-360: Normalize the angles using modulo operations.
        These adjustments can help ensure that the angle is consistent and easier to understand across different detections.*/
        private void processColor(Mat hsvImage, Scalar lower, Scalar upper, Scalar drawColor, Mat input) {
            Core.inRange(hsvImage, lower, upper, mask);

            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            for (MatOfPoint contour : contours) {
                contour2f.fromArray(contour.toArray());
                double epsilon = APPROX_FACTOR * Imgproc.arcLength(contour2f, true);
                Imgproc.approxPolyDP(contour2f, approx, epsilon, true);

                Moments moments = Imgproc.moments(contour);
                double area = moments.get_m00();

                if (area >= MIN_RECT) {
                    // Draw polygon on the frame
                    List<MatOfPoint> polygon = new ArrayList<MatOfPoint>() {{
                        add(new MatOfPoint(approx.toArray()));
                    }};
                    Imgproc.polylines(input, polygon, true, drawColor, 4);

                    // Store the rotated rectangle
                    result = Imgproc.minAreaRect(contour2f);
                    Point[] points = new Point[4];
                    result.points(points);
                    for (int i = 0; i < 4; i++) {
                        Imgproc.line(input, points[i], points[(i + 1) % 4], drawColor, 4);
                    }

                    // Normalize and print the angle for debugging
                    angle = result.angle;
                    if (result.size.width < result.size.height) {
                        angle = angle + 90; // adjust the angle for tall rectangles
                    }
                    angle = angle % 180; // ensure the angle is within 0-180
                }
            }
        }
    }

    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        synchronized (this) {
            WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
            OpenCvPipeline pipeline = new RectPipeline(telemetry);
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

    }

    public OpenCvCamera getCamera() {
        return camera;
    }

    public RotatedRect getResult() {
        return result;
    }

    public double getAngle() {
        return angle;
    }
}