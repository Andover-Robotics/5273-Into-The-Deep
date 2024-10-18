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
//    private final CRServo intake;
    private final Intake2 intake;

    /**
     * Initializes a Bot instance.
     * @param hardwareMap {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Bot(@NonNull HardwareMap hardwareMap) {
        ascendingStorm = new Slides(hardwareMap);
        godlikeManeuver = new Movement(hardwareMap);
//        intake = hardwareMap.get(CRServo.class, "intake");
        intake = new Intake2(hardwareMap);
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param gamepad1 {@link com.qualcomm.robotcore.hardware.Gamepad} 1
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        godlikeManeuver.teleopTick(gamepad1, telemetry);
//        intake.setPower(gamepad2.a ? 1 : (gamepad2.b ? -1 : 0));
        intake.teleopTick(gamepad2, telemetry);
    }
}
