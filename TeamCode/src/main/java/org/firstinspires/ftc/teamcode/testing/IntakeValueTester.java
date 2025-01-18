package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Intake;

@TeleOp(name = "Intake Value Tester", group = "Teleop")
public class IntakeValueTester extends LinearOpMode {
    Intake intake;
    @Override
    public void runOpMode(){
        intake = new Intake(hardwareMap,new Camera(hardwareMap, telemetry));
        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.left_stick_y!=0){
                intake.fourLTo(Math.signum(gamepad1.left_stick_y)*0.05);
                intake.fourRTo(Math.signum(gamepad1.left_stick_y)*0.05);
            }
            intake.moveRoll(gamepad1.dpad_left,gamepad1.dpad_right);
            intake.movePitch(gamepad1.dpad_up,gamepad1.dpad_down);
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
