package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import java.util.ArrayList;
import java.util.List;

// TODO look at https://github.com/OpenFTC/EasyOpenCV/blob/master/examples/src/main/java/org/firstinspires/ftc/teamcode/StoneOrientationExample.java sometime
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
    // Used for color detection
    private final int COLOR_BOUND = 170;
    private final Scalar bound1 = new Scalar(0, COLOR_BOUND, 0);
    private final Scalar bound2 = new Scalar(0, 0, COLOR_BOUND);
    private final Scalar highBound = new Scalar(255, 255, 255);

    // OpenCV image processing
    class RectPipeline extends OpenCvPipeline {
        private Mat gray = new Mat();
        private Mat blurredImage = new Mat();
        private Mat mask1 = new Mat();
        private Mat mask2 = new Mat();
        private Mat interMask = new Mat();
        private Mat colorResult = new Mat();
        private Mat hierarchy = new Mat();
        private MatOfPoint2f approx = new MatOfPoint2f();
        private MatOfPoint2f contour2f = new MatOfPoint2f();

        @Override
        public Mat processFrame(Mat input) {
            result = null;

            Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2YCrCb);

            // Reduce noise in outcome
            Imgproc.GaussianBlur(gray, blurredImage, new Size(GAUSSIAN_BLUR, GAUSSIAN_BLUR), 0);

            // Finds red
            Core.inRange(blurredImage, bound1, highBound, colorResult);

            // TODO Why does this crash?
            /*
            Core.inRange(blurredImage, bound1, highBound, mask1);
            Core.inRange(blurredImage, bound2, highBound, mask2);
            Core.bitwise_or(mask1, mask2, interMask);
            Core.bitwise_and(blurredImage, blurredImage, colorResult, interMask);
            */

            // Finds edge POINTS
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(colorResult, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            for (MatOfPoint contour: contours) {
                // Approximates a polygon from edge POINTS
                contour2f.fromArray(contour.toArray());
                double epsilon = APPROX_FACTOR * Imgproc.arcLength(contour2f, true);
                Imgproc.approxPolyDP(contour2f, approx, epsilon, true);

                // Zeroth moment means area (mass but height is 0)
                Moments moments = Imgproc.moments(contour);
                double area = moments.get_m00();

                if (area >= MIN_RECT) {
                    // Draw onto screen (thank you OpenCV for not being able to draw rotated rectangles)
                    List<MatOfPoint> polygon = new ArrayList<MatOfPoint>() {{
                        add(contour);
                    }};
                    Imgproc.polylines(input, polygon, true, new Scalar(255, 0, 0), 4);

                    // Gets a rotated rectangle, because standard Rects are parallel to coordinate axes
                    result = Imgproc.minAreaRect(contour2f);
                }
            }

            return input;
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
                // send help if this occurs
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
