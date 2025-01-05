
package org.firstinspires.ftc.teamcode.testing;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * An OpMode that finds servo values. Copied from last year's code.
 */
@TeleOp(name = "Servo Value Finder", group = "Teleop")
public class ServoValueFinder extends LinearOpMode {
    private final static double STEP = 0.0001;
    private static int num;
    private final ServoGroup[] SERVO_LIST = initServoGroups(hardwareMap, new ServoGroup[] {
            ServoGroup.makeAntiServoPair(hardwareMap,"fourOL","fourOR"),
            ServoGroup.makeAntiServoPair(hardwareMap,"fourIL","fourIR"),
            ServoGroup.makeAntiServoPair(hardwareMap,"slidesHL","slidesHR")
    });
    /**
     * Runs the OpMode.
     */
    @Override
    public void runOpMode() {
        waitForStart();
        ServoGroup servo = getServo();
        while(opModeIsActive()){
            if(gamepad2.b){
                servo = getServo();
            }else if(gamepad2.dpad_up){
                servo.Move(true);
            }else if(gamepad2.dpad_down){
                servo.Move(false);
            }
            telemetry.addData("Use controller 2\nUp and down dpad to move servo(s)\nB to go back to selection\n\nPos:", servo.getPositions());
            telemetry.update();
        }
    }
    private static ServoGroup[] initServoGroups(HardwareMap hardwareMap, ServoGroup[] groups){
        List<String> singleServoNames = Arrays.asList(hardwareMap.servo.entrySet().stream().map(Map.Entry::getKey).toArray(String[]::new)); // all servo names
        for(ServoGroup group : groups){
            for(String servoName : group.servoNames){
                singleServoNames.remove(servoName); // remove grouped servos
            }
        }
        ServoGroup[] result = new ServoGroup[singleServoNames.size() + groups.length];
        for(int i = 0; i < singleServoNames.size(); i++){
            result[i] = ServoGroup.makeServo(hardwareMap, singleServoNames.get(i));
        }
        for(int i = 0; i < groups.length; i++){
            result[singleServoNames.size() + i] = groups[i];
        }
        return result;
    }
    public ServoGroup getServo(){
        boolean dpad_right_prev = true;
        boolean dpad_left_prev = true;
        while(!gamepad2.a && !isStopRequested()){
            if(gamepad2.dpad_right && !dpad_right_prev){
                num = (num + 1) % SERVO_LIST.length;
                dpad_right_prev = true;
            }else if(gamepad2.dpad_left && !dpad_left_prev){
                num = (num + SERVO_LIST.length - 1) % SERVO_LIST.length;
                dpad_left_prev = true;
            }
            if(!gamepad2.dpad_right){
                dpad_right_prev = false;
            }
            if(!gamepad2.dpad_left){
                dpad_left_prev = false;
            }
            telemetry.addData("Use controller 2\nLeft and right dpad to scroll\nA to select\n\nSelected servo(s): ", SERVO_LIST[num]);
            telemetry.update();
        }
        return SERVO_LIST[num];
    }

    // From Luke :)

    public static class ServoGroup {
        private final String[] servoNames;
        private final Servo[] servos;
        private final boolean[] directions;
        public ServoGroup(HardwareMap hardwareMap, String[] servoNames, boolean... directions) {
            this.servoNames = servoNames;
            this.directions = directions;
            servos = new Servo[servoNames.length];
            for(int i = 0; i < servos.length; i++){
                servos[i] = hardwareMap.get(Servo.class, servoNames[i]);
            }
        }
        public void Move(boolean direction){ // true is forward
            for(int i = 0; i < servos.length; i++){
                servos[i].setPosition(servos[i].getPosition() + (direction ^ directions[i] ? STEP : -STEP));
            }
        }
        @NonNull
        public String toString(){
            return Arrays.toString(servoNames);
        }
        public String getPositions(){
            StringBuilder result = new StringBuilder(servos.length * 16);
            for(int i = 0; i < servos.length; i++){
                result.append("\n\t").append(servoNames[i]).append(": ").append(servos[i].getPosition());
            }
            return result.toString();
        }
        private static ServoGroup makeServoPair(HardwareMap hardwareMap, String servo1, String servo2) {
            return new ServoGroup(hardwareMap, new String[]{servo1,servo2}, true, true);
        }
        private static ServoGroup makeAntiServoPair(HardwareMap hardwareMap, String servo1, String servo2) {
            return new ServoGroup(hardwareMap, new String[]{servo1,servo2}, true, false);
        }
        private static ServoGroup makeServo(HardwareMap hardwareMap, String servo) {
            return new ServoGroup(hardwareMap, new String[] {servo}, true);
        }
    }
}