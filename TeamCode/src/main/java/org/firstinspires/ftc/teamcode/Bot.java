package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.time.temporal.TemporalField;

/**
 * Represents the Bot.
 */
public class Bot {
    private final Movement movement;
    private final SlidesHorizontal hSlides;
    private final Intake intake;
    private final SlidesVertical vSlides;
    private final Outtake outtake;
    private final Camera camera;

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
    public Bot(@NonNull HardwareMap hardwareMap, @NonNull Telemetry telemetry) {
        // initializations:
        camera = new Camera(hardwareMap, telemetry);

        // intake:
        hSlides = new SlidesHorizontal(hardwareMap);
        intake = new Intake(hardwareMap, camera);

        // outtake:
        vSlides = new SlidesVertical(hardwareMap);
        outtake = new Outtake(hardwareMap);

        movement = new Movement(hardwareMap);

        fsm = FSM.STARTING;
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(GamepadEx gamepad1, GamepadEx gamepad2, Telemetry telemetry) throws InterruptedException{
        final TriggerReader rightTrigger = new TriggerReader(gamepad2, GamepadKeys.Trigger.RIGHT_TRIGGER);
        movement.teleopTick(gamepad1.getLeftX(),gamepad1.getLeftY(),gamepad1.getRightX(), gamepad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER),telemetry);
        telemetry.addData("State: ",fsm);

        telemetry.addData("Vertical Slides Pos: ", vSlides.getEncoders());
        telemetry.addData("Horizontal Slides Pos: ", hSlides.getPositions());
        switch(fsm){
            case STARTING:
                if(gamepad2.wasJustPressed(GamepadKeys.Button.A)){
                  hSlides.close();
                  vSlides.moveToLowerBound();
                  intake.posSurvey();
                  outtake.closeBucket();
                  fsm = FSM.INTAKE;
                }
                break;
            case INTAKE:
                hSlides.slidesMove(gamepad2.getLeftY(), gamepad2.isDown(GamepadKeys.Button.B),telemetry);
                intake.moveRoll(gamepad2.getButton(GamepadKeys.Button.DPAD_LEFT),gamepad2.getButton(GamepadKeys.Button.DPAD_RIGHT));
                intake.movePitch(gamepad2.getButton(GamepadKeys.Button.DPAD_UP),gamepad2.getButton(GamepadKeys.Button.DPAD_DOWN));
                if (intake.fsm == Intake.IntakeState.SURVEY_OPEN) intake.toSamplePosition();
                if (rightTrigger.wasJustPressed()) {
                    intake.openIntake();
                }
                else if (intake.fsm == Intake.IntakeState.SURVEY_OPEN || intake.fsm == Intake.IntakeState.INTAKE_CLOSED){
                    intake.closeSurvey();
                }
                else if (intake.fsm == Intake.IntakeState.INTAKE_OPEN) {
                    intake.closeIntake();
                }
                else {
                    intake.openSurvey();
                }
                if(gamepad2.wasJustPressed(GamepadKeys.Button.B)) {
                    Thread thread = new Thread(() -> Actions.runBlocking(actionTransfer()));
                    thread.start();
                }
                telemetry.addData("Has sample: ",intake.hasSample());
                break;
            case TRANSFER:
                vSlides.slidesMove(gamepad2.getRightY(), gamepad2.isDown(GamepadKeys.Button.B), telemetry);
                if (rightTrigger.wasJustPressed())
                    outtake.openBucket();
                else if (rightTrigger.wasJustReleased())
                    outtake.closeBucket();
                if(gamepad2.wasJustPressed(GamepadKeys.Button.A)){
                    hSlides.close();
                    vSlides.moveToLowerBound();
                    intake.posSurvey();
                    outtake.closeBucket();
                    fsm = FSM.INTAKE;
                }
                break;
        }
    }

    public SequentialAction actionTransfer() {
        return new SequentialAction(
                new InstantAction(intake::closeSurvey),
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
