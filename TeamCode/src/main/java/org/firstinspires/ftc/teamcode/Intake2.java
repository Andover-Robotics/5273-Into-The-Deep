package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the Bot's intake.
 */
public class Intake2 {
    private final DcMotor motor;
    private final Servo servo1, servo2;
    private final static double POS_1_CLOSED = 0;
    private final static double POS_1_OPEN = 0;
    private final static double POS_2_CLOSED = 0;
    private final static double POS_2_OPEN = 0;

    /**
     * Initializes a Intake2 instance.
     * @param map {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Intake2(@NonNull HardwareMap map){
        motor = map.get(DcMotor.class, "intake2motor");
        servo1 = map.get(Servo.class, "intake2servo1");
        servo2 = map.get(Servo.class, "intake2servo2");
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
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
