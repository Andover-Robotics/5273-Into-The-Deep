package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@TeleOp(name = "Color Finder", group = "Teleop")
public class ColorTester extends LinearOpMode {
    ColorSensor colorSensor;

    @Override
    public void runOpMode(){
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Light Detected:",((OpticalDistanceSensor) colorSensor).getLightDetected());
            telemetry.addData("Red", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue", colorSensor.blue());
            telemetry.update();
        }
    }
}
