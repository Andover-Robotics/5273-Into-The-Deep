package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private final CRServo intake;
    private final Arm arm;
    private static final double RUN_POWER = 1;

    public Intake (HardwareMap map) {
        intake = map.get(CRServo.class, "iServo");
        arm = new Arm(map);
    }

    public enum IntakeState {
        INTAKE_RUN,
        INTAKE_STOP,
        TRANSFER_RUN,
        TRANSFER_STOP
    }

    public IntakeState fsm = IntakeState.INTAKE_STOP;

    public void runIntake(){
        posIntake();
        intake.setPower(RUN_POWER);
        fsm = IntakeState.INTAKE_RUN;
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
        intake.setPower(-RUN_POWER);
        fsm = IntakeState.TRANSFER_RUN;
    }

    public void posTransfer(){
        stop();
        if (!(arm.fsm == Arm.ArmState.TRANSFER)) arm.transferPos();
        fsm = IntakeState.TRANSFER_STOP;
    }

}
