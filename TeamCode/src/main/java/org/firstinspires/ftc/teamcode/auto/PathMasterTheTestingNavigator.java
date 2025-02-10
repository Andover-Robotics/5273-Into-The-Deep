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

//our special silly very important goofy classes (w rizz)
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Camera;
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
                .strafeTo(new Vector2d(-62, 0))
                .build();
        opMode.waitForStart();
        Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeSpecPark(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .strafeTo(new Vector2d(62, 0))
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
        // just set quadrant to some random value because it's easier than manually replacing them all and it doesn't matter what it is

        Vector2d intakeBucket1 = new Vector2d(-42.5, 44 );
        Vector2d intakeBucket2 = new Vector2d(-61, 44);
        //Vector2d intakeBucket3 = new Vector2d(-70, 40.832 );

        Vector2d outtakeBucket = new Vector2d(-58, 0 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .waitSeconds(1)
                // output sample 0
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeBucket1, Math.toRadians(90 ))
                .waitSeconds(1)
                // input sample 1
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 1
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(intakeBucket2, Math.toRadians(90))
                .waitSeconds(1)
                //input sample 2
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeBucket, Math.toRadians(45))
                .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 2
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(-62, 0), Math.toRadians(90))
                /*.strafeToSplineHeading(intakeBucket3, Math.toRadians((90, ))
                .waitSeconds(1)
                // input sample 3
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(outtakeBucket, Math.toRadians((45, ))
                .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 3
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)*/
                // turn around so its facing the field
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeSpecimen(LinearOpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        
        int pixelOne = 40;
        int pixelTwo = 50;
        int pixelThree = 65;
        int pushIn = 10;
        int pixelY = 50;


        //mid to sideplate7.5, mid to back7.168
        Vector2d outtakeSpecInit = new Vector2d(-12, 40.832 );
        Vector2d outtakeSpec1 = new Vector2d(-8, 40.832 );
        Vector2d outtakeSpec2 = new Vector2d(-4, 40.832 );
        Vector2d outtakeSpec3 = new Vector2d(-0, 40.832 );

        Vector2d intakeSpec = new Vector2d(36 , 0 );

        // initial is 10 60
        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(90)))
                .strafeToSplineHeading(outtakeSpecInit, Math.toRadians(270))
                .waitSeconds(1)
		        .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                // TODO figure out static claw positioning (left (90) or right (270))
                // Preloaded samples are at y = 24 inches, and the static claw is 12 inches long
                // Human player zone is at y = 60 inches
                .strafeToSplineHeading(new Vector2d(pixelOne-15  ,30 ), Math.toRadians(90))
                .strafeTo(new Vector2d(pixelOne , pixelY ))
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
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
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
                .strafeToSplineHeading(new Vector2d(62, 0), Math.toRadians(90))
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    private static Action doSweep() {
        return bot.actionSweep();
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
