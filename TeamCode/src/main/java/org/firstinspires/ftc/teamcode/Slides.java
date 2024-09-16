package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    private DcMotor slidesLeft;
    private DcMotor slidesRight;
    public Slides (HardwareMap map){
        slidesLeft = map.get(DcMotor.class, "placeholder");
        slidesRight = map.get(DcMotor.class, "placeholder");
    }
}