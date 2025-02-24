package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class SlidesVertical {

    private int holdTarget = 0;
    private final MotorEx slidesLeft, slidesRight;
    //sets limits of slides extension
    private static final int UPPER_BOUND = 2680;
    private static final int STORAGE = -5;
    private static final int CLIP_POS = 919;
    public PIDFController pidfController;
    public final int TOLERANCE = 10;
    public static double p = 0.015, i = 0, d = 0, f = 0, staticF = 0.25;  //tune these later (thanks lightning for placeholders)
    private final double powerUp = 0.1, powerDown = 0.05, powerMin =0.2, manualDivide = 1 ;
    public  double target = 0;
    private double power;
    private final OpMode opMode;
    public double manualPower = 0;
    public boolean goingDown = false;
    private double profile_init_time = 0;
    MotionProfiler profiler = new MotionProfiler(30000,20000);


    public SlidesVertical(OpMode running) {
        opMode = running;
        slidesLeft = new MotorEx(opMode.hardwareMap, "slidesL", Motor.GoBILDA.RPM_312);
        slidesRight = new MotorEx(opMode.hardwareMap, "slidesR", Motor.GoBILDA.RPM_312);
        slidesLeft.setInverted(false);
        slidesRight.setInverted(true);
        pidfController = new PIDFController(p, i, d, f);
        pidfController.setTolerance(TOLERANCE);
        pidfController.setSetPoint(0);
        slidesRight.setRunMode(Motor.RunMode.RawPower);
        slidesLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        slidesRight.setRunMode(Motor.RunMode.RawPower);
        slidesLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void moveToLowerBound() {
        setPosition(STORAGE);
    }

    public void resetEncoders() {
        slidesLeft.resetEncoder();
        slidesRight.resetEncoder();
    }

    public void moveToUpperBound() {
        setPosition(UPPER_BOUND);
    }

/*
    public void setPower(double power) {
        slidesLeft.(power);
        slidesRight.setPower(power);
    }
*/

    public int getEncodersAverage() {
        return (int)((slidesLeft.getCurrentPosition() + slidesRight.getCurrentPosition()) / 2);
    }

    //simply moves up or down based on input from controller
    public void slidesMove(double input) {
        if (input > powerMin || input < -powerMin) {
            manualPower = input;
        } else {
            manualPower = 0;
        }
    }

    // scrappy holdposition replaced with more useful periodic method (thanks lightning!!!)
    /*
    public void holdPosition() {
        double kP = 0.002;  // Adjust based on testing
        int error = holdTarget - getEncoders();

        if (Math.abs(error) > 5 && getEncoders()>750) {  // Small tolerance
            double power = kP * error;
            power = Math.max(-0.2, Math.min(0.2, power));  // Limit power range
            setPower(power);
        } else {
            setPower(0);  // Stop adjusting if within range
        }
    }
*/
    public void moveToTopBucketPos() {
        setPosition(UPPER_BOUND);
    }

    public void moveToRungClippingPos() {   // the highest one
    setPosition(CLIP_POS);
    }

    public void clipSpecimenVertSlides() {  // pulls vert slides down to clip it
        setPosition(CLIP_POS - 100);
    }

    public void goUpForSpecimenIntake() {
        setPosition(STORAGE + 100);
    }

    //moves based on position inputted
    public void setPosition(int targetPosition) {
        slidesLeft.setRunMode(Motor.RunMode.RawPower);
        slidesLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        slidesRight.setRunMode(Motor.RunMode.RawPower);
        slidesRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        pidfController = new PIDFController(p, i, d, f);
        pidfController.setTolerance(TOLERANCE);
        resetProfiler();
        profiler.init_new_profile(slidesLeft.getCurrentPosition(), targetPosition);
        profile_init_time = opMode.time;
        goingDown = targetPosition > target;
        target = targetPosition;
    }


    public double getCurrent() {
        return slidesLeft.motorEx.getCurrent(CurrentUnit.MILLIAMPS) + slidesRight.motorEx.getCurrent(CurrentUnit.MILLIAMPS);
    }


    public int getPosition() {
        return slidesLeft.getCurrentPosition();
    }

    public void resetProfiler() {
        profiler = new MotionProfiler(30000, 20000);
    }


    public void periodic() {
        slidesRight.setInverted(false);
        slidesLeft.setInverted(true);
        pidfController.setPIDF(p, i, d, f);
        double dt = opMode.time - profile_init_time;
        if (!profiler.isOver()) {
            pidfController.setSetPoint(profiler.motion_profile_pos(dt));
            power = powerUp * pidfController.calculate(slidesLeft.getCurrentPosition());
            if (goingDown) {
                power = powerDown * pidfController.calculate(slidesLeft.getCurrentPosition());
            }
            slidesLeft.set(power);
            slidesRight.set(power);
        } else {
            if (profiler.isDone()) {
                profiler = new MotionProfiler(30000, 20000);
            }
            if (manualPower != 0) {
                pidfController.setSetPoint(slidesLeft.getCurrentPosition());
                slidesLeft.set(manualPower / manualDivide);
                slidesRight.set(manualPower / manualDivide);
            } else {
                power = staticF * pidfController.calculate(slidesLeft.getCurrentPosition());
                slidesLeft.set(power);
                slidesRight.set(power);
            }
        }
    }
}
