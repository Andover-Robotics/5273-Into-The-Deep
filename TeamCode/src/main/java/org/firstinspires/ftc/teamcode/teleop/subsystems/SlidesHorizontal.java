package org.firstinspires.ftc.teamcode.teleop.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class SlidesHorizontal {
    private final Servo slidesLeft, slidesRight;

    private static final double EXPANDEDR = 0.4617, MIDDLER = 0.37, LOOSER = 0.27777778, CONTRACTEDR = 0.2206;
    private static final double EXPANDEDL = 0.5383, MIDDLEL = 0.6244, LOOSEL = 0.75277778, CONTRACTEDL = 0.7744;
    private final Telemetry telemetry;

    public SlidesHorizontal(HardwareMap map, Telemetry tele) {
        slidesLeft = map.get(Servo.class, "slidesHL");
        slidesRight = map.get(Servo.class, "slidesHR");
        telemetry = tele;
    }

    public enum HSlides {
        OUT,
        IN,
        MIDDLE
    }

    public HSlides fsm = HSlides.IN;

    public void setRight(double pos){
        slidesRight.setPosition(pos);
    }

    public void setLeft(double pos){
        slidesLeft.setPosition(pos);
    }

    public double getRight(){
        return(slidesRight.getPosition());
    }

    public double getLeft(){
        return(slidesLeft.getPosition());
    }

    private double clamp(double value, double bound1, double bound2) {
        return bound1 < bound2
                ? Math.max(bound1, Math.min(bound2, value))
                : Math.max(bound2, Math.min(bound1, value));
    }

    public void setPower(double power){
        setRight(clamp(getRight() + power*0.01, CONTRACTEDR, EXPANDEDR));
        setLeft(clamp(getLeft() + power*-0.01, CONTRACTEDL, EXPANDEDL));
        if(getLeft() == EXPANDEDL || getRight() == EXPANDEDR) fsm = HSlides.OUT;
        else if(getLeft() == CONTRACTEDL || getRight() == CONTRACTEDR) fsm = HSlides.IN;
        else fsm = HSlides.MIDDLE;

        telemetry.addData("Left Hslides", getLeft());
        telemetry.addData("Right Hslides", getRight());
    }

    public void close() {
        setRight(CONTRACTEDR);
        setLeft(CONTRACTEDL);
        fsm = HSlides.IN;
    }

    public void loose() {
        setRight(LOOSER);
        setLeft(LOOSEL);
    }

    public void middle() {
        setRight(MIDDLER);
        setLeft(MIDDLEL);
    }

    public Action horizPeriodicClosed() {
        class SlidesAction implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                close();
                return true;
            }
        }
        return new SlidesAction();
    }

    public void open() {
        setRight(EXPANDEDR);
        setLeft(EXPANDEDL);
        fsm = HSlides.OUT;
    }
}