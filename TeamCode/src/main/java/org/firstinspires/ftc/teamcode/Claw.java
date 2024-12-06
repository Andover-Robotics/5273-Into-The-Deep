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
    private final DiffyRotator andrewLu;
    public static final double OPEN_POS = 0.4244, CLOSED_POS = 0.3039;
    public static final double UP_POS = 0.1, DOWN_POS = 0.2;
    /**
     * Initializes a Claw instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap){
        rotobot = hardwareMap.get(Servo.class, "clawServo");
        andrewLu = new DiffyRotator(hardwareMap);
    }

    public void clawUp() {andrewLu.roll(UP_POS);}

    public void clawDown() {andrewLu.roll(DOWN_POS);}


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
     *  {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(boolean openButton, boolean closeButton, Telemetry telemetry){
        if (openButton) {
            openClaw();
        } else if (closeButton) {
            closeClaw();
        }
    }
}
