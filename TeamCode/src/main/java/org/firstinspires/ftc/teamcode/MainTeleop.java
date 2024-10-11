package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "New Main Teleop", group = "main")
/**
 * Represents the Teleop Op Mode
 */
public class MainTeleop extends LinearOpMode {
    @Override
    /**
     * Runs the OpMode.
     */
    public void runOpMode() {
        Bot bot = new Bot(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            bot.teleopTick(gamepad1, gamepad2, telemetry);
            telemetry.update();
        }
    }
}