package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
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
    private final Outtake outtake;

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
        outtake = new Outtake(hardwareMap);

        godlikeManeuver = new Movement(hardwareMap);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) throws InterruptedException{
        godlikeManeuver.teleopTick(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x,gamepad1.right_trigger,telemetry);
        switch(fsm){
            case STARTING:
                if(gamepad2.a){
                  hSlides.close();
                  vSlides.moveToLowerBound();
                  intake.posIntake();
                  outtake.closeBucket();
                  fsm = FSM.INTAKE;
                }
                break;
            case INTAKE:
                hSlides.slidesMove(gamepad2.left_stick_y, gamepad2.b,telemetry);
                intake.moveRoll(gamepad2.dpad_left,gamepad2.dpad_right);
                if(gamepad2.right_trigger>0) intake.openIntake();
                else intake.closeIntake();
                if(gamepad2.a) {
                    Thread thread = new Thread(() -> Actions.runBlocking(actionTransfer()));
                    thread.start();
                }
                break;
            case TRANSFER:
                vSlides.slidesMove(gamepad2.right_stick_y, gamepad2.b, telemetry);
                if (gamepad2.right_trigger>0)
                    outtake.closeBucket();
                else
                    outtake.openBucket();
                if(gamepad2.a){
                    hSlides.close();
                    vSlides.moveToLowerBound();
                    intake.posIntake();
                    outtake.closeBucket();
                    fsm = FSM.INTAKE;
                }
                break;
        }
    }

    public SequentialAction actionTransfer() {
        return new SequentialAction(
                new InstantAction(intake::closeIntake),
                new InstantAction(hSlides::close),
                new InstantAction(vSlides::moveToLowerBound),
                new InstantAction(outtake::openBucket),
                new InstantAction(intake::posTransfer),
                new InstantAction(outtake::posTransfer),
                new SleepAction(0.3),
                new InstantAction(outtake::closeTransfer),
                new SleepAction(0.1),
                new InstantAction(intake::openTransfer),
                new InstantAction(intake::posIntake),
                new InstantAction(outtake::posBucket),
                new InstantAction(() -> fsm = FSM.TRANSFER));
    }
}
