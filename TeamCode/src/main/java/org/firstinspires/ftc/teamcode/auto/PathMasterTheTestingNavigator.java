package org.firstinspires.ftc.teamcode.auto;
// RR-specific imports
import android.net.UrlQuerySanitizer;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;


// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//our special silly very important goofy classes (w rizz)
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Bot;




/**
 * Yet another OpMode, this time for Autonomous - the names are intentional (and great), don't mess with them
 */
public class PathMasterTheTestingNavigator {
    private static Bot bot;

    public static void runOpModeBucketPark(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        bot = new Bot(opMode,hardwareMap, telemetry);


        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .strafeTo(new Vector2d(-55, 0))
                .build();
        opMode.waitForStart();
        Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeSpecPark(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(270)));

        bot = new Bot(opMode,hardwareMap, telemetry);

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(270)))
                .strafeTo(new Vector2d(55, 0))
                .build();
        opMode.waitForStart();
        Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeBucket(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        bot = new Bot(opMode,hardwareMap, telemetry);

        Vector2d intakeSample1 = new Vector2d(-17, 26);
        Vector2d intakeSample2 = new Vector2d(-29.25, 26);
        Vector2d intakeSample3 = new Vector2d(-26.75, 36.7);

        Vector2d outtakeBucket = new Vector2d(-20, 10 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .stopAndAdd(intakePosition())
                .stopAndAdd(bot.closeHori())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .stopAndAdd(doOuttakeBucket())
                // output sample 1
                .strafeToSplineHeading(intakeSample1, Math.toRadians(90))
                .stopAndAdd(bot.closeHori())
                .waitSeconds(1)
                .stopAndAdd(bot.closeHori())
                // input sample 1
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .stopAndAdd(doTransfer())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .waitSeconds(1)
                // output sample 1
                .stopAndAdd(doOuttakeBucket())
                .strafeToSplineHeading(intakeSample2, Math.toRadians(90))
                .stopAndAdd(bot.closeHori())
                .waitSeconds(1)
                //input sample 2
                .stopAndAdd(bot.closeHori())
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .stopAndAdd(doTransfer())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .waitSeconds(1)
                // output sample 2
                .stopAndAdd(doOuttakeBucket())
                .strafeToSplineHeading(intakeSample3, Math.toRadians(180))
                .stopAndAdd(bot.clawRoll90())
                .stopAndAdd(bot.closeHori())
                .waitSeconds(1)
                //input sample 3
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .stopAndAdd(doTransfer())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .waitSeconds(1)
                // output sample 3
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(0, -38), Math.toRadians(90))
                .stopAndAdd(outtakeTransferPos())
                .build();

        opMode.waitForStart();

        Actions.runBlocking(new ParallelAction(
                arcStrikeVelocity,
                bot.slidesPeriodic()
        ));
    }

    public static void runOpModeSpecimen(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(270)));

        bot = new Bot(opMode,hardwareMap, telemetry);

        // push positions
        int pixelOne = 35;
        int pixelTwo = 45;
        int pixelThree = 55;
        int pushIn = 16;
        int pixelY = 55;

        Vector2d outtakeSpecInit = new Vector2d(-6, 28.832 );
        Vector2d outtakeSpec1 = new Vector2d(-4, 28.832 );
        Vector2d outtakeSpec2 = new Vector2d(-2, 28.832 );
        Vector2d outtakeSpec3 = new Vector2d(-0, 28.832 );

        Vector2d intakeSpec = new Vector2d(32 , 4 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(270)))

                .strafeToSplineHeading(outtakeSpecInit, Math.toRadians(270))
                .waitSeconds(1)
		        .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(0,45), Math.toRadians(90))     // ready for first sweep
                .waitSeconds(1)
                .stopAndAdd(sweepOut())
                .strafeToSplineHeading(new Vector2d(0,35), Math.toRadians(0))      // first sweep
                .waitSeconds(1)
                .stopAndAdd(sweepIn())
                .strafeToSplineHeading(new Vector2d(11.25,45), Math.toRadians(90))     // ready for second sweep
                .waitSeconds(1)
                .stopAndAdd(sweepOut())
                .strafeToSplineHeading(new Vector2d(11.25, 35), Math.toRadians(0))     // second sweep
                .waitSeconds(1)
                .stopAndAdd(sweepIn())
                .strafeToSplineHeading(new Vector2d(23.5, 45), Math.toRadians(90))    // ready for third sweep
                .waitSeconds(1)
                .stopAndAdd(sweepOut())
                .strafeToSplineHeading(new Vector2d(23.5, 35), Math.toRadians(0))     // third sweep
                .waitSeconds(1)
                .stopAndAdd(sweepIn())
                .waitSeconds(1)

                /*
                // push
                .strafeTo(new Vector2d(pixelOne-8,24 ))
                .strafeTo(new Vector2d(pixelOne-8, pixelY))
                .strafeTo(new Vector2d(pixelOne , pixelY))
                .strafeTo(new Vector2d(pixelOne, pushIn))
                .strafeTo(new Vector2d(pixelOne, pixelY))
                .strafeTo(new Vector2d(pixelTwo , pixelY ))
                .strafeTo(new Vector2d(pixelTwo , pushIn ))
                .strafeTo(new Vector2d(pixelTwo , pixelY ))
                .strafeTo(new Vector2d(pixelThree , pixelY ))
                .strafeTo(new Vector2d(pixelThree , pushIn ))
                */


                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .waitSeconds(1)
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpec1, Math.toRadians(270))
                .waitSeconds(1)
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .waitSeconds(1)
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpec2, Math.toRadians(270))
                .waitSeconds(1)
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .waitSeconds(1)
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpec3, Math.toRadians(270))
                .waitSeconds(1)
                .stopAndAdd((doOuttakeSpecimen()))
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(38, 0), Math.toRadians(90))
                .stopAndAdd(outtakeTransferPos())
                .build();

        opMode.waitForStart();

        Actions.runBlocking(new ParallelAction(
                arcStrikeVelocity,
                bot.slidesPeriodic(),
                bot.periodicSweep()
        ));
    }

    private static Action sweepOut() {
        return bot.actionSweepOut();
    }

    private static Action sweepIn() {
        return bot.actionSweepIn();
    }

    private static Action doTransfer() { return bot.actionTransfer();}

    private static Action outtakeTransferPos() {
        return bot.actionOuttakeTransfer();
    }

    private static Action doIntake() {
        return bot.actionIntakeSample();
    }

    private static Action doOuttakeBucket() {
        return bot.actionOuttakeBucket();
    }

    private static Action doIntakeSpecimen() { //  specimen from wall
        return bot.actionIntakeSpecimen();
    }

    private static Action doOuttakeSpecimen() { // clips to top rung
        return new SequentialAction(
                bot.actionSpecPos(),
                new SleepAction(1),
                bot.actionClipSpecimen()
        );
    }

    private static Action intakePosition(){ return bot.actionIntakePos();}
}
