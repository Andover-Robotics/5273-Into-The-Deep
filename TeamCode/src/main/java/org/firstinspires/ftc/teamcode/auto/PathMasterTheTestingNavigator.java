package org.firstinspires.ftc.teamcode.auto;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;


// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

//our special silly very important goofy classes (w rizz)
import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    public static void runOpMode(LinearOpMode opMode, int quadrant){
        HardwareMap hardwareMap = opMode.hardwareMap;
        Telemetry telemetry = opMode.telemetry;

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(-10, -60, Math.toRadians(90)));

        intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        outtake = new Outtake(hardwareMap);
        verticalSlides = new SlidesVertical(hardwareMap);

        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>=2?-1:1;

        Action arcStrikeVelocity = mecanumDrive.actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .strafeTo(new Vector2d(48 * xFactor, 38 * yFactor))
                // input sample 1
                .stopAndAdd(doIntake())
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                // output sample 1
                .stopAndAdd(doOuttake())
                .strafeToSplineHeading(new Vector2d(58 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                //input sample 2
                .stopAndAdd(doIntake())
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                // output sample 2
                .stopAndAdd(doOuttake())
                .strafeToSplineHeading(new Vector2d(69 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                // input sample 3
                .stopAndAdd(doIntake())
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                // output sample 3
                .stopAndAdd(doOuttake())
                .build();

        opMode.waitForStart();

        Actions.runBlocking(arcStrikeVelocity);
    }

    private static Action doIntake() {
        return new SequentialAction(
                new InstantAction(intake::openIntake),
                new SleepAction(0.5),
                new InstantAction(() -> {
                    intake.posIntake();
                    intake.closeIntake();
                }),
                new SleepAction(0.5),
                new InstantAction(() -> {
                    intake.looseClaw();
                    intake.posTransfer();
                })
        );
    }

    private static Action doOuttake() {
        return new SequentialAction(
                new SleepAction(0.5),
                new InstantAction(outtake::openTransfer),
                new SleepAction(0.5),
                new InstantAction(outtake::close),
                new SleepAction(0.5),
                new InstantAction(() -> {
                    verticalSlides.moveToUpperBound();
                    outtake.closeBucket();
                }),
                new SleepAction(0.5),
                new InstantAction(() -> {
                    outtake.open();
                    verticalSlides.moveToLowerBound();
                })
        );
    }

    /*
    public void intakeAndTransfer() throws InterruptedException {
        //Intake intake = new Intake(hardwareMap, new Camera());

        //intake.openIntake();
        // open before you get there cus yeah timesave
        //intake.posIntake();

        //the wait should be there by being after the bot gets to the right pos
        //intake.closeIntake();

        //intake.looseClaw();

        //intake.posTransfer();

    }


    public void transferAndTopBucket() throws InterruptedException {
        Outtake outtake = new Outtake(hardwareMap);
        SlidesVertical verticalSlides = new SlidesVertical(hardwareMap);

        outtake.openTransfer();
        Thread.sleep(1000);
        outtake.close();

        Thread.sleep(1000);

        verticalSlides.moveToUpperBound();

        outtake.closeBucket();

        Thread.sleep(1000);

        outtake.open();

        verticalSlides.moveToLowerBound();
    }

     */
}
