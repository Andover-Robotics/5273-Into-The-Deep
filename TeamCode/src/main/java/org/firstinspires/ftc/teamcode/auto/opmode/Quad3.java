package org.firstinspires.ftc.teamcode.auto.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator;

@Autonomous(name = "Red Alliance Right", group = "Autonomous")
public class Quad3 extends LinearOpMode {
    @Override
    public void runOpMode() {
        PathMasterTheTestingNavigator.runOpModeSpecimen(this, 3);
    }
}
