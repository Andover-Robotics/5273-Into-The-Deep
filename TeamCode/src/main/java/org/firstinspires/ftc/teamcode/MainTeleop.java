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
        Bot bot = new Bot(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            bot.teleopTick(gamepad1, gamepad2, telemetry);
            telemetry.update();
        }
    }
}