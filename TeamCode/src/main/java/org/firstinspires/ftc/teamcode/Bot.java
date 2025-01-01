package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.old.Pivot;

/**
 * Represents the Bot.
 */
public class Bot {
    private final Movement godlikeManeuver;
    private final SlidesHorizontal hSlides;
    private final Intake intake;
    private final SlidesVertical vSlides;
    private final Claw claw;

    public enum FSM{
        STARTING,
        INTAKE,
        TRANSFER
    }
    public FSM fsm = FSM.STARTING;
    /**
     * Initializes a Bot instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Bot(@NonNull HardwareMap hardwareMap) {
        // initializations:

        // intake:
        hSlides = new SlidesHorizontal(hardwareMap);
        intake = new Intake(hardwareMap);

        // outtake:
        vSlides = new SlidesVertical(hardwareMap);
        claw = new Claw(hardwareMap);

        godlikeManeuver = new Movement(hardwareMap);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        godlikeManeuver.teleopTick(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x,gamepad1.right_trigger,telemetry);
        switch(fsm){
            case STARTING:
                intake.posIntake();
                vSlides.moveToLowerBound();
                hSlides.close();
                intake.stop();
            case INTAKE:
                hSlides.slidesMove(gamepad2.left_stick_y, gamepad2.b,telemetry);
                intake.posIntake();
                vSlides.moveToLowerBound();
                if(gamepad2.right_trigger>0) intake.runIntake();
                else intake.stop();
                //TODO: do claw class autotranfer
                if(gamepad2.a) {
                    //TODO: move the intake-transfer to an action
                    hSlides.close();
                    vSlides.moveToLowerBound();
                    intake.posTransfer();
                    claw.openClaw();
                    //TODO: implement claw transferring
                    intake.runTransfer();
                    claw.closeClaw();
                    fsm = FSM.TRANSFER;
                }
        }
    }
}
