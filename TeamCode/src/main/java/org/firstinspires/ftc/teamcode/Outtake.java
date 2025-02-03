package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Outtake {
    private final Servo armL, armR;
    private final Servo claw;
    private static final double CLAW_OPEN = 0.2028, CLAW_CLOSED = 0.0022;
    private static final double ARML_BUCKET = 0.85222, ARML_TRANSFER = 0.23500, ARML_CLIP = 0.909444;
    private static final double ARMR_BUCKET = 0.14500, ARMR_TRANSFER = 0.75055, ARMR_CLIP = 0.089444;

    public Outtake (HardwareMap map) {
        //intake = map.get(CRServo.class, "iServo");
        armL = map.get(Servo.class, "fourOL");
        armR = map.get(Servo.class, "fourOR");
        claw = map.get(Servo.class, "oClaw");
    }
    public enum OuttakeState {
        TRANSFER_OPEN,
        TRANSFER_CLOSED,
        BUCKET_OPEN,
        BUCKET_CLOSED,
        CLIP_OPEN,
        CLIP_CLOSED
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
        return(armL.getPosition());
    }

    public double fourRPos(){
        return(armR.getPosition());
    }

    public void posBucket(){
        fourRTo(ARMR_BUCKET);
        fourLTo(ARML_BUCKET);
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
        fourLTo(ARML_TRANSFER);
        fourRTo(ARMR_TRANSFER);
    }
    public void openClip(){
        posClip();
        openClaw();
        fsm = OuttakeState.CLIP_OPEN;
    }

    public void closeClip(){
        posClip();
        closeClaw();
        fsm = OuttakeState.CLIP_CLOSED;
    }

    public void posClip(){
        fourLTo(ARML_CLIP);
        fourRTo(ARMR_CLIP);
    }
    public void open(){
        claw.setPosition(CLAW_OPEN);
    }
    public void close(){
        claw.setPosition(CLAW_CLOSED);
    }
    public void fourLTo(double position){
        armL.setPosition(position);
    }
    public void fourRTo(double position){
        armR.setPosition(position);
    }

    public void openClaw(){
        claw.setPosition(CLAW_OPEN);
    }

    public void closeClaw(){
        claw.setPosition(CLAW_CLOSED);
    }
}
