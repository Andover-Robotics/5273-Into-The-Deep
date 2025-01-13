package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Represents the Teleop OpMode
 */
@TeleOp(name = "New Main Teleop", group = "main")
public class MainTeleop extends LinearOpMode {
    /**
     * Runs the OpMode.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        Movement movement = new Movement(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            movement.teleopTick(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x,gamepad1.right_trigger,telemetry);
            telemetry.update();
        }
    }
}