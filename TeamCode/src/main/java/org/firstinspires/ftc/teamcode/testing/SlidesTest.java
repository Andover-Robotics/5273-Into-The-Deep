package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.SlidesHorizontal;
import org.firstinspires.ftc.teamcode.SlidesVertical;
@TeleOp(name = "Slides Test", group = "Teleop")
public class SlidesTest extends LinearOpMode  {
    @Override
    public void runOpMode() {
        Bot bot = new Bot(this,hardwareMap,telemetry);
        SlidesVertical vSlides = new SlidesVertical(this);
        SlidesHorizontal hSlides = new SlidesHorizontal(hardwareMap, telemetry);
        waitForStart();
        vSlides.resetEncoders();
        while (opModeIsActive()) {
            GamepadEx gamepadx = new GamepadEx(gamepad1);
            if(gamepadx.wasJustPressed(GamepadKeys.Button.A)) Actions.runBlocking(bot.actionSpecPos());
            if(gamepadx.wasJustPressed(GamepadKeys.Button.B)) Actions.runBlocking(bot.actionClipSpecimen());
            if (gamepadx.wasJustPressed(GamepadKeys.Button.X)) Actions.runBlocking(bot.slidesDown());
            vSlides.periodic();
        }
    }
}
