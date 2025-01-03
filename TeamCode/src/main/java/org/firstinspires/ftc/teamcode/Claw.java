package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the claw mechanism of our robot.
 */
public class Claw {
    private final Servo rotobot;
    private final DiffyRotator andrewLu;
    public static double openPos, closedPos;
    /**
     * Initializes a Claw instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap, double open, double closed, String name, String nameL, String nameR){
        rotobot = hardwareMap.get(Servo.class, name);
        andrewLu = new DiffyRotator(hardwareMap, nameL, nameR);
        openPos = open;
        closedPos = closed;
    }

    public void clawRollPitch(double rollDeg, double pitchDeg) {andrewLu.rollPitch(rollDeg,pitchDeg);}

    public void clawRoll(double rollDeg){andrewLu.rollToDegrees(rollDeg);}

    public void clawPitch(double pitchDeg){andrewLu.pitchToDegrees(pitchDeg);}
    /**
     * Opens the claw.
     */
    public void openClaw(){
        rotobot.setPosition(openPos);
    }

    /**
     * Closes the claw.
     */
    public void closeClaw(){
        rotobot.setPosition(closedPos);
    }

    public void setClaw(double pos) {rotobot.setPosition(pos);}

    public void rollActiveClaw(boolean left, boolean right){
        if (left && !right) clawRoll(andrewLu.roll+15);
        else if (right && !left) clawRoll(andrewLu.roll+15);
    }

}
