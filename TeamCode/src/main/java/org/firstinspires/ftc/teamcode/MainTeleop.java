package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "New Main Teleop", group = "main")
public class MainTeleop extends LinearOpMode {
    @Override
    public void runOpMode() {

        Bot bot = new Bot(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            bot.teleopTick(gamepad1, gamepad2, telemetry);
            telemetry.update();
        }
    }
}