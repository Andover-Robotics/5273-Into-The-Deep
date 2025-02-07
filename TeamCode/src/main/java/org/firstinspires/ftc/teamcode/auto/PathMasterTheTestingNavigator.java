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
    private static int getAngle(int angle, int quadrant) {
        return angle;
        /*
        switch (quadrant) {
            case 0:
                return angle;
            case 1:
                return 180 - angle;
            case 2:
                return 180 + angle;
            case 3:
                return 360 - angle;
            default:
                return 0;
        }
         */
    }

    private static Intake intake;
    private static Outtake outtake;
    private static SlidesVertical verticalSlides;
    private static Bot bot;

    public static void runOpModePark(LinearOpMode opMode, int quadrant) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(getAngle(270, quadrant))))
                .strafeToSplineHeading(new Vector2d(50, 0), Math.toRadians(getAngle(90, quadrant)))
                .build();
                opMode.waitForStart();
                Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeBucket(LinearOpMode opMode, int quadrant) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);


        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(getAngle(270, quadrant))))
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(45, quadrant)))
                .waitSeconds(1)
                // output sample 0
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeTo(new Vector2d(48 , 38 ))
                .waitSeconds(1)
                // input sample 1
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(45, quadrant)))
                .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 1
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(58 , 38 ), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(1)
                //input sample 2
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(45, quadrant)))
                .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 2
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(69 , 38 ), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(1)
                // input sample 3
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(45, quadrant)))
                .stopAndAdd(doTransfer())
                .waitSeconds(1)
                // output sample 3
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                // turn around so its facing the field
                .turn(Math.toRadians(180))
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    public static void runOpModeSpecimen(LinearOpMode opMode, int quadrant) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        int pixelOne = 46;
        int pixelTwo = 58;
        int pixelThree = 62;
        int pushIn = 56;
        int pixelY = 10;

        Vector2d outtakeSpec = new Vector2d(8 , 38 );
        Vector2d intakeSpec = new Vector2d(60 , 60 );

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(0 , 0 , Math.toRadians(getAngle(270, quadrant))))
                //.strafeToSplineHeading(new Vector2d(-16 , 33 ), Math.toRadians(getAngle(90,quadrant)))
                //.waitSeconds(0.5)
                // outtake specimen 0
                //.stopAndAdd(doOuttakeSpecimen()) //TODO tune outtaking specimens
                //.waitSeconds(1)
                // TODO figure out static claw positioning (left (90) or right (270))


                /*
                // go to sweep
                .strafeToSplineHeading(new Vector2d(40 , 45 ), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(1)
                //sweep
                .stopAndAdd(doSweep())
                .strafeToSplineHeading(new Vector2d(40 , 45 ), Math.toRadians(getAngle(120,quadrant)))
                .waitSeconds(1)
                // go to sweep
                .strafeToSplineHeading(new Vector2d(50 , 45 ), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(1)
                //sweep
                .stopAndAdd(doSweep())
                .strafeToSplineHeading(new Vector2d(50 , 45 ), Math.toRadians(getAngle(120,quadrant)))
                .waitSeconds(1)
                // go to sweep
                .strafeToSplineHeading(new Vector2d(60 , 45 ), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(1)
                // sweep
                .stopAndAdd(doSweep())
                .strafeToSplineHeading(new Vector2d(60 , 45 ), Math.toRadians(getAngle(120,quadrant)))
                .waitSeconds(1)
                */

                // intake specimen 1
                /*.stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(-8 , 33 ), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // outtake specimen 1
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // intake specimen 2
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(0 , 33 ), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // outtake specimen 2
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // intake specimen 3
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(8 , 33 ), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // outtake specimen 3
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 , 60 ), Math.toRadians(getAngle(90, quadrant)))
                // park
                .turn(Math.toRadians(180))*/
                .strafeToSplineHeading(outtakeSpec, Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(0.5)
                // TODO figure out static claw positioning (left (90) or right (270))
                // Preloaded samples are at y = 24 inches, and the static claw is 12 inches long
                .strafeTo(new Vector2d(20 ,33 ))
                .strafeToSplineHeading(new Vector2d(pixelOne , pixelY ),Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(1)
                // Human player zone is at y = 60 inches
                .strafeTo(new Vector2d(pixelOne  ,pushIn ))
                .strafeTo(new Vector2d(pixelOne , pixelY ))
                .strafeTo(new Vector2d(pixelTwo , pixelY ))
                .strafeTo(new Vector2d(pixelTwo , pushIn ))
                .strafeTo(new Vector2d(pixelTwo , pixelY ))
                .strafeTo(new Vector2d(pixelThree , pixelY ))
                .strafeTo(new Vector2d(pixelThree , pushIn ))

                .strafeToSplineHeading(intakeSpec, Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(outtakeSpec, Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(outtakeSpec, Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(outtakeSpec, Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .strafeToSplineHeading(intakeSpec, Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(1)
                .turn(Math.toRadians(180))
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
