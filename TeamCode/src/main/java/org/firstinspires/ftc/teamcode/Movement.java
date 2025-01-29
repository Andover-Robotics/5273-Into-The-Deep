package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.auto.MecanumDrive.PARAMS;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.auto.ThreeDeadWheelLocalizer;

/**
 * Represents the drivetrain.
 */
public class Movement {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    private final ThreeDeadWheelLocalizer localizer;

    /**
     * Initializes a Movement instance.
     * @param map {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Movement(@NonNull HardwareMap map){
        leftFront = map.get(DcMotor.class, "leftFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightFront = map.get(DcMotor.class, "rightFront");
        rightBack = map.get(DcMotor.class, "rightBack");

        localizer = new ThreeDeadWheelLocalizer(map, PARAMS.inPerTick);

        //reversed motor to reflect installation

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // tick for teleop

    public void teleopTick(double leftStickX, double leftStickY, double rightStickX, double trigger, Telemetry telemetry){
        double axial = -leftStickY * (1 - trigger * 0.75);  // Note: pushing stick forward gives negative value
        double lateral = leftStickX * (1 - trigger * 0.75);
        double yaw = -rightStickX * (1 - trigger * 0.75);

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;


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
        localizer.update();
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
