package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake2 {
    private final DcMotor motor;
    private final Servo servo1, servo2;
    private final static double POS_1_CLOSED = 0;
    private final static double POS_1_OPEN = 0;
    private final static double POS_2_CLOSED = 0;
    private final static double POS_2_OPEN = 0;
    public Intake2(HardwareMap map){
        motor = map.get(DcMotor.class, "intake2motor");
        servo1 = map.get(Servo.class, "intake2servo1");
        servo2 = map.get(Servo.class, "intake2servo2");
    }
    public void teleopTick(Gamepad gamepad2, Telemetry telemetry){
        if(gamepad2.a){
            servo1.setPosition(POS_1_CLOSED);
            servo2.setPosition(POS_2_CLOSED);
        }else{
            servo1.setPosition(POS_1_OPEN);
            servo2.setPosition(POS_2_OPEN);
        }
        motor.setPower(gamepad2.b ? 1 : 0); // TODO find if 1 should be replaced with -1
    }
}