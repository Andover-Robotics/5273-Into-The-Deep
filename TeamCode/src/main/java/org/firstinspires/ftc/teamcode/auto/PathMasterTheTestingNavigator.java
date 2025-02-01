package org.firstinspires.ftc.teamcode.auto;

// RR-specific imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;


// Non-RR imports
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
    }

    private static Intake intake;
    private static Outtake outtake;
    private static SlidesVertical verticalSlides;
    private static Bot bot;

    public static void runOpModeBucket(LinearOpMode opMode, int quadrant) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(-10, -60, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        int xFactor = 1;
        int yFactor = 1;

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(10 * xFactor, 60 * yFactor, Math.toRadians(getAngle(270, quadrant))))
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                .waitSeconds(1)
                // output sample 0
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeTo(new Vector2d(48 * xFactor, 38 * yFactor))
                .waitSeconds(1)
                // input sample 1
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                .waitSeconds(1)
                // output sample 1
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(58 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(1)
                //input sample 2
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                .waitSeconds(1)
                // output sample 2
                .stopAndAdd(doOuttakeBucket())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(69 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(1)
                // input sample 3
                .stopAndAdd(doIntake())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
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

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(-10, -60, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);
        bot = new Bot(hardwareMap, telemetry);

        int xFactor = 1;
        int yFactor = 1;

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(10 * xFactor, 60 * yFactor, Math.toRadians(getAngle(270, quadrant))))
                .strafeToSplineHeading(new Vector2d(-16 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(0.5)
                // outtake specimen 0
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                // TODO figure out static claw positioning (left (90) or right (270))
                // go to sweep
                .strafeToSplineHeading(new Vector2d(40 * xFactor, 45 * yFactor), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(1)
                //sweep
                .stopAndAdd(doSweep())
                .strafeToSplineHeading(new Vector2d(40 * xFactor, 45 * yFactor), Math.toRadians(getAngle(120,quadrant)))
                .waitSeconds(1)
                // go to sweep
                .strafeToSplineHeading(new Vector2d(50 * xFactor, 45 * yFactor), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(1)
                //sweep
                .stopAndAdd(doSweep())
                .strafeToSplineHeading(new Vector2d(50 * xFactor, 45 * yFactor), Math.toRadians(getAngle(120,quadrant)))
                .waitSeconds(1)
                // go to sweep
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 45 * yFactor), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(1)
                // sweep
                .stopAndAdd(doSweep())
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 45 * yFactor), Math.toRadians(getAngle(120,quadrant)))
                .waitSeconds(1)

                // intake specimen 1
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(-8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // outtake specimen 1
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // intake specimen 2
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(0 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // outtake specimen 2
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // intake specimen 3
                .stopAndAdd(doIntakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                // outtake specimen 3
                .stopAndAdd(doOuttakeSpecimen())
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                // park
                .turn(Math.toRadians(180))
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    private static Action doSweep() {
        return bot.actionSweep();
    }

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
