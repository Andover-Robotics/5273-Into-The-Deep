package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);
        Bot bot = new Bot(hardwareMap, telemetry);
        Movement movement = new Movement(hardwareMap);

        Thread movementThread = new Thread(() -> { // thread movement separately so that Thread.sleep() can be safely called in bot.teleopTick()
            while (!Thread.currentThread().isInterrupted()) {
                movement.teleopTick(gamepadEx1.getLeftX(),gamepadEx1.getLeftY(),gamepadEx1.getRightX(), gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER),telemetry);
            }
        });
        waitForStart();
        movementThread.start();
        while (opModeIsActive()) {
            gamepadEx1.readButtons();
            gamepadEx2.readButtons();
            bot.teleopTick(gamepadEx1, gamepadEx2, telemetry);
            telemetry.update();
        }
        movementThread.interrupt();
    }
}