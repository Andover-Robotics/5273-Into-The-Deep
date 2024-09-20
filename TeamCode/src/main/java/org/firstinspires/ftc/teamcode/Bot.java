package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Slides;
import org.firstinspires.ftc.teamcode.Movement;

/**
 * Represents the Bot.
 */
public class Bot {
    private Slides ascendingStorm;
    private Movement godlikeManeuver;

    public Bot(@NonNull HardwareMap hardwareMap) {
        ascendingStorm = new Slides(hardwareMap);
        godlikeManeuver = new Movement(hardwareMap);
    }
}
