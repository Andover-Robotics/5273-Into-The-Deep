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

    private static final double EXPANDEDR = 0.5383, CONTRACTEDR = 1.0;
    private static final double EXPANDEDL = 0.4567, CONTRACTEDL = 0.0;

    public SlidesHorizontal(HardwareMap map) {
        slidesLeft = map.get(Servo.class, "slidesHL");
        slidesRight = map.get(Servo.class, "slidesHR");
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
        setLeft(0.0794);
        setRight(0.9189);
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

    public void setPower(double power){
        if (((getLeft()+Math.signum(power)*-0.005)>=EXPANDEDL) || ((getRight()+Math.signum(power)*0.005)<=EXPANDEDR)) open();
        else if (((getLeft()+Math.signum(power)*-0.005)<=CONTRACTEDL) || ((getRight()+Math.signum(power)*0.005)>=CONTRACTEDR)) close();
        else {
            setRight(getRight()+Math.signum(power)*0.005);
            setLeft(getLeft()+Math.signum(power)*-0.005);
            fsm = HSlides.MIDDLE;
        }
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