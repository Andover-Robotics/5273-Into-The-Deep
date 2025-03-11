package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleop.subsystems.Movement;

/**
 * Represents the Teleop OpMode
 */
@TeleOp(name = "New Main Teleop", group = "main")
class MainTeleop extends LinearOpMode {
    /**
     * Runs the OpMode.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);
        Bot bot = new Bot(this,hardwareMap, telemetry);
        Movement movement = new Movement(hardwareMap);

        Thread threaded = new Thread(() -> { // thread movement separately so that Thread.sleep() can be safely called in bot.teleopTick()
            while (!Thread.currentThread().isInterrupted()) {
                movement.teleopTick(gamepadEx1.getLeftX(),gamepadEx1.getLeftY(),gamepadEx1.getRightX(), telemetry);//,gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER),telemetry);
                bot.runPeriodic();
            }
        });

        waitForStart();
        threaded.start();
        while (opModeIsActive()) {
            gamepadEx1.readButtons();
            gamepadEx2.readButtons();
            bot.teleopTick(gamepadEx1, telemetry);
            telemetry.update();
        }
        threaded.interrupt();
    }
}