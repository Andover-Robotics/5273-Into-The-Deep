package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
/**
 * Represents the Bot.
 */
public class Bot {
    //private final Slides ascendingStorm;
    private final Movement godlikeManeuver;
    //private final Claw claw;
    private final Slides slides;
    private final Pivot pivot;
    private final ClawRotator clawRotator;

    /**
     * Initializes a Bot instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Bot(@NonNull HardwareMap hardwareMap) {
        //ascendingStorm = new Slides(hardwareMap);
        godlikeManeuver = new Movement(hardwareMap);
        //claw = new Claw(hardwareMap);
        slides = new Slides(hardwareMap);
        pivot = new Pivot(hardwareMap);
        clawRotator = new ClawRotator(hardwareMap);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        godlikeManeuver.teleopTick(gamepad1, telemetry);
        slides.teleopTick(gamepad2, telemetry);
        //claw.teleopTick(gamepad2, telemetry);
        pivot.teleopTick(gamepad2, telemetry);
        clawRotator.teleopTick(gamepad2, telemetry);
    }
}
