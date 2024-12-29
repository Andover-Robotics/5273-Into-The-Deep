package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class SlidesHorizontal {
    private final Servo slideServo;

    private static double expanded = 1.0, contracted = 0.0;

    public SlidesHorizontal(HardwareMap map) {
        slideServo = map.get(Servo.class, "slidesH");
    }

    public enum HSlides {
        OUT,
        IN
    }

    public HSlides fsm = HSlides.IN;

    public void close() {
        slideServo.setPosition(contracted);
        fsm = HSlides.IN;
    }

    public void open() {
        slideServo.setPosition(expanded);
        fsm = HSlides.OUT;
    }

}