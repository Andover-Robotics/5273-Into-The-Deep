package org.firstinspires.ftc.teamcode.auto.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator;

@Autonomous(name = "Blue Alliance Left", group = "Autonomous")
public class Quad0 extends LinearOpMode {
    @Override
    public void runOpMode() {
        PathMasterTheTestingNavigator.runOpModeBucket(this, 0, new GamepadEx(gamepad2));
    }
}
