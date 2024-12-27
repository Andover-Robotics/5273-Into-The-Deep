package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Bot.
 */
public class Bot {
    //private final Slides ascendingStorm;
    private final Movement godlikeManeuver;
    private final Claw claw;
    private final Slides slides;
    private final Pivot pivot;
    private final DiffyRotator diffyRotator;
    private final OpenCvPipeline pipeline = new RectPipeline();

    // Factor to estimate a polygon with - shouldn't be too low because noise will impact
    // the image more, and shouldn't be too high, or otherwise detail is lost
    private final double APPROX_FACTOR = 0.04;
    // Makes sure that the rectangle is large enough and that it isn't noise
    private final double MIN_RECT = 1000;
    // How many pixels should be blended together
    private final int GAUSSIAN_BLUR = 5;
    // Lower gradient bound for Canny edge detector
    // https://en.wikipedia.org/wiki/Canny_edge_detector#Double_threshold
    private final int CANNY_THRESHOLD_LOWER = 50;
    // Higher bound for Canny edge detector
    private final int CANNY_THRESHOLD_HIGHER = 150;
    // Used for camera streaming - make sure this matches the camera's resolution
    private final int CAMERA_WIDTH = 320;
    private final int CAMERA_HEIGHT = 240;

    // OpenCV image processing
    class RectPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            Mat gray = new Mat();
            Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGBA2GRAY);

            // Reduce noise in outcome
            Mat blurredImage = new Mat();
            Imgproc.GaussianBlur(gray, blurredImage, new Size(GAUSSIAN_BLUR, GAUSSIAN_BLUR), 0);

            // Finds edge LINES
            Mat edges = new Mat();
            Imgproc.Canny(blurredImage, edges, CANNY_THRESHOLD_LOWER, CANNY_THRESHOLD_HIGHER);

            // Finds edge POINTS
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            for (MatOfPoint contour: contours) {
                // Approximates a polygon from edge POINTS
                MatOfPoint2f approx = new MatOfPoint2f();
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                double epsilon = APPROX_FACTOR * Imgproc.arcLength(contour2f, true);
                Imgproc.approxPolyDP(contour2f, approx, epsilon, true);

                if (approx.rows() == 4) {
                    // Zeroth moment means area (mass but height is 0)
                    Moments moments = Imgproc.moments(contour);
                    double area = moments.get_m00();

                    if (area >= MIN_RECT) {
                        // Gets a rotated rectangle, because standard Rects are parallel to coordinate axes
                        RotatedRect rotated = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                        // Do something with rotated.angle for orientation
                    }
                }
            }

            return input;
        }
    }

    public enum FSM{
        OUTTAKE, //outtake
        INTAKE, //intake
        STARTING
    }

    public FSM fsm = FSM.STARTING;
    /**
     * Initializes a Bot instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Bot(@NonNull HardwareMap hardwareMap) {
        //ascendingStorm = new Slides(hardwareMap);
        godlikeManeuver = new Movement(hardwareMap);
        claw = new Claw(hardwareMap);
        slides = new Slides(hardwareMap);
        pivot = new Pivot(hardwareMap);
        diffyRotator = new DiffyRotator(hardwareMap);
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "NAME_OF_CAMERA_IN_CONFIG_FILE");
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {
                // send help if this occurs
            }
        });
        camera.setPipeline(pipeline);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        godlikeManeuver.teleopTick(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger,telemetry);
        slides.teleopTick(gamepad2.left_stick_y, gamepad2.b, telemetry);
        claw.teleopTick(gamepad2.right_trigger, telemetry);
        pivot.teleopTick(gamepad2.right_stick_y, gamepad2.b, telemetry);
        diffyRotator.teleopTick(gamepad2, telemetry);
    }
}
