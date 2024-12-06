package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Represents the Teleop OpMode
 */
@TeleOp(name = "Diffy Zero", group = "main")
public class DiffyZero extends LinearOpMode {
    /**
     * Runs the OpMode.
     */
    @Override
    public void runOpMode() {
        DiffyRotator diff = new DiffyRotator(hardwareMap);

        waitForStart();
        diff.pitch(0);
    }
}