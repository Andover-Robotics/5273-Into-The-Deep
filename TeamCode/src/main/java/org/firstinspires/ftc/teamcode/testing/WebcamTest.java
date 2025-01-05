package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Camera;
import org.openftc.easyopencv.OpenCvCamera;

@TeleOp(name = "Luke", group = "Teleop")
public class WebcamTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        OpenCvCamera camera = new Camera(hardwareMap, telemetry).getCamera();
        waitForStart();

        while (opModeIsActive()) {
            // Statistics because why not
            // Taken from https://github.com/OpenFTC/EasyOpenCV/blob/master/examples/src/main/java/org/firstinspires/ftc/teamcode/WebcamExample.java
            telemetry.addData("Frame Count", camera.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", camera.getFps()));
            telemetry.addData("Total frame time ms", camera.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", camera.getPipelineTimeMs());
            telemetry.addData("Overhead time ms", camera.getOverheadTimeMs());
            telemetry.addData("Theoretical max FPS", camera.getCurrentPipelineMaxFps());
            telemetry.update();
        };
    }
}
