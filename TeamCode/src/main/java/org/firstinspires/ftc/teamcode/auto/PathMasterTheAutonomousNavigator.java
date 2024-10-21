package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.auto.MecanumDrive;

//our special silly very important goofy classes (w rizz)
import  org.firstinspires.ftc.teamcode.Slides;

/**
 * Yet another OpMode, this time for Autonomous - the names are intentional (and great), don't mess with them
 */
@Config
@Autonomous(name = "PATH", group = "Autonomous")
public class PathMasterTheAutonomousNavigator extends LinearOpMode {
    /**
     * Runs the OpMode
    */
    public void runOpMode(){
        MecanumDrive titanDrivePrecisionPowertrain = new MecanumDrive(hardwareMap, new Pose2d(-10, -60, Math.toRadians(90)));
        Slides ethan = new Slides(hardwareMap);
        Action arcStrikeVelocity;

        arcStrikeVelocity = titanDrivePrecisionPowertrain.actionBuilder(titanDrivePrecisionPowertrain.pose)
                .lineToY(-35)
                .waitSeconds(3)
                .strafeTo(new Vector2d(-48,-38))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-55),Math.toRadians(225))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-57.5,-38),Math.toRadians(90))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-55),Math.toRadians(225))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-38),Math.toRadians(180))
                .strafeTo(new Vector2d(-55,-25))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-55),Math.toRadians(225))
                .build();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        arcStrikeVelocity
                )
        );
    }
}
