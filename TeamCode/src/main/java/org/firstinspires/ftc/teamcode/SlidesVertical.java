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

    //makes sure FSM is updated based on encoder positions
    public void updateFSM(){
        if (getEncoders()<=LOWER_BOUND+5) fsm = VSlides.LOWERED;
        if (getEncoders()>=UPPER_BOUND-5) fsm = VSlides.RAISED;
        else fsm = VSlides.MIDDLE;
    }


    public void moveToLowerBound() {
        if(!(fsm == VSlides.LOWERED)) setPosition(LOWER_BOUND);
        fsm = VSlides.LOWERED;
    }

    public void resetEncoders(){
        slidesRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void moveToUpperBound() {
        if(!(fsm== VSlides.RAISED)) setPosition(UPPER_BOUND);
        fsm = VSlides.RAISED;
    }


    public void setPower(double power) {
        slidesLeft.setPower(power);
        slidesRight.setPower(power);
    }


    public int getEncoders() {
        return (int)((slidesLeft.getCurrentPosition() + slidesRight.getCurrentPosition()) / 2);
    }

    //simply moves up or down based on input from controller
    public void slidesMove(double input, boolean overrideButton, Telemetry telemetry) {
        int pos = getEncoders();
        telemetry.addData("Slides position: ", pos);

        if (!overrideButton && ((pos > UPPER_BOUND && input > 0) || (pos < LOWER_BOUND && input < 0))) {
            setPower(0);
        } else {
            setPower(input);
        }

        if (Math.abs(input) < 0.05) {  // If no input from the controller
            holdPosition();
        } else {
            holdTarget = getEncoders();  // Save position to hold
        }

        updateFSM();
    }

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

    public void moveToTopBucketPos() {
        setPosition(UPPER_BOUND);
        fsm = VSlides.MIDDLE;
    }

    public void moveToRungClippingPos() {   // the highest one
    setPosition(CLIP_POS);
        fsm = VSlides.MIDDLE;
    }

    public void clipSpecimenVertSlides() {  // pulls vert slides down to clip it
        setPosition(CLIP_POS - 100);
        fsm = VSlides.MIDDLE;
    }

    public void goUpForSpecimenIntake() {
        setPosition(LOWER_BOUND + 100);
    }

    //moves based on position inputted
    public void setPosition(int targetPosition) {
        final int TOLERANCE = 5;
        int error = targetPosition - getEncoders();

        if (Math.abs(error) > TOLERANCE) {
            double power = Math.max(0.1, Math.min(1.0, Math.abs(error) / 1000.0));
            if (error > 0) {
                setPower(power);
            } else {
                setPower(-power);
            }
        } else {
            setPower(0);
        }
        updateFSM();
    }
}
