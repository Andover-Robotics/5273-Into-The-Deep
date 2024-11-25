package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ClawRotator {
    private final double BACK_POS_LEFT = 0, BACK_POS_RIGHT = 0, DEPOSIT_POS_LEFT = 0, DEPOSIT_POS_RIGHT = 0;
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
    public void back() {
        leftServo.setPosition(BACK_POS_LEFT);
        rightServo.setPosition(BACK_POS_RIGHT);
    }

    /**
     * Turns the claw rotator to the back position.
     */
    public void deposit() {
        leftServo.setPosition(DEPOSIT_POS_LEFT);
        rightServo.setPosition(DEPOSIT_POS_RIGHT);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad2, Telemetry telemetry) {
        if (gamepad2.left_bumper) {
            back();
        } else if (gamepad2.right_bumper) {
            deposit();
        }
    }
}
