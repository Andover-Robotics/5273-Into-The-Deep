package org.firstinspires.ftc.teamcode;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class SlidesHorizontal {
    private final Servo slidesLeft, slidesRight;

    private static final double EXPANDEDR = 0.4617, CONTRACTEDR = 0.0;
    private static final double EXPANDEDL = 0.5383, CONTRACTEDL = 1.0;
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

    public void setBothPosition(double position) {
        slidesLeft.setPosition(position);
        slidesRight.setPosition(position);
    }

    public void setPreTransfer(){
        setLeft(0.36);
        setRight(0.6389);
    }

    public void setTransfer(){
        setLeft(0.155);
        setRight(0.838);
    }

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
        if(Math.abs(EXPANDEDL - CONTRACTEDL) - Math.abs(EXPANDEDR - CONTRACTEDR) > 0.01) {
            throw new RuntimeException("The horizontal slides need equal range of motion");
        }

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

    public void open() {
        setRight(EXPANDEDR);
        setLeft(EXPANDEDL);
        fsm = HSlides.OUT;
    }
}