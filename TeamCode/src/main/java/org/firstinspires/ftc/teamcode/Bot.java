package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
/**
 * Represents the Bot.
 */
public class Bot {
    private final Slides slides;
    private final Movement movement;
    public double x = 0;
    public double y = 0;
    public double theta = 0;

    public Bot(@NonNull HardwareMap hardwareMap) {
        slides = new Slides(hardwareMap);
        movement = new Movement(hardwareMap);
    }
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        movement.teleopTick(gamepad1, telemetry);
    }
}
