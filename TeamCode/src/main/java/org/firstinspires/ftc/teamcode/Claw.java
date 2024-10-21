package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    private final Servo rotobot;
    public static final double OPEN_POS = 0.0,CLOSED_POS=0.0;
    public Claw(HardwareMap hardwareMap){
        rotobot = hardwareMap.get(Servo.class, "clawServo");
    }
    public void openClaw(){
        rotobot.setPosition(OPEN_POS);
    }
    public void closeClaw(){
        rotobot.setPosition(CLOSED_POS);
    }
    public void teleopTick(Gamepad gamepad2, Telemetry telemetry){
        if (gamepad2.y) {
            openClaw();
        } else if (gamepad2.x) {
            closeClaw();
        }
    }
}
