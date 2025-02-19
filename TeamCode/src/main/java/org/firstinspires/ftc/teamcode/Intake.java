package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    //private final CRServo intake;
    private final Servo fourL, fourR;
    private final Claw claw;
    private static final double CLAW_OPEN = 0.2461, CLAW_CLOSED = 0.0339;
    private static final double FOURL_INTAKE = 0.06, FOURL_TRANSFER = 0.0800, FOURL_SURVEY = 0.46277;
    private static final double FOURR_INTAKE = 0.99, FOURR_TRANSFER = 0.9544, FOURR_SURVEY = 0.58277;

    public Intake(HardwareMap map, Camera camera) {
        //intake = map.get(CRServo.class, "iServo");
        fourL = map.get(Servo.class, "fourIL");
        fourR = map.get(Servo.class, "fourIR");
        claw = new Claw(map, CLAW_OPEN, CLAW_CLOSED, camera);
    }

    public enum IntakeState {
        SURVEY_OPEN,
        SURVEY_CLOSED,
        INTAKE_CLOSED,
        INTAKE_OPEN,
        TRANSFER_OPEN,
        TRANSFER_CLOSED
    }

    public IntakeState fsm = IntakeState.INTAKE_OPEN;

    public static double getClawOpen() {
        return CLAW_OPEN;
    }

    public static double getClawClosed() {
        return CLAW_CLOSED;
    }

    public boolean isSurveyOpen() {
        return (fsm == IntakeState.SURVEY_OPEN);
    }

    public boolean isTransferOpen() {
        return (fsm == IntakeState.TRANSFER_OPEN);
    }

    public boolean isIntakeOpen() {
        return (fsm == IntakeState.INTAKE_OPEN);
    }

    public boolean isSurveyClosed() {
        return (fsm == IntakeState.SURVEY_CLOSED);
    }

    public boolean isTransferClosed() {
        return (fsm == IntakeState.TRANSFER_CLOSED);
    }

    public boolean isIntakeClosed() {
        return (fsm == IntakeState.INTAKE_CLOSED);
    }

    public void open() {
        claw.openClaw();
    }

    public void close() {
        claw.closeClaw();
    }

    public void openSurvey() {
        posSurvey();
        claw.openClaw();
        fsm = IntakeState.SURVEY_OPEN;
    }

    public void closeSurvey() {
        posSurvey();
        claw.closeClaw();
        fsm = IntakeState.SURVEY_CLOSED;
    }

    public void setPitchTransfer() {
        claw.setPitch(0.2155555);
    }

    public void posSurvey() {
        fourLTo(FOURL_SURVEY);
        fourRTo(FOURR_SURVEY);
        claw.setPositions(Claw.RollPosition.MIDDLE, Claw.PitchPosition.DOWN_90);
    }

    public void openIntake() {
        fourR.setPosition(0.8644);
        fourL.setPosition(0.1778);
        claw.setPitch(1);
        claw.openClaw();
        fsm = IntakeState.INTAKE_OPEN;
    }

    public void closeIntake() {
        posIntake();
        claw.closeClaw();
        fsm = IntakeState.INTAKE_CLOSED;
    }

    public void posIntake() {
        fourLTo(FOURL_INTAKE);
        fourRTo(FOURR_INTAKE);
    }

    public void looseClaw() {
        claw.looseClaw();
    }

    public void openTransfer() {
        posTransfer();
        claw.looseClaw();
        fsm = IntakeState.TRANSFER_OPEN;
    }

    public void closeTransfer() {
        posTransfer();
        claw.closeClaw();
        fsm = IntakeState.TRANSFER_CLOSED;
    }

    public void posTransfer() {
        fourLTo(FOURL_TRANSFER);
        fourRTo(FOURR_TRANSFER);
        claw.setPitch(Claw.PitchPosition.TRANSFER);
        claw.setRoll(Claw.RollPosition.MIDDLE);
    }

    public void fourLTo(double position) {
        fourL.setPosition(position);
    }

    public void fourRTo(double position) {
        fourR.setPosition(position);
    }

    public double getRoll() {
        return (claw.getRoll());
    }

    public double getPitch() {
        return (claw.getPitch());
    }

    public void clawActive(double input) {
        claw.setClaw(claw.getClawPos() + (0.01 * Math.signum(input)));
    }

    public double getClaw() {
        return claw.getClawPos();
    }

    public double fourLPos() {
        return (fourL.getPosition());
    }

    public double fourRPos() {
        return (fourR.getPosition());
    }

    public void toSamplePosition() throws InterruptedException {
        boolean worked = claw.toSamplePosition();
        if (worked) {
            Thread.sleep(100);
            closeIntake();
            Thread.sleep(400);
            closeSurvey();
        }
    }

    public boolean hasSample() {
        return (claw.hasSample());
    }

    public void moveDiffyPos(GamepadEx gamepad, Telemetry telemetry) {
        claw.positionalActiveRollPitch(gamepad, telemetry);
    }

    public void clawRoll45() {
        claw.rollIt45();
    }
}
