package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Outtake {
    private final Servo armL, armR;
    private final Servo claw;
    private static final double CLAW_OPEN = 0.1472, CLAW_CLOSED = 0.00;
    private static final double ARML_RUNGCLIP = .33277778, ARML_TRANSFER = 0.14444445, ARML_CLIP = 0.8572222;
    private static final double ARMR_RUNGCLIP = 0.6566667, ARMR_TRANSFER = 0.83888889, ARMR_CLIP = 0.1272222;
    private static final double ARML_BUCKETPARK = 0.631666666667, ARMR_BUCKETPARK = .361666666666;
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
        posRungClip();
        openClaw();
        fsm = OuttakeState.BUCKET_OPEN;
    }

    public void closeBucket(){
        posRungClip();
        closeClaw();
        fsm = OuttakeState.BUCKET_CLOSED;
    }


    public double fourLPos(){
        return(armL.getPosition());
    }

    public double fourRPos(){
        return(armR.getPosition());
    }

    public void posRungClip(){
        fourRTo(ARMR_RUNGCLIP);
        fourLTo(ARML_RUNGCLIP);
    }

    public void openTransfer(){
        posTransfer();
        openClaw();
        fsm = OuttakeState.TRANSFER_OPEN;
    }

    public void posPreTransfer(){
        fourLTo(ARML_TRANSFER + 0.075);
        fourRTo(ARMR_TRANSFER - 0.075);
        openClaw();
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
        posClipIntake();
        openClaw();
        fsm = OuttakeState.CLIP_OPEN;
    }

    public void closeClip(){
        posClipIntake();
        closeClaw();
        fsm = OuttakeState.CLIP_CLOSED;
    }

    public void posPreBucket(){
        fourLTo(0.712778);
        fourRTo(0.276111);
    }

    public void posClipIntake(){
        fourLTo(ARML_CLIP);
        fourRTo(ARMR_CLIP);
    }

    public void posBucketPark() {
        fourLTo(ARML_BUCKETPARK);
        fourRTo(ARMR_BUCKETPARK);
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
