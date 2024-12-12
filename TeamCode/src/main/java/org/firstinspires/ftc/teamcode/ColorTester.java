package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@TeleOp(name = "Sigma Color Finder", group = "Teleop")
public class ColorTester extends LinearOpMode {
    ColorSensor ethanLazar;

    @Override
    public void runOpMode(){
        ethanLazar = hardwareMap.get(ColorSensor.class, "color");
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Light Detected:",((OpticalDistanceSensor) ethanLazar).getLightDetected());
            telemetry.addData("Red", ethanLazar.red());
            telemetry.addData("Green", ethanLazar.green());
            telemetry.addData("Blue", ethanLazar.blue());
            telemetry.update();
        }
    }
}
