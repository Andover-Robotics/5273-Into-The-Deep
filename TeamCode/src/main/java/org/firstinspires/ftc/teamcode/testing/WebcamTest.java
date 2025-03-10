package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleop.subsystems.Camera;
import org.firstinspires.ftc.teamcode.teleop.subsystems.Claw;
import org.opencv.core.RotatedRect;

@TeleOp(name = "Luke", group = "Teleop")
public class WebcamTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Camera camera = new Camera(hardwareMap, telemetry);
        Claw claw = new Claw(hardwareMap, 0, 0, camera);
        waitForStart();
        while(opModeIsActive()){
            RotatedRect rect = camera.getResult();

            if (gamepad2.b) claw.toSamplePosition();

            telemetry.addData("Rect", rect);
            telemetry.addData("angle", rect != null ? camera.getAngle() : "god help me");
            telemetry.update();
        }
        //Claw claw = new Claw(hardwareMap, Intake.getClawOpen(), Intake.getClawClosed(), "iClaw", "iDiffL", "iDiffR", );
        //while (opModeIsActive()) {
        //    if (gamepad2.b) claw.toSamplePosition();
        //};
    }
}
