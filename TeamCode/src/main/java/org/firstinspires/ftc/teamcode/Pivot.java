package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Pivot mechanism, representing both Pivot motors.
 */

public class Pivot {
    private final DcMotor pivot;
    private static final int UPPER_BOUND = 250;
    private static final int LOWER_BOUND = -250;

    private static final double EQUILIBRIUM_MAGNITUDE = 0.2; // for counteracting gravity
    private static final double BRAKING_MAGNITUDE = 0.2; // for smoothness when it stops going up/down
    private static final double INPUT_MAGNITUDE = 0.3;

    private int lastPos;

    private final ElapsedTime dt; // delta t

    public Pivot(HardwareMap map) {

        //extension 0
        pivot = map.get(DcMotor.class, "pivot");

        pivot.setDirection(DcMotor.Direction.FORWARD);

        pivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
        double power = pivot.getPower();
        return power;
    }

    /**
     * Sets the power of both Pivot motors.
     *
     * @param power the power of the motors to set
     */
    public void setPower(double power) {
        pivot.setPower(power);
    }

    /**
     * Gets the encoder position of both Pivot motors.
     *
     * @return the encoder position as an average
     */

    public double getEncoders() {
        double encoder = pivot.getCurrentPosition();
        return encoder;
    }

    public void teleopTick(Gamepad gamepad2, Telemetry telemetry) {
        int pos = (int) getEncoders();
        double input = gamepad2.right_stick_y;
        if(!gamepad2.b && ((pos > UPPER_BOUND && input > 0) || (pos < LOWER_BOUND && input < 0) )) {
            setPower(0);
        }else{
            double dtMills = dt.milliseconds();
            dt.reset();
            int dxTicks = lastPos - pos;
            lastPos = pos;
            double vel = (double)dxTicks/dtMills;
            double equilibrium = Math.sin((-(Math.PI * pos) / 500)) * EQUILIBRIUM_MAGNITUDE;
            double additionalBraking = input == 0.0 ? vel * BRAKING_MAGNITUDE : 0;
            setPower(equilibrium + input * INPUT_MAGNITUDE + additionalBraking);
        }
    }
}
