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
    private final Slides ascendingStorm;
    private final Movement godlikeManeuver;
    private final CRServo intake;

    public Bot(@NonNull HardwareMap hardwareMap) {
        ascendingStorm = new Slides(hardwareMap);
        godlikeManeuver = new Movement(hardwareMap);
        intake = hardwareMap.get(CRServo.class, "intake");
    }
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        godlikeManeuver.teleopTick(gamepad1, telemetry);
        intake.setPower(gamepad2.a ? 1 : 0);        // TODO: figure out if intake direction needs to be reversed
    }
}
