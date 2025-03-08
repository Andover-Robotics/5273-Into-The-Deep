package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the Bot.
 */

//test commit for new branch
public class Bot {
    private final SlidesHorizontal hSlides;
    private final Intake intake;
    private final SlidesVertical vSlides;
    private final Outtake outtake;
    private final Camera camera;
    private final Servo sweepServo;

    private static final double SWEEP_UP = 0.5, SWEEP_DOWN = 0;
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
    public Bot(OpMode opMode, @NonNull HardwareMap hardwareMap, @NonNull Telemetry telemetry) {
        // initializations:
        camera = new Camera(hardwareMap, telemetry);

        // intake:
        hSlides = new SlidesHorizontal(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, camera);

        // outtake:
        vSlides = new SlidesVertical(opMode);
        outtake = new Outtake(hardwareMap);

        sweepServo = hardwareMap.get(Servo.class, "Sweep");

        fsm = FSM.STARTING;
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
        if (gamepad2.isDown(GamepadKeys.Button.X))
            fsm = FSM.HANG;
        switch (fsm) {
            case STARTING: // if just started
                vSlides.resetEncoders();
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    hSlides.middle();
                    intake.openSurvey();
                    outtake.openTransfer();
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
                outtake.openTransfer();
                hSlides.setPower(gamepad2.getLeftY());
                intake.moveDiffyPos(gamepad2, telemetry);
                telemetry.addData("Intake State", intake.fsm);
                if (rightTriggerDown && (intake.isSurveyOpen() || intake.isSurveyClosed())){
                    intake.open();
                    Thread.sleep(100);
                    intake.openIntake();
                    Thread.sleep(300);
                }
                if (intake.isIntakeOpen()) {
                    if (gamepad2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1) {
                        intake.toSamplePosition();
                        // Still need to press B later
                    } else if (!rightTriggerDown) {
                        intake.posIntake();
                        Thread.sleep(100);
                        intake.closeIntake();
                        Thread.sleep(400);
                        intake.closeSurvey();
                    }
                }
                if(gamepad2.wasJustPressed(GamepadKeys.Button.B)) {
                    Actions.runBlocking(actionTransfer());
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
                outtake.posPreBucket();
                if (gamepad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                    outtake.open();
                else
                    outtake.close();
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    hSlides.middle();
                    vSlides.toStorage();
                    intake.posSurvey();
                    outtake.openTransfer();
                    fsm = FSM.INTAKESAMPLE;
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    vSlides.toStorage();
                    intake.openSurvey();
                    outtake.openClip();
                    Thread.sleep(2000);
                    vSlides.resetEncoders();
                    fsm = FSM.INTAKESPECIMEN;
                }
                break;
            case INTAKESPECIMEN:
                outtake.openClip();
                if(gamepad2.wasJustPressed(GamepadKeys.Button.B)) {
                    Actions.runBlocking(actionIntakeSpecimenDown());
                }
                if(gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    vSlides.toStorage();
                    hSlides.middle();
                    intake.openSurvey();
                    outtake.openTransfer();
                    fsm = FSM.INTAKESAMPLE;
                }
                break;
            case CLIPSPECIMEN:
                outtake.posRungClip();
                if(gamepad2.wasJustPressed(GamepadKeys.Button.B)){
                    Actions.runBlocking(actionClipSpecimenDownUp());
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)) {
                    hSlides.middle();
                    vSlides.toStorage();
                    intake.posSurvey();
                    outtake.openTransfer();
                    fsm = FSM.INTAKESAMPLE;
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.Y)) {
                    hSlides.close();
                    vSlides.toStorage();
                    intake.openSurvey();
                    outtake.openClip();
                    fsm = FSM.INTAKESPECIMEN;
                }
                break;
            case HANG:
                outtake.closeTransfer();
                hSlides.close();
                intake.closeTransfer();
                vSlides.slidesMove(gamepad2.getLeftY());
                if (rightTriggerDown) {
                    vSlides.toStorage();
                }
                if (gamepad2.wasJustPressed(GamepadKeys.Button.A)){
                    fsm = FSM.INTAKESAMPLE;
                }
                break;
        }
        if(gamepad2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
            vSlides.toStorage();
        }
        telemetry.addData("State: ", fsm);
        telemetry.addData("Vertical Slides Pos: ", vSlides.getEncodersAverage());
    }

    public SequentialAction actionTransfer() {
        return new SequentialAction(
                new InstantAction(outtake::posPreTransfer),
                new InstantAction(intake::closeIntake),
                new InstantAction(intake::setPitchTransfer),
                new InstantAction(intake::looseClaw),
                new InstantAction(vSlides::toStorage),
                new InstantAction(hSlides::middle),
                new SleepAction(0.2),
                new InstantAction(intake::posTransfer),
                new InstantAction(hSlides::close),
                new SleepAction(0.5),
                new InstantAction(outtake::openTransfer),
                new SleepAction(0.25),
                new InstantAction(outtake::closeClaw),
                new SleepAction(0.25),
                new InstantAction(intake::open),
                new SleepAction(0.4),
                new InstantAction(intake::openSurvey),
                new InstantAction(vSlides::toTopBucket),
                new SleepAction(0.1),
                new InstantAction(outtake::posPreBucket),
                new InstantAction(hSlides::close),
                new SleepAction(0.1),
                new InstantAction(() -> fsm = FSM.SCORESAMPLE));
    }


    public SequentialAction actionTransferNoSlides() {
        return new SequentialAction(
                new InstantAction(outtake::posPreTransfer),
                new InstantAction(intake::setPitchTransfer),
                new InstantAction(vSlides::toStorage),
                new InstantAction(hSlides::middle),
                new SleepAction(0.2),
                new InstantAction(intake::posTransfer),
                new InstantAction(hSlides::close),
                new SleepAction(0.5),
                new InstantAction(outtake::openTransfer),
                new SleepAction(0.25),
                new InstantAction(outtake::closeClaw),
                new SleepAction(0.25),
                new InstantAction(intake::open),
                new SleepAction(0.4),
                new InstantAction(intake::openSurvey),
                new InstantAction(hSlides::close)
        );
    }


    public Action actionIntakeSample() { // using the intake claw
        return new SequentialAction(
                new InstantAction(intake::open),
                new SleepAction(0.1),
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

    public Action actionOuttakeBucketOne() {
        return new SequentialAction(
                new InstantAction(vSlides::toTopBucket)
        );
    }

    public Action actionOuttakeBucketTwo() {
        return new SequentialAction(
                new InstantAction(outtake::posPreBucket),
                new SleepAction(.5),
                new InstantAction(outtake::open),
                new SleepAction(0.5),
                new InstantAction(outtake::openTransfer)
        );
    }

    public Action actionToStorage() {
        return new InstantAction(vSlides::toStorage);
    }

    public SequentialAction actionIntakeSpecimenDown() {
        return new SequentialAction(
            // the moving to lower bound should be done by the outtake method at the end
            // open the claw before calling this method
            new InstantAction(outtake::close),
            new SleepAction(0.2),
            new InstantAction(outtake::posRungClip),
            new InstantAction(vSlides::toClipBottom),
            new InstantAction(() -> fsm = FSM.CLIPSPECIMEN));
    }

    public SequentialAction actionIntakeSpecimenUp() {
        return new SequentialAction(
                new InstantAction(outtake::close),
                new SleepAction(0.2),
                new InstantAction(outtake::posRungClip),
                new InstantAction(vSlides::toClipTop));
    }


    public InstantAction actionSpecPosUp() {
        return new InstantAction(vSlides::toClipTop);
    }

    public void runPeriodic(){
        vSlides.periodic();
    }

    public SequentialAction actionClipSpecimenDownUp() {
        return new SequentialAction(
                // claw should be set to perfect clipping pos so all you need is to have bot flush with the
                // bottom part of the submersible, and brings higher vert slides
                new InstantAction(vSlides::toClipTop),
                new SleepAction(1),
                new InstantAction(outtake::openClaw));
    }

    public SequentialAction actionClipSpecimenUpDown() {
        return new SequentialAction(
                new InstantAction(vSlides::toClipBottom),
                new SleepAction(1),
                new InstantAction(outtake::openClaw));
    }

    public Action slidesDown() {
        return new InstantAction(vSlides::toStorage);
    }

    public Action actionSweepArmUp() {
        return new SequentialAction(
                new InstantAction(() -> sweepServo.setPosition(SWEEP_UP))
        );
    }

    public Action actionSweepArmDown() {
        return new SequentialAction(
                new InstantAction(() -> sweepServo.setPosition(SWEEP_DOWN))
        );
    }

    public Action periodicHorizSlidesClosed() {
        return hSlides.horizPeriodicClosed();
    }

    public Action actionStartAxons() {
        return new SequentialAction(
                new InstantAction(intake::openSurvey),
                new InstantAction(outtake::closeTransfer)
        );
    }

    public Action actionArmRungBottomPos() {
        return new InstantAction(outtake::posRungClip);
    }

    public Action actionOuttakeTransfer() {
        return new SequentialAction(
                new InstantAction(outtake::openTransfer)
        );
    }

    public Action clawRoll90() {
        return new SequentialAction(
                new InstantAction(intake::clawRoll90)
                );
    }
    public Action closeHori() {
        return new SequentialAction(
                new InstantAction(hSlides::close)
                );
    }

    public Action vertSlidesToBottom() {
        return new InstantAction(vSlides::toStorage);
    }

    public Action slidesPeriodic() {
        return vSlides.periodicAction();
    }

    public Action looseHori() {
        return new InstantAction(hSlides::loose);
    }
}