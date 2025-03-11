package org.firstinspires.ftc.teamcode.teleop.subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the claw mechanism of our robot.
 */
public class Claw {
    private final Servo servo;
    private final Servo roll;
    private final Servo pitch;
    public static double openPos, closedPos;
    public final double LOOSE_POS = 0.28833334;
    private ColorSensor colorSensor;
    private Camera camera;
    private static final double ROLL_MIDDLE = 0.5;
    private static final double ROLL_90_CLOCKWISE = 0.158;
    private static final double ROLL_90_COUNTERCLOCKWISE = 0.83;
    private static final double ROLL_45_CLOCKWISE = (ROLL_MIDDLE + ROLL_90_CLOCKWISE) / 2;
    private static final double ROLL_45_COUNTERCLOCKWISE = (ROLL_MIDDLE + ROLL_90_COUNTERCLOCKWISE) / 2;
    private static final double PITCH_MIDDLE = 0.5294;
    private static final double PITCH_TRANSFER = 0.835;
    private static final double PITCH_90_DOWN = 0.1383;
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
        TRANSFER,
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

    public void setPitch(double pitchPos) {pitch.setPosition(pitchPos);}

    public void positionalActiveRollPitch(GamepadEx gamepad, Telemetry telemetry) {
        if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            currentRoll = Utils.advance(currentRoll, new Integer[]{0, 45, 90, -45}, 1);
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
            currentRoll = Utils.advance(currentRoll, new Integer[]{0, 45, 90, -45}, -1);
        }
        roll.setPosition(Utils.map(currentRoll,
                new Integer[]{0, 45, 90, -45},
                new Double[]{ROLL_45_CLOCKWISE, ROLL_90_CLOCKWISE, ROLL_45_COUNTERCLOCKWISE, ROLL_MIDDLE}
        ));

        telemetry.addData("roll", currentRoll);
        telemetry.addData("rollServo", roll.getPosition());


        if (gamepad.isDown(GamepadKeys.Button.DPAD_UP)) {
            pitch.setPosition(pitch.getPosition() + 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_DOWN)) {
            pitch.setPosition(pitch.getPosition() - 0.01);
        }
//        if (gamepad.isDown(GamepadKeys.Button.DPAD_LEFT)) {
//            pitch.setPosition(pitch.getPosition() + 0.01);
//        }
//        if (gamepad.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
//            pitch.setPosition(pitch.getPosition() - 0.01);
//        }

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
        roll.setPosition(Utils.map(position,
                new RollPosition[]{RollPosition.MIDDLE, RollPosition.CLOCKWISE_45, RollPosition.CLOCKWISE_90, RollPosition.COUNTERCLOCKWISE_45, RollPosition.COUNTERCLOCKWISE_90},
                new Double[]{ROLL_MIDDLE, ROLL_45_CLOCKWISE, ROLL_90_CLOCKWISE, ROLL_45_COUNTERCLOCKWISE, ROLL_90_COUNTERCLOCKWISE}
        ));
    }

    public void setPitch(PitchPosition position) {
        pitch.setPosition(Utils.map(position,
                new PitchPosition[]{PitchPosition.MIDDLE, PitchPosition.TRANSFER, PitchPosition.DOWN_90},
                new Double[]{PITCH_MIDDLE, PITCH_TRANSFER, PITCH_90_DOWN}
        ));
    }

    public void setPositions(RollPosition rollPosition, PitchPosition pitchPosition) {
        setRoll(rollPosition);
        setPitch(pitchPosition);
    }

    public boolean toSamplePosition() {
        double result = camera.getAngle();
        if (result != -1) {
            // TODO fix the angling for new claw
            int fixed = (int) Math.round(result / 45);
            roll.setPosition(Utils.map(fixed,
                    new Integer[]{0, 1, 2, 3, 4},
                    new Double[]{ROLL_MIDDLE, ROLL_45_CLOCKWISE, ROLL_90_CLOCKWISE, ROLL_45_COUNTERCLOCKWISE, ROLL_MIDDLE}
            ));
            return true;
        }
        return false;
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
