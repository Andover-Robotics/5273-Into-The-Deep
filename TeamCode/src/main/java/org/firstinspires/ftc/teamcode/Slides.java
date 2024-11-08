package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class Slides {
    private final DcMotor slidesLeft, slidesRight;
    private static final int UPPER_BOUND = 6000;
    private static final int LOWER_BOUND = -6000;

    /**
     * Initializes a Slides instance.
     * @param map {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Slides(@NonNull HardwareMap map) {
        // TODO: reverse one of them or both of them
        slidesLeft = map.get(DcMotor.class, "slidesLeftMotor");
        slidesRight = map.get(DcMotor.class, "slidesRightMotor");
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
        // 17/20 is a temporary scale factor TODO: remove scale factor once we use new drivetrain
        slidesLeft.setPower(power*(17d/20d));
        slidesRight.setPower(power);
    }

    /**
     * Gets the encoder position of both Slide motors.
     * @return the encoder position as an average
     */
    public double getEncoders() {
        // TODO: average, highest, lowest, or array?
        double encoder1 = slidesLeft.getCurrentPosition();
        double encoder2 = slidesRight.getCurrentPosition();
        return (encoder1 + encoder2) / 2;
    }

    /**
     * Runs one tick of the Teleop Op Mode.
     * @param gamepad2 {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(Gamepad gamepad2, Telemetry telemetry){
        int pos = (int) getEncoders();
        if (!gamepad2.b && (pos > UPPER_BOUND || pos < LOWER_BOUND)) {
            setPower(0);
        } else {
            // TODO: reverse input?
            setPower(gamepad2.left_stick_y);
        }
    }

    /**
     * An {@link com.acmerobotics.roadrunner.Action} that raises the Slide motors.
     */
    public class SlidesUp implements Action {
        private boolean initialized = false;

        /**
         * Runs the Action.
         * @param quantumPulseDataStream {@link com.acmerobotics.dashboard.telemetry.TelemetryPacket}
         * @return If the Action should still be running.
         * @see org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator
         */
        public boolean run(@NonNull TelemetryPacket quantumPulseDataStream){
            if (!initialized) {
                // do stuff here
                setPower(1);
                initialized = true;
            }

            double pos = getEncoders();
            quantumPulseDataStream.put("slidePos", pos);
            if (pos < 3000) {
                return true;
            } else {
                setPower(0);
                return false;
            }
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
