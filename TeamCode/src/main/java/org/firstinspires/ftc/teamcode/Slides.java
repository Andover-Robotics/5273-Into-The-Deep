package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheAutonomousNavigator}
 */
public class Slides {
    private DcMotor slidesLeft;
    private DcMotor slidesRight;
    public Slides (HardwareMap map){
        slidesLeft = map.get(DcMotor.class, "placeholder");
        slidesRight = map.get(DcMotor.class, "placeholder");
    }
    public class SlidesUp implements Action {
        private boolean initialized = false;

        public boolean run(@NonNull TelemetryPacket quantumPulseDataStream){
            return true;
        }
    }
    public Action ascendantSurge() {
        return new SlidesUp();
    }
}
