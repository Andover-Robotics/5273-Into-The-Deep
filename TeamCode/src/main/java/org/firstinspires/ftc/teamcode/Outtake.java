package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Outtake {
    private final Servo fourL, fourR;
    private final Claw claw;
    private static final double CLAW_OPEN = 0, CLAW_CLOSED = 0;
    private static final double FOUR_BUCKET = 0, FOUR_TRANSFER = 0;
    private static final double ROLL_HORIZONTAL = 0, ROLL_VERTICAL = 0;
    private static final double PITCH_BUCKET = 0, PITCH_TRANSFER= 0;

    public Outtake (HardwareMap map, Camera camera) {
        //intake = map.get(CRServo.class, "iServo");
        fourL = map.get(Servo.class, "fourOL");
        fourR = map.get(Servo.class, "fourOR");
        claw = new Claw(map,CLAW_OPEN,CLAW_CLOSED,"oClaw","oDiffL","oDiffR", camera);
        fourR.setDirection(Servo.Direction.REVERSE);
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
        claw.openClaw();
        fsm = OuttakeState.BUCKET_OPEN;
    }

    public void closeBucket(){
        posBucket();
        claw.closeClaw();
        fsm = OuttakeState.BUCKET_CLOSED;
    }

    public void posBucket(){
        fourTo(FOUR_BUCKET);
        claw.clawPitch(PITCH_BUCKET);
    }

    public void openTransfer(){
        posTransfer();
        claw.openClaw();
        fsm = OuttakeState.TRANSFER_OPEN;
    }

    public void closeTransfer(){
        posTransfer();
        claw.closeClaw();
        fsm = OuttakeState.TRANSFER_CLOSED;
    }

    public void posTransfer(){
        fourTo(FOUR_TRANSFER);
        claw.clawPitch(PITCH_TRANSFER);
    }

    public void fourTo(double position) {
        fourL.setPosition(position);
        fourR.setPosition(position);
    }

}
