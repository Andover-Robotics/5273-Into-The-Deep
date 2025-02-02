package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.ButtonReader;
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
    private static final double ROLL_MIDDLE = 0.5;
    private static final double ROLL_90_CLOCKWISE = 0.158;
    private static final double ROLL_90_COUNTERCLOCKWISE = 0.83;
    private static final double ROLL_45_CLOCKWISE = (ROLL_MIDDLE + ROLL_90_CLOCKWISE) / 2;
    private static final double ROLL_45_COUNTERCLOCKWISE = (ROLL_MIDDLE + ROLL_90_COUNTERCLOCKWISE) / 2;
    private static final double PITCH_MIDDLE = 0.556;
    private static final double PITCH_90_UP = 0.197;
    private static final double PITCH_90_DOWN = 0.883;
    private int currentRoll = 0;

    public enum RollPosition {
        MIDDLE,
        CLOCKWISE_45,
        CLOCKWISE_90,
        COUNTERCLOCKWISE_45,
        COUNTERCLOCKWISE_90
    }

    public enum PitchPosition {
        MIDDLE,
        UP_90,
        DOWN_90
    }


    /**
     * Initializes a Claw instance.
     *
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Claw(@NonNull HardwareMap hardwareMap, double open, double closed, Camera Camera) {
        servo = hardwareMap.get(Servo.class, "iClaw");
        roll = hardwareMap.get(Servo.class, "roll");
        pitch = hardwareMap.get(Servo.class, "pitch");
        openPos = open;
        closedPos = closed;
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        camera = Camera;
    }

    public void looseClaw() {
        servo.setPosition(LOOSE_POS);
    }

    /**
     * Opens the claw.
     */
    public void openClaw() {
        servo.setPosition(openPos);
    }

    /**
     * Closes the claw.
     */
    public void closeClaw() {
        servo.setPosition(closedPos);
    }

    public void setClaw(double pos) {
        servo.setPosition(pos);
    }

    public double getClawPos() {
        return (servo.getPosition());
    }

    public void positionalActiveRollPitch(GamepadEx gamepad, Telemetry telemetry) {
        if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            //move in 45 degree increments in the positive direction for currentRoll
            switch (currentRoll) {
                case 0:
                    roll.setPosition(ROLL_45_CLOCKWISE);
                    currentRoll = 45;
                    break;
                case 45:
                    roll.setPosition(ROLL_90_CLOCKWISE);
                    currentRoll = 90;
                    break;
                case 90:
                case -90:
                    roll.setPosition(ROLL_45_COUNTERCLOCKWISE);
                    currentRoll = -45;
                    break;
                case -45:
                default:
                    roll.setPosition(ROLL_MIDDLE);
                    currentRoll = 0;
                    break;
            }
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
            //move in 45 degree increments in the negative direction for currentRoll
            switch (currentRoll) {
                case 0:
                    roll.setPosition(ROLL_45_COUNTERCLOCKWISE);
                    currentRoll = -45;
                    break;
                case -45:
                    roll.setPosition(ROLL_90_COUNTERCLOCKWISE);
                    currentRoll = -90;
                    break;
                case -90:
                case 90:
                    roll.setPosition(ROLL_45_CLOCKWISE);
                    currentRoll = 45;
                    break;
                case 45:
                default:
                    roll.setPosition(ROLL_MIDDLE);
                    currentRoll = 0;
                    break;
            }
        }
        telemetry.addData("roll", currentRoll);
        telemetry.addData("rollServo", roll.getPosition());



        telemetry.addData("roll position: ", roll.getPosition());
        telemetry.addData("pitch position: ", pitch.getPosition());
    }

    public void setPositions(double rollPos, double pitchPos) {
//        roll.setPosition(rollPos);
//        pitch.setPosition(pitchPos);
    }

    public double getRoll() {
        return roll.getPosition();
    }

    public double getPitch() {
        return pitch.getPosition();
    }

    public void setRoll(RollPosition position) {
        switch (position) {
            case MIDDLE:
                roll.setPosition(ROLL_MIDDLE);
                break;
            case CLOCKWISE_45:
                roll.setPosition(ROLL_45_CLOCKWISE);
                break;
            case CLOCKWISE_90:
                roll.setPosition(ROLL_90_CLOCKWISE);
                break;
            case COUNTERCLOCKWISE_45:
                roll.setPosition(ROLL_45_COUNTERCLOCKWISE);
                break;
            case COUNTERCLOCKWISE_90:
                roll.setPosition(ROLL_90_COUNTERCLOCKWISE);
                break;
        }
    }

    public void setPitch(PitchPosition position) {
        switch (position) {
            case MIDDLE:
                pitch.setPosition(PITCH_MIDDLE);
                break;
            case UP_90:
                pitch.setPosition(PITCH_90_UP);
                break;
            case DOWN_90:
                pitch.setPosition(PITCH_90_DOWN);
                break;
        }
    }

    public void setPositions(RollPosition rollPosition, PitchPosition pitchPosition) {
        setRoll(rollPosition);
        setPitch(pitchPosition);
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
    public boolean hasSample() {
        int red, blue, green;
        red = colorSensor.red();
        blue = colorSensor.blue();
        green = colorSensor.green();
        return (
                (red >= 2500 && blue <= 500 && green <= 500) ||
                        (red <= 500 && blue >= 2500 && green <= 500) ||
                        (red >= 2500 && blue <= 500 && green >= 2500));
    }
}
