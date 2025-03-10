package org.firstinspires.ftc.teamcode.teleop.subsystems;

import static org.firstinspires.ftc.teamcode.auto.miscRR.MecanumDrive.PARAMS;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.auto.miscRR.ThreeDeadWheelLocalizer;

/**
 * Represents the drivetrain.
 */
public class Movement {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    private final ThreeDeadWheelLocalizer localizer;
    private final double STRAFE_MULTIPLIER = 0.8, ROTATION_MULTIPLIER = 0.3;

    /**
     * Initializes a Movement instance.
     * @param map {@link com.qualcomm.robotcore.hardware.HardwareMap}
     */
    public Movement(@NonNull HardwareMap map){
        leftFront = map.get(DcMotor.class, "leftFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightFront = map.get(DcMotor.class, "rightFront");
        rightBack = map.get(DcMotor.class, "rightBack");

        localizer = new ThreeDeadWheelLocalizer(map, PARAMS.inPerTick,new Pose2d(0,0,0));

        //reversed motor to reflect installation

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // tick for teleop

    public void teleopTick(double leftStickX, double leftStickY, double rightStickX,/* boolean toggle,*/ Telemetry telemetry){
        double trigger = 0;// toggle ? 0.5 : 1.0;
        double axial = -leftStickY * (STRAFE_MULTIPLIER);  // forward back strafing
        double lateral = -leftStickX * (STRAFE_MULTIPLIER); //side to side strafing
        double yaw = -rightStickX * (ROTATION_MULTIPLIER); //heading change

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
