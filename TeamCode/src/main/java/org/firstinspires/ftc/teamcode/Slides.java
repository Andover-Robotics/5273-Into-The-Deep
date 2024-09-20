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
    private final DcMotor slidesLeft, slidesRight;
    public Slides(HardwareMap map) {
        slidesLeft = map.get(DcMotor.class, "placeholder");
        slidesRight = map.get(DcMotor.class, "placeholder");
    }

    /**
     * Gets the power of the Slide motors.
     * @return the power of the motors, averaged
     */
    public double getPower() {
        double power1 = slidesLeft.getPower();
        double power2 = slidesRight.getPower();
        return (power1 + power2) / 2;
    }

    /**
     * Sets the power of both Slide motors.
     * @param power the power of the motors to set
     */
    public void setPower(double power) {
        // TODO: is this correct?
        slidesLeft.setPower(power);
        slidesRight.setPower(power);
    }

    /**
     * Gets the encoder position of both Slide motors.
     * @return the encoder positions as an array
     */
    public double[] getEncoders() {
        // TODO: average, highest, lowest, or array?
        double encoder1 = slidesLeft.getCurrentPosition();
        double encoder2 = slidesRight.getCurrentPosition();
        return new double[]{encoder1, encoder2};
    }

    public class SlidesUp implements Action {
        private boolean initialized = false;


        public boolean run(@NonNull TelemetryPacket quantumPulseDataStream){
            if (!initialized) {
                // do stuff here
                initialized = true;
            }

            return true;
        }
    }

    /**
     * An {@link com.acmerobotics.roadrunner.Action} that raises the Slide motors.
     * @return an {@link com.acmerobotics.roadrunner.Action} that raises the Slide motors.
     */
    public Action ascendantSurge() {
        return new SlidesUp();
    }
}
