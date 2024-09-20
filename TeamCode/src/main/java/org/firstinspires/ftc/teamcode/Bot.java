package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Slides;
import org.firstinspires.ftc.teamcode.Movement;

/**
 * Represents the Bot.
 */
public class Bot {
    private Slides slides;
    private Movement movement;
    // Does this go in the Movement class?
    public double x = 0;
    public double y = 0;
    public double theta = 0;

    public Bot(@NonNull HardwareMap hardwareMap) {
        slides = new Slides(hardwareMap);
        movement = new Movement(hardwareMap);
    }
}
