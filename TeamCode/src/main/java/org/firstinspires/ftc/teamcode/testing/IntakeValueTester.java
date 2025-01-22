package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.SlidesHorizontal;
import org.firstinspires.ftc.teamcode.SlidesVertical;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Intake Value Tester", group = "Teleop")
public class IntakeValueTester extends LinearOpMode {
    Intake intake;
    SlidesVertical vSlides;
    Outtake outtake;
    GamepadEx gp1;
    GamepadEx gp2;
    SlidesHorizontal hSlides;

    @Override
    public void runOpMode(){
        gp1 = new GamepadEx(gamepad1);
        gp2 = new GamepadEx(gamepad2);
        intake = new Intake(hardwareMap,new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        vSlides = new SlidesVertical(hardwareMap);
        hSlides = new SlidesHorizontal(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            vSlides.slidesMove(gp2.getLeftY(), gp2.getButton(GamepadKeys.Button.B), telemetry);
            telemetry.addData("vSlides position",vSlides.getEncoders());
            if (gp1.getButton(GamepadKeys.Button.A)){
                /*intake.fourLTo(0.106);
                intake.fourRTo(0.2);*/
                outtake.fourLTo(0.1);
                outtake.fourRTo(0.9);

            }
            if (gp1.getButton(GamepadKeys.Button.B)){
                /*intake.fourLTo(0.503); //top position : 0.503
                intake.fourRTo(1); // top position : 1*/
                outtake.fourLTo(1);
                outtake.fourRTo(0);
            }
            if (gp1.getButton(GamepadKeys.Button.Y)){
               /* intake.fourLTo(0.302);
                intake.fourRTo(0.64);*/
            }
            if (gp1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
                intake.fourLTo(intake.fourLPos()+0.0005);
                intake.fourRTo(intake.fourRPos()-(0.0005));
            }
            if (gp1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
                intake.fourLTo(intake.fourLPos()+0.0005);
                intake.fourRTo(intake.fourRPos()-(0.0005));
            }
            intake.moveDiffyPos(gamepad1,telemetry);
            intake.clawActive(gamepad1.right_stick_y);
            hSlides.slidesMove(gamepad1.left_stick_y, gamepad1.b,telemetry);
            telemetry.addData("Outtake Arm Position Left: ",intake.fourLPos());
            telemetry.addData("Outtake Arm Position Right: ",intake.fourRPos());
            telemetry.addData("Roll Position : ",intake.getRoll());
            telemetry.addData("Pitch Position: ",intake.getPitch());
            telemetry.addData("Claw Position: ", intake.getClaw());
            telemetry.addData("Sample Found:",intake.hasSample());
            telemetry.update();
        }
    }
}
