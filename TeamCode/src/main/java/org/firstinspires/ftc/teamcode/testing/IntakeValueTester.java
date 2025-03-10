package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleop.subsystems.Camera;
import org.firstinspires.ftc.teamcode.teleop.subsystems.Intake;
import org.firstinspires.ftc.teamcode.teleop.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.teleop.subsystems.SlidesHorizontal;
import org.firstinspires.ftc.teamcode.teleop.subsystems.SlidesVertical;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

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
        vSlides = new SlidesVertical(this);
        hSlides = new SlidesHorizontal(hardwareMap, telemetry);
        boolean start = false;
        waitForStart();
        while(opModeIsActive()){
            if (!start){
                start = true;
                hSlides.close();
                vSlides.resetEncoders();
            }
            vSlides.slidesMove(gp2.getLeftY());
            telemetry.addData("vSlides position",vSlides.getEncodersAverage());
            telemetry.addData("y left",gp2.getLeftY());
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
                outtake.posRungClip();
            }
            if (gp1.getRightY()>0) intake.open();
            if (gp1.getRightY()==0) intake.looseClaw();
            if (gp1.getRightY()<0) intake.close();
            if (gp2.getRightY()>0) outtake.open();
            if (gp2.getRightY()<0) outtake.close();

            intake.moveDiffyPos(gp1,telemetry);
            hSlides.setPower(gp1.getLeftY());
            vSlides.periodic();
            telemetry.addData("Vslides Position",vSlides.getEncodersAverage());
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
