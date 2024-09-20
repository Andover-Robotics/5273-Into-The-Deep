package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Represents the drivetrain.
 */
public class Movement {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    public Movement (HardwareMap map){
        leftFront = map.get(DcMotor.class, "leftFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightFront = map.get(DcMotor.class, "rightFront");
        rightBack = map.get(DcMotor.class, "rightBack");
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
