package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Intake;
import org.opencv.core.RotatedRect;
import org.openftc.easyopencv.OpenCvCamera;

@TeleOp(name = "Luke", group = "Teleop")
public class WebcamTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Claw claw = new Claw(hardwareMap, Intake.CLAW_OPEN, Intake.CLAW_CLOSED, "iClaw", "iDiffL", "iDiffR", new Camera(hardwareMap, telemetry));
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad2.b) claw.toSamplePosition();
        };
    }
}
