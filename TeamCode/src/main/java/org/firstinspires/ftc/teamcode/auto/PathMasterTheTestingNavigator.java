package org.firstinspires.ftc.teamcode.auto;
// RR-specific imports

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

        Vector2d outtakeBucket = new Vector2d(-25, 5);

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .stopAndAdd(intakePosition())
                .stopAndAdd(bot.closeHori())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .stopAndAdd(doOuttakeBucketOne())
                .strafeTo(new Vector2d(-27, 3))
                .stopAndAdd(doOuttakeBucketTwo())
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
                .stopAndAdd(doOuttakeBucketOne())
                .strafeTo(new Vector2d(-27, 3))
                .stopAndAdd(doOuttakeBucketTwo())
                // output sample 1
                .strafeToSplineHeading(intakeSample2, Math.toRadians(90))
                .stopAndAdd(bot.closeHori())
                .waitSeconds(1)
                //input sample 2
                .stopAndAdd(bot.closeHori())
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .stopAndAdd(doTransfer())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .stopAndAdd(doOuttakeBucketOne())
                .strafeTo(new Vector2d(-27, 3))
                .stopAndAdd(doOuttakeBucketTwo())                // output sample 2
                .strafeToSplineHeading(intakeSample3, Math.toRadians(180))
                .stopAndAdd(bot.clawRoll90())
                .stopAndAdd(bot.closeHori())
                .waitSeconds(1)
                //input sample 3
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .stopAndAdd(doTransfer())
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .stopAndAdd(doOuttakeBucketOne())
                .strafeTo(new Vector2d(-27, 3))
                .stopAndAdd(doOuttakeBucketTwo())
                // output sample 3
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
        int pixelOne = 28;
        int pixelTwo = 38;
        int pixelThree = 46;
        int pushIn = 10;
        int pixelY = 48;

        Vector2d outtakeSpecInit = new Vector2d(-20, 28.5 );
        Vector2d outtakeSpec1 = new Vector2d(-18.5, 28.5 );
        Vector2d outtakeSpec2 = new Vector2d(-17, 28.5 );
        Vector2d outtakeSpec3 = new Vector2d(-15.5, 28.5 );

        Vector2d intakeSpec = new Vector2d(25 , 6 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(270)))
                .stopAndAdd(bot.vertSlidesToBottom())
                .strafeToSplineHeading(outtakeSpecInit, Math.toRadians(270))
		        .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(2)
                .stopAndAdd(slidesDown())

                .strafeToSplineHeading(new Vector2d(14, 28), Math.toRadians(210))
                .strafeToSplineHeading(new Vector2d(20,30), Math.toRadians(60))     // ready for first sweep
                .waitSeconds(1)
                .stopAndAdd(actionSweepArmDown())
                .strafeToLinearHeading(new Vector2d(20,12), Math.toRadians(0))      // first sweep
                .waitSeconds(1)
                .stopAndAdd(actionSweepArmUp())
                .splineToLinearHeading(new Pose2d(12,30, Math.toRadians(60)), Math.toRadians(90))     // ready for second sweep
                .waitSeconds(1)
                .stopAndAdd(actionSweepArmDown())
                .strafeToLinearHeading(new Vector2d(26, 12), Math.toRadians(0))     // second sweep
                .waitSeconds(1)
                .stopAndAdd(actionSweepArmUp())
                .splineToLinearHeading(new Pose2d(16, 30, Math.toRadians(60)), Math.toRadians(90))    // ready for third sweep
                .waitSeconds(1)
                .stopAndAdd(actionSweepArmDown())
                .strafeToLinearHeading(new Vector2d(30, 12), Math.toRadians(0))     // third sweep
                .waitSeconds(1)
                .stopAndAdd(actionSweepArmUp())


                // push
                /*
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
                .stopAndAdd(doIntakeSpecimen())
                .strafeToSplineHeading(outtakeSpec1, Math.toRadians(270))
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(2)
                .stopAndAdd(bot.slidesDown())
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .stopAndAdd(doIntakeSpecimen())
                .strafeToSplineHeading(outtakeSpec2, Math.toRadians(270))
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(2)
                .stopAndAdd(bot.slidesDown())
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .stopAndAdd(doIntakeSpecimen())
                .strafeToSplineHeading(outtakeSpec3, Math.toRadians(270))
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(2)
                .stopAndAdd(bot.slidesDown())
                .strafeToSplineHeading(new Vector2d(48, 0), Math.toRadians(90))
                .stopAndAdd(outtakeTransferPos())
                .build();

        opMode.waitForStart();

        Actions.runBlocking(new ParallelAction(
                arcStrikeVelocity,
                bot.slidesPeriodic(),
                bot.periodicHorizSlidesClosed()
        ));
    }

    public static Action actionSweepArmUp() {
        return bot.actionSweepArmUp();
    }

    public static Action actionSweepArmDown() {
        return bot.actionSweepArmDown();
    }

    private static Action doTransfer() { return bot.actionTransfer();}

    private static Action outtakeTransferPos() {
        return bot.actionOuttakeTransfer();
    }

    private static Action doIntake() {
        return bot.actionIntakeSample();
    }

    private static Action doOuttakeBucketOne() {
        return bot.actionOuttakeBucketOne();
    }

    private static Action doOuttakeBucketTwo() {
        return bot.actionOuttakeBucketTwo();
    }


    private static Action doIntakeSpecimen() { //  specimen from wall
        return bot.actionIntakeSpecimen();
    }

    private static Action doOuttakeSpecimen() { // clips to top rung
        return new SequentialAction(
                bot.actionArmBucketPos(),
                bot.actionSpecPos(),
                new SleepAction(5),
                bot.actionClipSpecimen()
        );
    }

    private static Action slidesDown() {
        return bot.slidesDown();
    }

    private static Action intakePosition(){ return bot.actionIntakePos();}
}
