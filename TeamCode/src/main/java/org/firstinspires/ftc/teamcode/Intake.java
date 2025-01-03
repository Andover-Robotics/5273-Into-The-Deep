package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private final CRServo intake;
    private final Arm arm;
    private static final double RUN_POWER = 1;
    //private final CRServo intake;
    private final Servo fourL, fourR;
    private final Claw claw;
    private static final double CLAW_OPEN = 0, CLAW_CLOSED = 0;
    private static final double FOUR_INTAKE = 0, FOUR_OUTTAKE = 0;

    public Intake (HardwareMap map) {
        //intake = map.get(CRServo.class, "iServo");
        fourL = map.get(Servo.class, "fourIL");
        fourR = map.get(Servo.class, "fourIR");
        claw = new Claw(map,CLAW_OPEN,CLAW_CLOSED,"iClaw");
        fourR.setDirection(Servo.Direction.REVERSE);
    }

    public enum IntakeState {
        INTAKE_OPEN,
        INTAKE_CLOSED,
        TRANSFER_OPEN,
        TRANSFER_CLOSED
    }

    public IntakeState fsm = IntakeState.INTAKE_OPEN;

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
        stop();
        if (!(arm.fsm == Arm.ArmState.INTAKE)) arm.intakePos();
        fsm = IntakeState.INTAKE_STOP;
    }

    public void stop(){
        intake.setPower(0);
    }

    public void runTransfer(){
        posTransfer();
        claw.openClaw();
        fsm = IntakeState.TRANSFER_OPEN;
    }

    public void closeTransfer(){
        posTransfer();
        claw.closeClaw();
        fsm = IntakeState.TRANSFER_CLOSED;
    }

    public void posTransfer(){
        stop();
        if (!(arm.fsm == Arm.ArmState.TRANSFER)) arm.transferPos();
        fsm = IntakeState.TRANSFER_STOP;
    }

}
