package org.firstinspires.ftc.teamcode.testing;

import android.transition.Slide;

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
        boolean start = false;
        waitForStart();
        while(opModeIsActive()){
            if (!start){
                start = true;
                hSlides.close();
            }
            vSlides.slidesMove(gp2.getLeftY(), gp2.getButton(GamepadKeys.Button.B), telemetry);
            telemetry.addData("vSlides position",vSlides.getEncoders());
            if (gp1.getButton(GamepadKeys.Button.A)){
                intake.posIntake();
            }
            if (gp1.getButton(GamepadKeys.Button.B)){
                intake.posTransfer();
            }
            if (gp1.getButton(GamepadKeys.Button.Y)){
                intake.posSurvey();
            }
            if (gp2.getButton(GamepadKeys.Button.A)){
                outtake.posTransfer();

            }
            if (gp2.getButton(GamepadKeys.Button.B)){
                outtake.posBucket();
            }
            if (gp1.getRightY()>0) intake.open();
            if (gp1.getRightY()==0) intake.looseClaw();
            if (gp1.getRightY()<0) intake.close();

            intake.moveDiffyPos(gamepad1,telemetry);
            hSlides.setPower(gp1.getLeftY());
            telemetry.addData("HSlides Left: ",hSlides.getLeft());
            telemetry.addData("HSlides Right: ",hSlides.getRight());
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
