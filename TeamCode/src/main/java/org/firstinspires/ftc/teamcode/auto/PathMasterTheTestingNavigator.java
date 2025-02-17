package org.firstinspires.ftc.teamcode.auto;
// RR-specific imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;


// Non-RR imports
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//our special silly very important goofy classes (w rizz)
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.SlidesVertical;

/**
 * Yet another OpMode, this time for Autonomous - the names are intentional (and great), don't mess with them
 */
public class PathMasterTheTestingNavigator {
    private static Intake intake;
    private static Outtake outtake;
    private static SlidesVertical verticalSlides;
    private static Bot bot;

    public static void runOpModeBucketPark(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);


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

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

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

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        Vector2d intakeSample1 = new Vector2d(-38.5, 22 );
        Vector2d intakeSample2 = new Vector2d(-50.5, 22);
        Vector2d intakeSample3 = new Vector2d(-50.5, 24);

        Vector2d outtakeBucket = new Vector2d(-44.5, 10 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .waitSeconds(1)
                // output sample 0
                // .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSample1, Math.toRadians(90 ))
                .waitSeconds(1)
                // input sample 1
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                // .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 1
                // .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSample2, Math.toRadians(90))
                .waitSeconds(1)
                //input sample 2
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                // .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 2
                // .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSample3, Math.toRadians(135))
                // .stopAndAdd(bot.clawRoll45())
                .waitSeconds(1)
                // input sample 3
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                // .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 3
                // .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                // turn around so its facing the field
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeSpecimen(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(270)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        // push positions
        int pixelOne = 35;
        int pixelTwo = 45;
        int pixelThree = 55;
        int pushIn = 14;
        int pixelY = 55;

        Vector2d outtakeSpecInit = new Vector2d(-6, 28.832 );
        Vector2d outtakeSpec1 = new Vector2d(-4, 28.832 );
        Vector2d outtakeSpec2 = new Vector2d(-2, 28.832 );
        Vector2d outtakeSpec3 = new Vector2d(-0, 28.832 );

        Vector2d intakeSpec = new Vector2d(36 , 0 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(270)))
                .strafeToSplineHeading(outtakeSpecInit, Math.toRadians(270))
                .waitSeconds(1)
		        // .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)



                /* Sweep
                // getting these arm down and up timings with the movements optimized is good
                // this is kinda assuming the sweep arm servo is really fast, may need to add more waits
                .strafeToSplineHeading(new Vector2d(18,45), Math.toRadians(90))     // ready for first sweep
                .waitSeconds(1)
                // .stopAndAdd(sweepDown())
                .strafeToSplineHeading(new Vector2d(10,35), Math.toRadians(0))      // first sweep
                .waitSeconds(1)
                // .stopAndAdd(sweepUp())
                .strafeToSplineHeading(new Vector2d(30,45), Math.toRadians(90))     // ready for second sweep
                .waitSeconds(1)
                // .stopAndAdd(sweepDown())
                .strafeToSplineHeading(new Vector2d(30, 35), Math.toRadians(0))     // second sweep
                .waitSeconds(1)
                // .stopAndAdd(sweepUp())
                .strafeToSplineHeading(new Vector2d(42, 45), Math.toRadians(90))    // ready for third sweep
                .waitSeconds(1)
                // .stopAndAdd(sweepDown())
                .strafeToSplineHeading(new Vector2d(42, 35), Math.toRadians(0))     // third sweep
                .waitSeconds(1)
                // .stopAndAdd(sweepUp())
                    */

                // push
                .splineTo(new Vector2d(pixelOne-18,28 ), Math.toRadians(180))
                .splineTo(new Vector2d(pixelOne-18, pixelY), Math.toRadians(270))
                .strafeTo(new Vector2d(pixelOne , pixelY))
                .strafeTo(new Vector2d(pixelOne, pushIn))
                .strafeTo(new Vector2d(pixelOne, pixelY))
                .strafeTo(new Vector2d(pixelTwo , pixelY ))
                .strafeTo(new Vector2d(pixelTwo , pushIn ))
                .strafeTo(new Vector2d(pixelTwo , pixelY ))
                .strafeTo(new Vector2d(pixelThree , pixelY ))
                .strafeTo(new Vector2d(pixelThree , pushIn ))

                .strafeTo(intakeSpec)
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpecInit, Math.toRadians(270))
                .waitSeconds(1)
                // .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .waitSeconds(1)
                // .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpec1, Math.toRadians(270))
                .waitSeconds(1)
                // .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .waitSeconds(1)
                // .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpec2, Math.toRadians(270))
                .waitSeconds(1)
                // .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(90))
                .waitSeconds(1)
                // .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeSpec3, Math.toRadians(270))
                .waitSeconds(1)
                // .stopAndAdd((doOuttakeSpecimen()))
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(62, 0), Math.toRadians(90))
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    private static Action sweepDown() {
        return bot.actionSweepArmDown();
    }

    private static Action sweepUp() {
        return bot.actionSweepArmUp();
    }

    private static Action doTransfer() { return bot.actionTransfer();}

    private static Action doIntake() {
        return bot.actionIntake();
    }

    private static Action doOuttakeBucket() {
        return bot.actionOuttakeBucket();
    }

    private static Action doIntakeSpecimen() { //  specimen from wall
        return bot.actionIntakeSpecimen();
    }

    private static Action doOuttakeSpecimen() { // clips to top rung
        return bot.actionOuttakeSpecimen();
    }
}
