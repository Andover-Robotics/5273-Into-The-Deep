package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private final Servo armL;
    private final Servo armR;
    private final Servo wrist;
    private static final double INTAKE_ARM = 0.5;
    private static final double INTAKE_WRIST = 0.5;
    private static final double TRANSFER_ARM = 0.5;
    private static final double TRANSFER_WRIST = 0.5;

    public Arm (HardwareMap map) {
        armL = map.get(Servo.class, "armL");
        armR = map.get(Servo.class, "armR");
        wrist = map.get(Servo.class, "wrist");
    }

    public enum ArmState {
        INTAKE,
        TRANSFER
    }

    public ArmState fsm = ArmState.INTAKE;

    public void intakePos(){
        armL.setPosition(INTAKE_ARM);
        armR.setPosition(INTAKE_ARM);
        wrist.setPosition(INTAKE_WRIST);
        fsm = ArmState.INTAKE;
    }

    public void transferPos(){
        armL.setPosition(TRANSFER_ARM);
        armR.setPosition(TRANSFER_ARM);
        wrist.setPosition(TRANSFER_WRIST);
        fsm = ArmState.TRANSFER;
    }
}
