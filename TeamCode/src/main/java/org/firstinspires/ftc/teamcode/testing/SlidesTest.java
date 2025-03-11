package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleop.Bot;
import org.firstinspires.ftc.teamcode.teleop.subsystems.SlidesHorizontal;
import org.firstinspires.ftc.teamcode.teleop.subsystems.SlidesVertical;
@TeleOp(name = "Slides Test", group = "Teleop")
public class SlidesTest extends LinearOpMode  {
    @Override
    public void runOpMode() {
        Bot bot = new Bot(this,hardwareMap,telemetry);
        SlidesVertical vSlides = new SlidesVertical(this);
        SlidesHorizontal hSlides = new SlidesHorizontal(hardwareMap, telemetry);
        GamepadEx gamepadx = new GamepadEx(gamepad1);
        waitForStart();
        vSlides.resetEncoders();
        while (opModeIsActive()) {
            vSlides.slidesMove(gamepadx.getLeftY());
            if(gamepadx.wasJustPressed(GamepadKeys.Button.A)) Actions.runBlocking(bot.actionSpecPosUp());
            if(gamepadx.wasJustPressed(GamepadKeys.Button.B)) Actions.runBlocking(bot.actionClipSpecimenDownUp());
            if (gamepadx.wasJustPressed(GamepadKeys.Button.X)) Actions.runBlocking(bot.actionSlidesDown());
            vSlides.periodic();
            gamepadx.readButtons();
        }
    }
}
