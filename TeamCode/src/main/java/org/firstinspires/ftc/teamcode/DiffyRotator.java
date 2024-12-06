package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo.Direction;

public class DiffyRotator {
    private final Servo leftServo;
    private final Servo rightServo;

    public DiffyRotator(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, "diffyRotatorLeft");
        rightServo = hardwareMap.get(Servo.class, "diffyRotatorRight");
    }
    public void roll(double sigma) {
        leftServo.setPosition(sigma);
        rightServo.setPosition(-sigma);
    }

    public void pitch(double gamma){
        leftServo.setPosition(gamma);
        rightServo.setPosition(gamma);
    }

    public void zero() {
        leftServo.setPosition(0);
        rightServo.setPosition(0);
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
}

