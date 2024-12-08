package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class Slides {
    private final DcMotor slidesLeft, slidesRight;
    //sets limits of slides extension
    private static final int UPPER_BOUND = 52;
    private static final int LOWER_BOUND = -4880;

    /**
     * Initializes a Slides instance.
     * @param map {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Slides(HardwareMap map) {
        slidesLeft = map.get(DcMotor.class, "slidesLeftMotor");
        slidesRight = map.get(DcMotor.class, "slidesRightMotor");
        slidesRight.setDirection(DcMotorSimple.Direction.REVERSE);

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
        slidesLeft.setPower(power);
        slidesRight.setPower(power);
    }

    /**
     * Gets the encoder position of both Slide motors.
     * @return the encoder position as an average
     */
    public int getEncoders() {
        int encoder1 = slidesLeft.getCurrentPosition();
        int encoder2 = slidesRight.getCurrentPosition();
        return (encoder1 + encoder2) / 2;
    }

    /**
     * Runs one tick of the Teleop OpMode.
     * @param stickY {@link com.qualcomm.robotcore.hardware.Gamepad} 2
     * @param overrideButton {@link com.qualcomm.robotcore.hardware.Gamepad}
     * @param telemetry {@link org.firstinspires.ftc.robotcore.external.Telemetry}
     */
    public void teleopTick(double stickY, boolean overrideButton, Telemetry telemetry){
        double input = stickY;
        int pos = getEncoders();
        telemetry.addData("Slides position: ",pos);
        if(!overrideButton && ((pos > UPPER_BOUND && input > 0) || (pos < LOWER_BOUND && input < 0) )) {
            setPower(0);
        }else{
            setPower(input);
        }
    }

    /**
     * An {@link }
     */
    public class SlidesUp implements Action {
        private boolean initialized = false;
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
