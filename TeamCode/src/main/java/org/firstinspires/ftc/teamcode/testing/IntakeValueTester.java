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
    GamepadEx gp1;
    GamepadEx gp2;
    @Override
    public void runOpMode(){
        gp1 = new GamepadEx(gamepad1);
        gp2 = new GamepadEx(gamepad2);
        intake = new Intake(hardwareMap,new Camera(hardwareMap, telemetry));
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
            intake.moveRoll(gamepad1.dpad_left,gamepad1.dpad_right);
            intake.movePitch(gamepad1.dpad_up,gamepad1.dpad_down);
            if (gp1.getButton(GamepadKeys.Button.Y)){
            if (gp1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            if (gp1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            intake.clawActive(gamepad1.right_stick_y);
            telemetry.addData("Intake Arm Position Left: ",intake.fourLPos());
            telemetry.addData("Intake Arm Position Right: ",intake.fourRPos());
            telemetry.addData("Roll Position : ",intake.getRoll());
            telemetry.addData("Pitch Position: ",intake.getPitch());
            telemetry.addData("Claw Position: ", intake.getClaw());
            telemetry.addData("Sample Found:",intake.hasSample());
            telemetry.update();
        }
    }
}
