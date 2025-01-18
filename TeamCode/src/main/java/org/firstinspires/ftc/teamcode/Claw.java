package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.core.RotatedRect;

/**
 * Represents the claw mechanism of our robot.
 */
public class Claw {
    private final Servo servo;
    private final DiffyRotator diffyRotator;
    public static double openPos, closedPos;
    private ColorSensor colorSensor;
    private Camera camera;
    /**
     * Initializes a Claw instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap, double open, double closed, String name, String nameL, String nameR, Camera dCamera)
    {
        servo = hardwareMap.get(Servo.class, name);
        diffyRotator = new DiffyRotator(hardwareMap, nameL, nameR);
        openPos = open;
        closedPos = closed;
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        camera = dCamera;
    }

    public void clawRollPitch(double rollDeg, double pitchDeg) {
        diffyRotator.rollPitch(rollDeg,pitchDeg);}

    public void clawRoll(double rollDeg){
        diffyRotator.rollToDegrees(rollDeg);}

    public void clawPitch(double pitchDeg){
        diffyRotator.pitchToDegrees(pitchDeg);}
    /**
     * Opens the claw.
     */
    public void openClaw(){
        servo.setPosition(openPos);
    }

    /**
     * Closes the claw.
     */
    public void closeClaw(){
        servo.setPosition(closedPos);
    }

    public void setClaw(double pos) {
        servo.setPosition(pos);
    }

    public double getRoll(){
        return(diffyRotator.roll);
    }
    public double getPitch(){
        return(diffyRotator.pitch);
    }

    public double getClawPos(){
        return(servo.getPosition());
    }

    public void rollActiveClaw(boolean left, boolean right){
        if (left && !right) clawRoll(diffyRotator.roll+15);
        else if (right && !left) clawRoll(diffyRotator.roll+15);
    }
    public void pitchActiveClaw(boolean up, boolean down){
        if (up && !down) clawPitch(diffyRotator.roll+5);
        else if (down && !up) clawPitch(diffyRotator.roll+5);
    }
    public boolean sampleFound() {
        RotatedRect result = camera.getResult();
        return(result != null);
    }
    public void toSamplePosition() {
        RotatedRect result = camera.getResult();
        if (result != null) {
            // TODO roll claw position down (after testing)
            double angle = result.angle;
            if (result.size.width > result.size.height) angle += 90;
            clawRoll(angle);
        }
    }

    // sample colors red, blue and yellow yellow = #FFFF00
    // This means that the range for each color value is from 0 to 4095 (2^12 = 4096).
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
