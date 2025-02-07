package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the Bot.
 */

//test commit for new branch
public class Bot {
    private final Movement movement;
    private final SlidesHorizontal hSlides;
    private final Intake intake;
    private final SlidesVertical vSlides;
    private final Outtake outtake;
    private final Camera camera;

    public enum FSM {
        STARTING,
        INTAKESAMPLE,
        SCORESAMPLE,
        INTAKESPECIMEN,
        CLIPSPECIMEN,
        HANG
    }

    public FSM fsm = FSM.STARTING;

    /**
     * Initializes a Bot instance.
     *
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Bot(@NonNull HardwareMap hardwareMap, @NonNull Telemetry telemetry) {
        // initializations:
        camera = new Camera(hardwareMap, telemetry);

        // intake:
        hSlides = new SlidesHorizontal(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, camera);

        // outtake:
        vSlides = new SlidesVertical(hardwareMap);
        outtake = new Outtake(hardwareMap);

        movement = new Movement(hardwareMap);

        fsm = FSM.STARTING;
    }

    public Movement getMovement() {
        return movement;
    }

    /**
     * Runs one tick of the Teleop OpMode, excluding movement, which is threaded separately
     *
     * @param gamepad1  {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2  {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */

    public void teleopTick(GamepadEx gamepad1, GamepadEx gamepad2, Telemetry telemetry) throws InterruptedException {
        boolean rightTriggerDown = gamepad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1;
        final TriggerReader rightTrigger = new TriggerReader(gamepad2, GamepadKeys.Trigger.RIGHT_TRIGGER);
        telemetry.addData("State: ", fsm);
        telemetry.addData("Vertical Slides Pos: ", vSlides.getEncoders());
        if (gamepad2.isDown(GamepadKeys.Button.X))
            fsm = FSM.HANG;
        //telemetry.addData("Horizontal Slides Pos: ", hSlides.getPositions());
        switch (fsm) {
            case STARTING: // if just started
                vSlides.resetEncoders();
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    hSlides.close();
                    intake.openSurvey();
                    outtake.closeBucket();
                    fsm = FSM.INTAKESAMPLE;
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    intake.openSurvey();
                    outtake.openClip();
                    fsm = FSM.INTAKESPECIMEN;
                }
                break;
            case INTAKESAMPLE: // direction control over horizontal slides and intake

                hSlides.setPower(gamepad2.getLeftY());
                telemetry.addData("is right trigger?: ",rightTriggerDown);
                intake.moveDiffyPos(gamepad2, telemetry);
                telemetry.addData("Intake State", intake.fsm);
                telemetry.addData("arm i left", intake.fourLPos());
                telemetry.addData("arm i right", intake.fourRPos());
                //if (intake.isSurveyOpen()) intake.toSamplePosition();
                if (rightTriggerDown && (intake.isSurveyOpen() || intake.isSurveyClosed())){
                    intake.open();
                    Thread.sleep(100);
                    intake.openIntake();
                    Thread.sleep(300);
                }
                if (intake.isIntakeOpen() && !(rightTriggerDown)) {
                    intake.posIntake();
                    Thread.sleep(100);
                    intake.closeIntake();
                    Thread.sleep(200);
                    intake.closeSurvey();
                }

                if(gamepad2.wasJustPressed(GamepadKeys.Button.B)) {
                    Thread thread = new Thread(() -> Actions.runBlocking(actionTransfer()));
                    thread.start();
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    intake.openSurvey();
                    outtake.openClip();
                    fsm = FSM.INTAKESPECIMEN;
                }
                telemetry.addData("Has sample: ",intake.hasSample());
                break;
            case SCORESAMPLE: // direct control over vertical slides and outtake
                vSlides.slidesMove(gamepad2.getLeftY(), gamepad2.isDown(GamepadKeys.Button.B), telemetry);
                outtake.posPreTransfer();
                if (!gamepad2.isDown(GamepadKeys.Button.B) && gamepad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
                    Actions.runBlocking(actionOuttakeBucket());
                }
                else if (!gamepad2.isDown(GamepadKeys.Button.B))
                    outtake.closeBucket();


                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    hSlides.close();
                    vSlides.setPosition(0);
                    intake.posSurvey();
                    outtake.closeBucket();
                    fsm = FSM.INTAKESAMPLE;
                }

                if (gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    intake.openSurvey();
                    outtake.openClip();
                    fsm = FSM.INTAKESPECIMEN;
                }
                break;
            case INTAKESPECIMEN:
                if(gamepad2.wasJustPressed(GamepadKeys.Button.B)) {
                    Actions.runBlocking(actionIntakeSpecimen());
                    fsm = FSM.CLIPSPECIMEN;
                }
                if(gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    intake.openSurvey();
                    outtake.closeBucket();
                    fsm = FSM.INTAKESAMPLE;
                }
                break;
            case CLIPSPECIMEN:
                vSlides.slidesMove(gamepad2.getLeftY(), gamepad2.isDown(GamepadKeys.Button.B), telemetry);
                if(gamepad2.wasJustPressed(GamepadKeys.Button.B))
                    Actions.runBlocking(actionOuttakeSpecimen());
                if (gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    vSlides.setPosition(0);
                    intake.posSurvey();
                    outtake.closeBucket();
                    fsm = FSM.INTAKESAMPLE;
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    hSlides.close();
                    vSlides.setPosition(0);
                    intake.openSurvey();
                    outtake.openClip();
                    fsm = FSM.INTAKESPECIMEN;
                }
                break;
            case HANG:
                outtake.closeTransfer();
                hSlides.close();
                intake.closeTransfer();
                vSlides.slidesMove(gamepad2.getLeftY(), gamepad2.isDown(GamepadKeys.Button.B), telemetry);
                if (rightTriggerDown) {
                    vSlides.moveToLowerBound();
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)){
                    fsm = FSM.INTAKESAMPLE;
                }
                break;
        }
        if (gamepad2.isDown(GamepadKeys.Button.X)) fsm = FSM.HANG;
    }

    public SequentialAction actionTransfer() {
        return new SequentialAction(
                new InstantAction(outtake::closeBucket),
                new InstantAction(intake::closeSurvey),
                new SleepAction(0.2),
                new InstantAction(vSlides::moveToLowerBound),
                new InstantAction(hSlides::close),
                new SleepAction(0.2),
                new InstantAction(intake::closeTransfer),
                new SleepAction(0.25),
                new InstantAction(intake::looseClaw),
                new SleepAction(0.2),
                new InstantAction(outtake::posPreTransfer),
                new SleepAction(0.1),
                new InstantAction(outtake::openTransfer),
                new SleepAction(0.25),
                new InstantAction(outtake::closeClaw),
                new SleepAction(0.1),
                new InstantAction(intake::open),
                new SleepAction(0.2),
                new InstantAction(intake::openSurvey),
                //new SleepAction(1),
                //new InstantAction(vSlides::moveToTopBucketPos),
                new InstantAction(outtake::closeBucket),
                new SleepAction(1),
                new InstantAction(hSlides::close),
                new SleepAction(1),
                new InstantAction(() -> fsm = FSM.SCORESAMPLE));
    }

    public Action actionIntake() { // using the intake claw
        return new SequentialAction(
                new InstantAction(intake::openIntake),
                new SleepAction(0.5),
                new InstantAction(() -> {
                    intake.posIntake();
                    intake.closeIntake();
                }),
                new SleepAction(0.5),
                new InstantAction(() -> {
                    intake.looseClaw();
                    intake.posTransfer();
                })
        );
    }

    public Action actionOuttakeBucket() {
        return new SequentialAction(
                new InstantAction(vSlides::moveToTopBucketPos),
                new InstantAction(outtake::posPreTransfer),
                new SleepAction(0.5),
                new InstantAction(outtake::closeBucket),
                new SleepAction(0.5),
                new InstantAction(outtake::open)
        );
    }

    public SequentialAction actionIntakeSpecimen() {
        return new SequentialAction(
                // the moving to lower bound should be done by the outtake method at the end
                // open the claw before calling this method
                new InstantAction(outtake::closeClaw),
                new SleepAction(0.5),
                new InstantAction(vSlides::moveToRungClippingPos),
                new SleepAction(0.5),
                new InstantAction(outtake::posBucket),
                new InstantAction(() -> fsm = FSM.CLIPSPECIMEN));
    }

    public SequentialAction actionOuttakeSpecimen() {
        return new SequentialAction(
                // claw should be set to perfect clipping pos so all you need is to have bot flush with the
                // bottom part of the submersible, and lowers vert slides
                new InstantAction(vSlides::clipSpecimenVertSlides),
                new SleepAction(0.5),
                new InstantAction(outtake::openClaw),
                new InstantAction(vSlides::moveToLowerBound));
    }

    public Action actionSweep() {
        return null; // TODO should lower sweeping arm, wait, then raise arm
    }
}