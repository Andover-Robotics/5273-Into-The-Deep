package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Movement {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    public Movement (HardwareMap map){
        leftFront = map.get(DcMotor.class, "driveFL");
        leftBack = map.get(DcMotor.class, "driveBL");
        rightFront = map.get(DcMotor.class, "driveFR");
        rightBack = map.get(DcMotor.class, "driveBR");
    }
}
