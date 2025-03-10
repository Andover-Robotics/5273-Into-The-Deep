package org.firstinspires.ftc.teamcode.teleop.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Outtake {
    private final Servo armL, armR;
    private final Servo claw;
    private static final double CLAW_OPEN = 0.1472, CLAW_CLOSED = 0.00;
    private static final double ARM_RUNG_LEFT = .33277778, ARM_TRANSFER_LEFT = 0.15166666, ARM_WALLINTAKE_LEFT = 0.8572222, ARM_BUCKET_LEFT = 0.712778;
    private static final double ARM_RUNG_RIGHT = 0.6566667, ARM_TRANSFER_RIGHT = 0.82833333, ARM_WALLINTAKE_RIGHT = 0.1272222, ARM_BUCKET_RIGHT = 0.276111;

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

    public double leftArmPosition(){
        return(armL.getPosition());
    }

    public double rightArmPosition(){
        return(armR.getPosition());
    }

    public void posRungClip(){
        rightArmTo(ARM_RUNG_RIGHT);
        leftArmTo(ARM_RUNG_LEFT);
    }

    public void openTransfer(){
        posTransfer();
        openClaw();
        fsm = OuttakeState.TRANSFER_OPEN;
    }

    public void posPreTransfer(){
        leftArmTo(ARM_TRANSFER_LEFT + 0.075);
        rightArmTo(ARM_TRANSFER_RIGHT - 0.075);
        openClaw();
    }

    public void closeTransfer(){
        posTransfer();
        closeClaw();
        fsm = OuttakeState.TRANSFER_CLOSED;
    }

    public void posTransfer(){
        leftArmTo(ARM_TRANSFER_LEFT);
        rightArmTo(ARM_TRANSFER_RIGHT);
    }

    public void openWallIntake(){
        posWallIntake();
        openClaw();
        fsm = OuttakeState.CLIP_OPEN;
    }

    public void closeWallIntake(){
        posWallIntake();
        closeClaw();
        fsm = OuttakeState.CLIP_CLOSED;
    }

    public void posBucketOuttake(){
        leftArmTo(ARM_BUCKET_LEFT);
        rightArmTo(ARM_BUCKET_RIGHT);
    }

    public void posWallIntake(){
        leftArmTo(ARM_WALLINTAKE_LEFT);
        rightArmTo(ARM_WALLINTAKE_RIGHT);
    }

    public void posBucketPark() {
        leftArmTo(ARML_BUCKETPARK);
        rightArmTo(ARMR_BUCKETPARK);
    }
    public void open(){
        claw.setPosition(CLAW_OPEN);
    }
    public void close(){
        claw.setPosition(CLAW_CLOSED);
    }
    public void leftArmTo(double position){
        armL.setPosition(position);
    }
    public void rightArmTo(double position){
        armR.setPosition(position);
    }

    public void openClaw(){
        claw.setPosition(CLAW_OPEN);
    }

    public void closeClaw(){
        claw.setPosition(CLAW_CLOSED);
    }
}
