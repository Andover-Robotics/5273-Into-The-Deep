package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.Servo.Direction;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DiffyRotator {
    public final SimpleServo leftServo;
    public final SimpleServo rightServo;
    public double roll;
    public double pitch;

    public final double ROLL_MAX = 180, ROLL_MIN = 0;
    public final double PITCH_MAX = 100, PITCH_MIN = 0;

    public DiffyRotator(HardwareMap hardwareMap, String nameL, String nameR) {
        leftServo = new SimpleServo(hardwareMap, nameL, 0,180);
        rightServo = new SimpleServo(hardwareMap, nameR,0,180);
        leftServo.setInverted(true);
    }

    public void setRight(double pos){
        rightServo.setPosition(pos);
    }
    public void setLeft(double pos){
        leftServo.setPosition(pos);
    }

    public void teleopTick(GamepadEx gamepad , Telemetry telemetry) {
        if (gamepad.isDown(GamepadKeys.Button.DPAD_UP)) {
            rightServo.setPosition(rightServo.getPosition() + 0.01);
            leftServo.setPosition(leftServo.getPosition() + 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_DOWN)) {
            rightServo.setPosition(rightServo.getPosition() - 0.01);
            leftServo.setPosition(leftServo.getPosition() - 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_LEFT)) {
            rightServo.setPosition(rightServo.getPosition() + 0.01);
            leftServo.setPosition(leftServo.getPosition() - 0.01);
        }
        if (gamepad.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
            rightServo.setPosition(rightServo.getPosition() - 0.01);
            leftServo.setPosition(leftServo.getPosition() + 0.01);
        }

        telemetry.addData("diffy right position: ",rightServo.getPosition());
        telemetry.addData("diffy left position: ",leftServo.getPosition());

    }

    public void rollToDegrees(double rollDegrees){
        if (rollDegrees>=ROLL_MAX) rollDegrees=ROLL_MAX;
        if (rollDegrees<=ROLL_MIN) rollDegrees=ROLL_MIN;
        double deltaL = pitch + rollDegrees;
        double deltaR = pitch + (ROLL_MAX - rollDegrees); //inverse amount of degrees to the right side servo
        leftServo.turnToAngle(deltaL);
        rightServo.turnToAngle(deltaR);
        roll = rollDegrees;
    }

    public void pitchToDegrees(double pitchDegrees){
        if (pitchDegrees>=PITCH_MAX) pitchDegrees=PITCH_MAX;
        if (pitchDegrees<=PITCH_MIN) pitchDegrees=PITCH_MIN;
        double deltaL = roll + pitchDegrees;
        double deltaR = pitchDegrees + (PITCH_MAX - roll); //inverse amount of degrees to the right side servo
        leftServo.turnToAngle(deltaL);
        rightServo.turnToAngle(deltaR);
        pitch = pitchDegrees;
    }

    public void rollPitch(double rollDegrees, double pitchDegrees){
        pitchDegrees=pitchDegrees%360;
        rollDegrees = Math.max(ROLL_MIN, Math.min(ROLL_MAX, rollDegrees));
        pitchDegrees = Math.max(PITCH_MIN, Math.min(PITCH_MAX, pitchDegrees));
        double leftAngle = pitchDegrees + (rollDegrees);
        double rightAngle = pitchDegrees + (ROLL_MAX - (rollDegrees));
        leftServo.turnToAngle(leftAngle);
        rightServo.turnToAngle(rightAngle);
        roll = rollDegrees;
        pitch = pitchDegrees;
    }

}

