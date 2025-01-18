package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Slides mechanism, representing both Slide motors.
 * See {@link org.firstinspires.ftc.teamcode.auto.PathMasterTheTestingNavigator}
 */
public class SlidesVertical {
    private final DcMotor slidesLeft, slidesRight;
    //sets limits of slides extension
    private static final int UPPER_BOUND = 5200;
    private static final int LOWER_BOUND = 0;

    public enum VSlides {
        LOWERED,  // Slides at the lower bound
        RAISED,  // Slides at the upper bound
        MIDDLE //Slides in the middle
    }

    public VSlides fsm = VSlides.LOWERED;

    //makes sure FSM is updated based on encoder positions
    public void updateFSM(){
        if (getEncoders()<=LOWER_BOUND+5) fsm = VSlides.LOWERED;
        if (getEncoders()>=UPPER_BOUND-5) fsm = VSlides.RAISED;
        else fsm = VSlides.MIDDLE;
    }

    public void moveToLowerBound() {
        if(!(fsm == VSlides.LOWERED)) setPosition(LOWER_BOUND);
        fsm = VSlides.LOWERED;
    }

    public void moveToUpperBound() {
        if(!(fsm== VSlides.RAISED)) setPosition(UPPER_BOUND);
        fsm = VSlides.RAISED;
    }

    public SlidesVertical(HardwareMap map) {
        slidesLeft = map.get(DcMotor.class, "slidesL");
        slidesRight = map.get(DcMotor.class, "slidesR");
        slidesRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power) {
        slidesLeft.setPower(power);
        slidesRight.setPower(power);
    }


    public int getEncoders() {
        return (int)((slidesLeft.getCurrentPosition() + slidesRight.getCurrentPosition()) / 2);
    }

    //simply moves up or down based on input from controller
    public void slidesMove(double input, boolean overrideButton, Telemetry telemetry){
        int pos = getEncoders();
        telemetry.addData("Slides position: ",pos);
        if(!overrideButton && ((pos > UPPER_BOUND && input > 0) || (pos < LOWER_BOUND && input < 0) )) {
            setPower(0);
        }else{
            setPower(input);
        }
        updateFSM();
    }

    //moves based on position inputted
    public void setPosition(int targetPosition) {
        final int TOLERANCE = 5;
        int error = targetPosition - getEncoders();

        if (Math.abs(error) > TOLERANCE) {
            double power = Math.max(0.1, Math.min(1.0, Math.abs(error) / 1000.0));
            if (error > 0) {
                setPower(power);
            } else {
                setPower(-power);
            }
        } else {
            setPower(0);
        }
        updateFSM();
    }
}
