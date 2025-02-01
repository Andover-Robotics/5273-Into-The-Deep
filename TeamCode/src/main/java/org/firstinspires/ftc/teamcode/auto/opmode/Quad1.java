package org.firstinspires.ftc.teamcode.auto.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator;

@Autonomous(name = "Blue Alliance Right", group = "Autonomous")
public class Quad1 extends LinearOpMode {
    @Override
    public void runOpMode() {
        PathMasterTheTestingNavigator.runOpModeBucket(this, 1);
    }
}
