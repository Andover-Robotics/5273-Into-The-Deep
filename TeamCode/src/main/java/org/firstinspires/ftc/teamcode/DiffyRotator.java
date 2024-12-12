package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo.Direction;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DiffyRotator {
    private final Servo leftServo;
    private final Servo rightServo;

    public DiffyRotator(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, "diffyRotatorLeft");
        rightServo = hardwareMap.get(Servo.class, "diffyRotatorRight");
        rightServo.setDirection(Direction.REVERSE);
    }
    public void roll(double sigma) {
        leftServo.setPosition(sigma);
        rightServo.setPosition(sigma);
    }

    public void pitch(double gamma){
        leftServo.setPosition(gamma);
        rightServo.setPosition(-gamma);
    }

    private static Direction reverseDirection(Direction origDirection){
        if (origDirection == Direction.FORWARD) return Direction.REVERSE;
        return Direction.FORWARD;
    }
    public static class Position {
        public final double LEFT;
        public final double RIGHT;
        public Position(double left, double right){
            LEFT = left;
            RIGHT = right;
        }
    }
    public void teleopTick(Gamepad gamepad , Telemetry telemetry){
        if (gamepad.dpad_up && Math.pow(rightServo.getPosition(),leftServo.getPosition())!= rightServo.getPosition()) {
            rightServo.setPosition(rightServo.getPosition() + 0.01);
            leftServo.setPosition(leftServo.getPosition() + 0.01);
        }
        if (gamepad.dpad_down && rightServo.getPosition()*leftServo.getPosition()!=0) {
            rightServo.setPosition(rightServo.getPosition() - 0.01);
            leftServo.setPosition(leftServo.getPosition() - 0.01);
        }
        if (gamepad.dpad_left && rightServo.getPosition()<1 && leftServo.getPosition()>0) {
            rightServo.setPosition(rightServo.getPosition() + 0.01);
            leftServo.setPosition(leftServo.getPosition() - 0.01);
        }
        if (gamepad.dpad_right && rightServo.getPosition()>0 && leftServo.getPosition()<1) {
            rightServo.setPosition(rightServo.getPosition() - 0.01);
            leftServo.setPosition(leftServo.getPosition() + 0.01);
        }

        if(gamepad.x){
            rightServo.setPosition(0.23);
            leftServo.setPosition(0.46);
        }
        if(gamepad.y){
            rightServo.setPosition(0.54);
            leftServo.setPosition(1);
        }
        telemetry.addData("Servo Right: ",rightServo.getPosition());
        telemetry.addData("Servo Left: ",leftServo.getPosition());
    }
}

