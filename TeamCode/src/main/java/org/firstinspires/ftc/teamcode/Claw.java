package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
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
    private ColorSensor colorSensor;
    /**
     * Initializes a Claw instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap, double open, double closed, String name, String nameL, String nameR)
    {
        rotobot = hardwareMap.get(Servo.class, name);
        andrewLu = new DiffyRotator(hardwareMap, nameL, nameR);
        openPos = open;
        closedPos = closed;
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
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
        // sample colors red, blue and yellow yellow = #FFFF00
     //This means that the range for each color value is from 0 to 4095 (2^12 = 4096).
    //TODO: fine tune values
    public boolean hasSample()
    {
        int red,blue,green;
        red = colorSensor.red();
        blue = colorSensor.blue();
        green = colorSensor.green();
        return(
                (red >= 2500 && blue <= 500 && green <= 500) ||
                (red <= 500 && blue >= 2500 && green <= 500) ||
                 (red >= 2500 && blue <= 500 && green >= 2500));
    }
}
