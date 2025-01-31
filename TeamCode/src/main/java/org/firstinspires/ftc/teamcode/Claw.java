package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.RotatedRect;

/**
 * Represents the claw mechanism of our robot.
 */
public class Claw {
    private final Servo servo;
    private final Servo roll;
    private final Servo pitch;
    public static double openPos, closedPos;
    public final double LOOSE_POS = 0.04;
    private ColorSensor colorSensor;
    private Camera camera;
    /**
     * Initializes a Claw instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap, double open, double closed, Camera Camera)
    {
        servo = hardwareMap.get(Servo.class, "iClaw");
        roll = hardwareMap.get(Servo.class, "roll");
        pitch = hardwareMap.get(Servo.class, "pitch");
        openPos = open;
        closedPos = closed;
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        camera = Camera;
    }

    public void looseClaw(){
        servo.setPosition(LOOSE_POS);
    }
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

    public double getClawPos(){
        return(servo.getPosition());
    }

    public void positionalActiveRollPitch(GamepadEx gamepad , Telemetry telemetry) {
        if (gamepad.isDown(GamepadKeys.Button.DPAD_UP)) {
            roll.setPosition(roll.getPosition() + 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_DOWN)) {
            roll.setPosition(roll.getPosition() - 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_LEFT)) {
            pitch.setPosition(pitch.getPosition() + 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
            pitch.setPosition(pitch.getPosition() - 0.01);
        }

        telemetry.addData("roll position: ",roll.getPosition());
        telemetry.addData("pitch position: ",pitch.getPosition());
    }

    public void setPositions(double rollPos, double pitchPos){
        roll.setPosition(rollPos);
        pitch.setPosition(pitchPos);
    }

    public double getRoll(){
        return roll.getPosition();
    }

    public double getPitch(){
        return pitch.getPosition();
    }

    public void toSamplePosition() {
        double result = camera.getAngle();
        if (result != -1) {
            // TODO fix the angling for new claw
            roll.setPosition(result / 180);
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
