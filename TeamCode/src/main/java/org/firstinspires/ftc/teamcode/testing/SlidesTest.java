package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SlidesHorizontal;
import org.firstinspires.ftc.teamcode.SlidesVertical;

@TeleOp(name = "Slides Test", group = "Teleop")
public class SlidesTest extends LinearOpMode  {
    @Override
    public void runOpMode() {
        SlidesVertical vSlides = new SlidesVertical(hardwareMap);
        SlidesHorizontal hSlides = new SlidesHorizontal(hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {
            //hSlides.slidesMove(gamepad2.left_stick_y, gamepad2.b, telemetry);
            vSlides.slidesMove(gamepad2.right_stick_y, gamepad2.b, telemetry);
        }
    }
}
