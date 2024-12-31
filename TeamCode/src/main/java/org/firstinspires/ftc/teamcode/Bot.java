package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.old.Pivot;

/**
 * Represents the Bot.
 */
public class Bot {
    private final Movement godlikeManeuver;
    private final SlidesHorizontal hSlides;
    private final Intake intake;
    private final SlidesVertical vSlides;
    private final Claw claw;

    public enum FSM{
        STARTING
    }

    public FSM fsm = FSM.STARTING;
    /**
     * Initializes a Bot instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Bot(@NonNull HardwareMap hardwareMap) {
        // initializations:

        // intake:
        hSlides = new SlidesHorizontal(hardwareMap);
        intake = new Intake(hardwareMap);

        // outtake:
        vSlides = new SlidesVertical(hardwareMap);
        claw = new Claw(hardwareMap);

        godlikeManeuver = new Movement(hardwareMap);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){

    }
}
