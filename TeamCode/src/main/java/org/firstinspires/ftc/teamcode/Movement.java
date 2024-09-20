package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Represents the drivetrain.
 */
public class Movement {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    public Movement (HardwareMap map){
        leftFront = map.get(DcMotor.class, "leftFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightFront = map.get(DcMotor.class, "rightFront");
        rightBack = map.get(DcMotor.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // brake behavior the goat
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // tick for teleop
    public void teleopTick(Gamepad gamepad1, Telemetry telemetry){
        double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial + lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial - lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        double max = Math.max(Math.max(Math.max(
                Math.abs(leftFrontPower),
                Math.abs(rightFrontPower)),
                Math.abs(leftBackPower)),
                Math.abs(rightBackPower));
        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        // Send calculated power to wheels
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }
    // TODO: implement later
    /**
     * Moves the bot to a certain point with a certain orientation.
     * @param x the X location to move to
     * @param y the Y location to move to
     * @param thetaDiff the change in bot orientation
     */
    public void moveToPoint(double x, double y, double thetaDiff) {
        throw new UnsupportedOperationException();
    }

    /**
     * Orients the bot in a certain direction.
     * @param theta the angle to orient to
     */
    public void orient(double theta) {
        throw new UnsupportedOperationException();
    }
}
