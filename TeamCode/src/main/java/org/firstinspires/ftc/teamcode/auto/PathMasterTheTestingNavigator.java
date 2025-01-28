package org.firstinspires.ftc.teamcode.auto;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;


// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//our special silly very important goofy classes (w rizz)
import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.SlidesVertical;

/**
 * Yet another OpMode, this time for Autonomous - the names are intentional (and great), don't mess with them
 */
@Config
@Autonomous(name = "Autonomous", group = "Autonomous")
public class PathMasterTheTestingNavigator extends LinearOpMode {
    /**
     * Runs the OpMode
    */



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

    public void runOpMode(){
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(-10, -60, Math.toRadians(90)));
        SlidesVertical slidesVertical = new SlidesVertical(hardwareMap);
        Action arcStrikeVelocity;

        Intake intake = new Intake(hardwareMap, new Camera(hardwareMap, telemetry));
        Outtake outtake = new Outtake(hardwareMap);
        SlidesVertical verticalSlides = new SlidesVertical(hardwareMap);


        int quadrant = 1;

        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>=2?-1:1;

        arcStrikeVelocity =mecanumDrive.actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .strafeTo(new Vector2d(48 * xFactor, 38 * yFactor))
                // input sample 1
                .stopAndAdd(intake::openIntake)
                .waitSeconds(0.5)
                .stopAndAdd(intake::posIntake)
                .stopAndAdd(intake::closeIntake)
                .waitSeconds(0.5)
                .stopAndAdd(intake::looseClaw)
                .stopAndAdd(intake::posTransfer)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                // output sample 1
                .waitSeconds(0.5)
                .stopAndAdd(outtake::openTransfer)
                .waitSeconds(0.5)
                .stopAndAdd(outtake::close)
                .waitSeconds(0.5)
                .stopAndAdd(verticalSlides::moveToUpperBound)
                .stopAndAdd(outtake::closeBucket)
                .waitSeconds(0.5)
                .stopAndAdd(outtake::open)
                .stopAndAdd(verticalSlides::moveToLowerBound)
                .strafeToSplineHeading(new Vector2d(58 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                //input sample 2
                .stopAndAdd(intake::openIntake)
                .waitSeconds(0.5)
                .stopAndAdd(intake::posIntake)
                .stopAndAdd(intake::closeIntake)
                .waitSeconds(0.5)
                .stopAndAdd(intake::looseClaw)
                .stopAndAdd(intake::posTransfer)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                // output sample 2
                .waitSeconds(0.5)
                .stopAndAdd(outtake::openTransfer)
                .waitSeconds(0.5)
                .stopAndAdd(outtake::close)
                .waitSeconds(0.5)
                .stopAndAdd(verticalSlides::moveToUpperBound)
                .stopAndAdd(outtake::closeBucket)
                .waitSeconds(0.5)
                .stopAndAdd(outtake::open)
                .stopAndAdd(verticalSlides::moveToLowerBound)
                .strafeToSplineHeading(new Vector2d(69 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                // input sample 3
                .stopAndAdd(intake::openIntake)
                .waitSeconds(0.5)
                .stopAndAdd(intake::posIntake)
                .stopAndAdd(intake::closeIntake)
                .waitSeconds(0.5)
                .stopAndAdd(intake::looseClaw)
                .stopAndAdd(intake::posTransfer)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(45, quadrant)))
                // output sample 3
                .waitSeconds(0.5)
                .stopAndAdd(outtake::openTransfer)
                .waitSeconds(0.5)
                .stopAndAdd(outtake::close)
                .waitSeconds(0.5)
                .stopAndAdd(verticalSlides::moveToUpperBound)
                .stopAndAdd(outtake::closeBucket)
                .waitSeconds(0.5)
                .stopAndAdd(outtake::open)
                .stopAndAdd(verticalSlides::moveToLowerBound)
                .build();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        arcStrikeVelocity
                )
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
