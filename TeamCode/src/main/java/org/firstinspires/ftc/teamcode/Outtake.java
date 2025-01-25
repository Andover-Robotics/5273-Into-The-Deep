package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Outtake {
    private final Servo fourL, fourR;
    private final Servo claw;
    private static final double CLAW_OPEN = 0.2028, CLAW_CLOSED = 0.0022;
    private static final double FOURL_BUCKET = 0, FOURL_TRANSFER = 1;
    private static final double FOURR_BUCKET = 1, FOURR_TRANSFER = 0;

    public Outtake (HardwareMap map) {
        //intake = map.get(CRServo.class, "iServo");
        fourL = map.get(Servo.class, "fourOL");
        fourR = map.get(Servo.class, "fourOR");
        claw = map.get(Servo.class, "oClaw");
    }
    public enum OuttakeState {
        TRANSFER_OPEN,
        TRANSFER_CLOSED,
        BUCKET_OPEN,
        BUCKET_CLOSED
    }

    public OuttakeState fsm = OuttakeState.BUCKET_CLOSED;

    public void openBucket(){
        posBucket();
        openClaw();
        fsm = OuttakeState.BUCKET_OPEN;
    }

    public void closeBucket(){
        posBucket();
        closeClaw();
        fsm = OuttakeState.BUCKET_CLOSED;
    }


    public double fourLPos(){
        return(fourL.getPosition());
    }

    public double fourRPos(){
        return(fourR.getPosition());
    }

    public void posBucket(){
        fourRTo(FOURR_BUCKET);
        fourLTo(FOURL_BUCKET);
    }

    public void openTransfer(){
        posTransfer();
        openClaw();
        fsm = OuttakeState.TRANSFER_OPEN;
    }

    public void closeTransfer(){
        posTransfer();
        closeClaw();
        fsm = OuttakeState.TRANSFER_CLOSED;
    }

    public void posTransfer(){
        fourLTo(FOURL_TRANSFER);
        fourRTo(FOURR_TRANSFER);
    }
    public void open(){
        claw.setPosition(CLAW_OPEN);
    }
    public void close(){
        claw.setPosition(CLAW_CLOSED);
    }
    public void fourLTo(double position){
        fourL.setPosition(position);
    }
    public void fourRTo(double position){
        fourR.setPosition(position);
    }

    public void openClaw(){
        claw.setPosition(CLAW_OPEN);
    }

    public void closeClaw(){
        claw.setPosition(CLAW_CLOSED);
    }
}
