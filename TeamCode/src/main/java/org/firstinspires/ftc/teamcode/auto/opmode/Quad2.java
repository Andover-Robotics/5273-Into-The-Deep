package org.firstinspires.ftc.teamcode.auto.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator;

@Autonomous(name = "Red Alliance Left", group = "Autonomous")
public class Quad2 extends LinearOpMode {
    @Override
    public void runOpMode() {
        PathMasterTheTestingNavigator.runOpModeBucket(this, 2, new GamepadEx(gamepad2));
    }
}
