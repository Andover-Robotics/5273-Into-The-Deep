package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
/**
 * Represents the Bot.
 */
public class Bot {
    private final Slides ascendingStorm;
    private final Movement godlikeManeuver;

    public Bot(@NonNull HardwareMap hardwareMap) {
        ascendingStorm = new Slides(hardwareMap);
        godlikeManeuver = new Movement(hardwareMap);
    }
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        godlikeManeuver.teleopTick(gamepad1, telemetry);
    }
}
