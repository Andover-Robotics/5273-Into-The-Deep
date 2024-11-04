package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the claw mechanism of our robot.
 */
public class Claw {
    private final Servo rotobot;
    public static final double OPEN_POS = 0.4167, CLOSED_POS = 0.2739;

    /**
     * Initializes a Claw instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap){
        rotobot = hardwareMap.get(Servo.class, "clawServo");
    }

    /**
     * Opens the claw.
     */
    public void openClaw(){
        rotobot.setPosition(OPEN_POS);
    }

    /**
     * Closes the claw.
     */
    public void closeClaw(){
        rotobot.setPosition(CLOSED_POS);
    }

    /**
     * Runs one tick of the Teleop Op Mode.
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad2, Telemetry telemetry){
        if (gamepad2.y) {
            openClaw();
        } else if (gamepad2.x) {
            closeClaw();
        }
    }
}
