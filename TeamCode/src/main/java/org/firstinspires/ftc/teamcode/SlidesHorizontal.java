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

    private static final double EXPANDED = 1.0, CONTRACTED = 0.0;

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

    public double getPositions(){
        return(slidesLeft.getPosition()+ slidesRight.getPosition())/2.0;
    }

    public void setPower(double power){
        //if (getPositions()+(Math.signum(power)*0.01) >= EXPANDED) open();
        //else if (getPositions()+(Math.signum(power)*0.01) <= CONTRACTED) close();
        //else {
            slidesRight.setPosition(slidesRight.getPosition() + (Math.signum(power)*0.01));
            slidesLeft.setPosition(slidesLeft.getPosition() + (Math.signum(power)*(-0.01)));
            fsm = HSlides.MIDDLE;
        //}
    }

    public void slidesMove(double input, boolean overrideButton, Telemetry telemetry){
        double pos = (getPositions());
        telemetry.addData("Horizontal Slides position: ",pos);
        if(!overrideButton && ((pos > CONTRACTED && input > 0) || (pos < EXPANDED && input < 0) )) {
            setPower(0);
        }else{
            setPower(input);
        }
    }

    public void close() {
        setBothPosition(CONTRACTED);
        fsm = HSlides.IN;
    }

    public void open() {
        setBothPosition(EXPANDED);
        fsm = HSlides.OUT;
    }
}