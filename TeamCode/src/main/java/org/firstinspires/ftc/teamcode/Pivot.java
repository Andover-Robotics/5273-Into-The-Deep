package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Pivot mechanism, representing both Pivot motors.
 */

public class Pivot {
    private final DcMotor pivotLeft, pivotRight;
    private static final int UPPER_BOUND = 6000;
    private static final int LOWER_BOUND = -6000;

    private static final double EQUALIBRIUM_MAGNITUDE = 0.2;
    private static final double BRAKING_MAGNITUDE = 0.2;
    private static final double INPUT_MAGNITUDE = 0.3;

    private int lastPos;

    private final ElapsedTime dt;

    public Pivot(HardwareMap map) {
        pivotLeft = map.get(DcMotor.class, "pivotLeft");
        pivotRight = map.get(DcMotor.class, "pivotRight");

        pivotLeft.setDirection(DcMotor.Direction.REVERSE);
        pivotRight.setDirection(DcMotor.Direction.FORWARD);

        pivotLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pivotRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pivotLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivotRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivotLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pivotRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dt = new ElapsedTime();
        dt.reset();

        lastPos = (int)getEncoders();
    }

    /**
     * Gets the power of the Pivot motors.
     *
     * @return the power of the motors, averaged
     */
    public double getPower() {
        double power1 = pivotLeft.getPower();
        double power2 = pivotRight.getPower();
        return (power1 + power2) / 2;
    }

    /**
     * Sets the power of both Pivot motors.
     *
     * @param power the power of the motors to set
     */
    public void setPower(double power) {
        pivotLeft.setPower(power);
        pivotRight.setPower(power);
    }

    /**
     * Gets the encoder position of both Pivot motors.
     *
     * @return the encoder position as an average
     */

    public double getEncoders() {
        double encoder1 = pivotLeft.getCurrentPosition();
        double encoder2 = pivotRight.getCurrentPosition();
        return (encoder1 + encoder2) / 2;
    }


    public void teleopTick(Gamepad gamepad2, Telemetry telemetry) {
        int pos = (int) getEncoders();
        double dtMills = dt.milliseconds();
        dt.reset();
        int dxTicks = lastPos - pos;
        lastPos = pos;
        double vel = (double)dxTicks/dtMills;


        double input = gamepad2.right_stick_y;

        double equalibrium = Math.sin((-(Math.PI * pos) / 500)* EQUALIBRIUM_MAGNITUDE);

        double additionalBraking = input == 0.0 ? vel * BRAKING_MAGNITUDE : 0;


        setPower(equalibrium + input * INPUT_MAGNITUDE + additionalBraking);
    }
}
