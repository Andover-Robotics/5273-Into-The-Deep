package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Old Main Teleop", group = "main")
public class OldMainTeleop extends LinearOpMode {
    private static final double TICKS_PER_REV = 8192;
    private static final double WHEEL_DIAMETER = 35 / 25.4;
    private static final double LATERAL_DISTANCE = 8.78;

    public double ticks_to_inches(double ticks) {
        return (ticks / TICKS_PER_REV) * WHEEL_DIAMETER * Math.PI;
    }

    @Override
    public void runOpMode() {

        double dt = 0.001;
        int spread = 5;

        DcMotor leftFrontDrive  = hardwareMap.get(DcMotor.class, "driveFL");
        DcMotor leftBackDrive  = hardwareMap.get(DcMotor.class, "driveBL");
        DcMotor rightFrontDrive = hardwareMap.get(DcMotor.class, "driveFR");
        DcMotor rightBackDrive = hardwareMap.get(DcMotor.class, "driveBR");

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        int rightEncoderVal = rightFrontDrive.getCurrentPosition();
        int leftEncoderVal = -rightBackDrive.getCurrentPosition();
        int lateralEncoderVal = leftFrontDrive.getCurrentPosition();

        int lastRightEncoderVal = rightEncoderVal;
        int lastLeftEncoderVal = leftEncoderVal;
        int lastLateralEncoderVal = lateralEncoderVal;

        double[] thetas = new double[spread*2+1];
        for(int i = -spread; i <= spread; i++) {
            thetas[i+spread]=0.0;
        }

        double[] lateral_distances = new double[spread*2+1];
        for(int i = -spread; i <= spread; i++) {
            lateral_distances[i+spread]=LATERAL_DISTANCE + i * dt;
        }


//        double theta = 0.0;

        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial + lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial - lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }
            // hello

            // Send calculated power to wheels
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            rightEncoderVal = rightFrontDrive.getCurrentPosition();
            leftEncoderVal = -rightBackDrive.getCurrentPosition();
            lateralEncoderVal = leftFrontDrive.getCurrentPosition();

            int deltaRightEncoderVal = rightEncoderVal - lastRightEncoderVal;
            int deltaLeftEncoderVal = leftEncoderVal - lastLeftEncoderVal;

            double inchesTraveled = (ticks_to_inches(deltaRightEncoderVal) + ticks_to_inches(deltaLeftEncoderVal)) / 2;
            double[] phis = new double[spread * 2 + 1];
            for(int i = 0; i < spread * 2 + 1; i++){
                phis[i]=(ticks_to_inches(deltaRightEncoderVal)  - ticks_to_inches(deltaLeftEncoderVal)) / lateral_distances[i];
            }
//            double phi = (ticks_to_inches(deltaRightEncoderVal)  - ticks_to_inches(deltaLeftEncoderVal)) / LATERAL_DISTANCE;

            telemetry.addData("left encoder", leftEncoderVal);
            telemetry.addData("right encoder", rightEncoderVal);
            telemetry.addData("lateral encoder", lateralEncoderVal);
            telemetry.addData("inches traveled", inchesTraveled);

//            double[] thetas2 = new double[thetas.size()];
//            for(int i = 0; i < thetas2.length; i++){
//                thetas2[i] = Math.abs(thetas.get(i)) % (2 * Math.PI) * (thetas.get(i) < 0 ? -1 : 1);
//                if(thetas2[i] > Math.PI){
//                    thetas2[i] -= 2 * Math.PI;
//                }else if(thetas2[i] < -Math.PI){
//                    thetas2[i] += 2 * Math.PI;
//                }
//            }

            telemetry.addData("thetas", thetas);

//            theta += phi;
            for(int i = 0; i < spread * 2 + 1; i++) {
                thetas[i] = thetas[i] + phis[i];
            }
            lastLateralEncoderVal = lateralEncoderVal;
            lastLeftEncoderVal = leftEncoderVal;
            lastRightEncoderVal = rightEncoderVal;


            telemetry.update();


        }


    }
}