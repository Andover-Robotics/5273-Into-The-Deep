package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    //private final CRServo intake;
    private final Servo fourL, fourR;
    private final Claw claw;
    private static final double CLAW_OPEN = 0.1683, CLAW_CLOSED = 0.0094;
    private static final double FOURL_INTAKE = 0.08, FOURL_TRANSFER = 0.503, FOURL_SURVEY = 0.302;
    private static final double FOURR_INTAKE = 0.16, FOURR_TRANSFER = 1, FOURR_SURVEY = 0.64;
    private static final double ROLL_HORIZONTAL = 0, ROLL_VERTICAL = 0;
    private static final double PITCH_INTAKE = 0, PITCH_TRANSFER= 0, PITCH_SURVEY = 0;

    public Intake (HardwareMap map, Camera camera) {
        //intake = map.get(CRServo.class, "iServo");
        fourL = map.get(Servo.class, "fourIL");
        fourR = map.get(Servo.class, "fourIR");
        claw = new Claw(map,CLAW_OPEN,CLAW_CLOSED,"iClaw", "iDiffL","iDiffR", camera);
        fourR.setDirection(Servo.Direction.REVERSE);
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

    public void open(){
        claw.openClaw();
    }
    public void close(){
        claw.closeClaw();
    }
    public void openSurvey(){
        posSurvey();
        claw.openClaw();
        fsm = IntakeState.SURVEY_OPEN;
    }
    public void closeSurvey(){
        posSurvey();
        claw.openClaw();
        fsm = IntakeState.SURVEY_CLOSED;
    }
    public void posSurvey(){
        fourLTo(FOURL_SURVEY);
        fourRTo(FOURR_SURVEY);
        claw.setPositions(0.7406,0.6394);
    }
    public void openIntake(){
        posIntake();
        claw.openClaw();
        fsm = IntakeState.INTAKE_OPEN;
    }

    public void closeIntake(){
        posIntake();
        claw.closeClaw();
        fsm = IntakeState.INTAKE_CLOSED;
    }

    public void posIntake(){
        fourLTo(FOURL_INTAKE);
        fourRTo(FOURR_INTAKE);
        claw.setPositions(0.7406,0.6394);
    }

    public void looseClaw(){claw.looseClaw();}

    public void openTransfer(){
        posTransfer();
        claw.looseClaw();
        fsm = IntakeState.TRANSFER_OPEN;
    }

    public void closeTransfer(){
        posTransfer();
        claw.closeClaw();
        fsm = IntakeState.TRANSFER_CLOSED;
    }

    public void posTransfer(){
        fourLTo(FOURL_TRANSFER);
        fourRTo(FOURR_TRANSFER);
        claw.setPositions(1,1);
    }

    public void fourLTo(double position){
        fourL.setPosition(position);
    }
    public void fourRTo(double position){
        fourR.setPosition(position);
    }

    public double getRoll(){
        return (claw.getRoll());
    }

    public double getPitch(){
        return (claw.getPitch());
    }

    public void clawActive(double input){
        claw.setClaw(claw.getClawPos()+(0.01*Math.signum(input)));
    }
    public double getClaw(){
        return claw.getClawPos();
    }

    public double fourLPos(){
        return(fourL.getPosition());
    }

    public double fourRPos(){
        return(fourR.getPosition());
    }

    public void toSamplePosition() {
        claw.toSamplePosition();
    }

    public boolean hasSample(){
        return(claw.hasSample());
    }

    public void moveDiffyPos(Gamepad gamepad, Telemetry telemetry){
        claw.positionalActiveClaw(gamepad,telemetry);
    }


    public void movePitch(boolean l, boolean r){
        claw.rollActiveClaw(l,r);
    }
    public void moveRoll(boolean u, boolean d){
        claw.pitchActiveClaw(u,d);
    }

}
