package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ClawRotator {
    private final Position GROUND_POS = new Position(0,0);
    private final Position DROP_POS = new Position(0,0);
    private final Servo leftServo;
    private final Servo rightServo;

    /**
     * Initializes a ClawRotator instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public ClawRotator(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, "clawRotatorLeft");
        rightServo = hardwareMap.get(Servo.class, "clawRotatorRight");
    }

    /**
     * Turns the claw rotator to the down position.
     */
    public void goToPos(Position pos) {
        leftServo.setPosition(pos.LEFT);
        rightServo.setPosition(pos.RIGHT);
    }

    /**
     * Turns the claw rotator to the back position.
     */

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad2, Telemetry telemetry) {
        if (gamepad2.left_bumper) {
            goToPos(GROUND_POS);
        } else if (gamepad2.right_bumper) {
            goToPos(DROP_POS);
        }
    }
    private static class Position {
        public final int LEFT;
        public final int RIGHT;
        public Position(int left, int right){
            LEFT = left;
            RIGHT = right;
        }
    }
}
