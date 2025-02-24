package org.firstinspires.ftc.teamcode;

public class MotionProfiler {
    private final double MAX_VELOCITY, MAX_ACCELERATION;
    private boolean isOver = true;
    private boolean isDone = false;
    private double tempMaxAccel, tempMaxVel;
    private double start_pos, final_pos, distance, accelerationDt, halfwayDistance, accelerationDistance, newMaxVelocity, deacceleration_dt, cruiseDistance, cruiseDt, deaccelerationTime, entire_dt;
    public MotionProfiler(double MAX_VELOCITY, double MAX_ACCELERATION){
        this.MAX_ACCELERATION = MAX_ACCELERATION;
        this.MAX_VELOCITY = MAX_VELOCITY;
    }
    //bro lightning did a python coder make this
    public void initNewProfile(double startPos, double finalPos){
        this.start_pos = startPos;
        this.final_pos = finalPos;
        isOver = false;

        distance = finalPos-startPos;

        if(distance < 0){
            tempMaxVel = -MAX_VELOCITY;
            tempMaxAccel = -MAX_ACCELERATION;
        }else{
            tempMaxAccel = MAX_ACCELERATION;
            tempMaxVel = MAX_VELOCITY;
        }

        // calculate the time it takes to accelerate to max velocity
        accelerationDt = tempMaxVel / tempMaxAccel;

        // If we can't accelerate to max velocity in the given distance, we'll accelerate as much as possible
        halfwayDistance = distance / 2;
        accelerationDistance = 0.5 * tempMaxAccel * accelerationDt * accelerationDt;

        if (Math.abs(accelerationDistance) > Math.abs(halfwayDistance)) {
            accelerationDt = Math.sqrt(Math.abs(halfwayDistance / (0.5 * tempMaxAccel)));
        }
        accelerationDistance = 0.5 * tempMaxAccel * accelerationDt * accelerationDt;

        // recalculate max velocity based on the time we have to accelerate and decelerate
        newMaxVelocity = tempMaxAccel * accelerationDt;

        // we decelerate at the same rate as we accelerate
        deacceleration_dt = accelerationDt;

        // calculate the time that we're at max velocity
        cruiseDistance = distance - 2 * accelerationDistance;
        cruiseDt = cruiseDistance / newMaxVelocity;
        deaccelerationTime = accelerationDt + cruiseDt;

        // check if we're still in the motion profile
        entire_dt = accelerationDt + cruiseDt + deacceleration_dt;
    }

    public double motionProfilePos(double currentDt) {
//        Return the current reference position based on the given motion profile times, maximum acceleration, velocity, and current time.

        if (currentDt > entire_dt) {
            isOver = true;
            isDone = true;
            return final_pos;
        }

        // if we're accelerating
        if (currentDt < accelerationDt)
            // use the kinematic equation for acceleration
            return start_pos + 0.5 * tempMaxAccel * currentDt * currentDt;

            // if we're cruising
        else if (currentDt < deaccelerationTime) {
            accelerationDistance = 0.5 * tempMaxAccel * accelerationDt * accelerationDt;
            double cruiseCurrentDt = currentDt - accelerationDt;

            // use the kinematic equation for constant velocity
            return start_pos + accelerationDistance + newMaxVelocity * cruiseCurrentDt;
        }

        // if we're decelerating
        else {
            accelerationDistance = 0.5 * tempMaxAccel * accelerationDt * accelerationDt;
            cruiseDistance = newMaxVelocity * cruiseDt;
            deaccelerationTime = currentDt - deaccelerationTime;

            // use the kinematic equations to calculate the instantaneous desired position
            return start_pos + accelerationDistance + cruiseDistance + newMaxVelocity * deaccelerationTime - 0.5 * tempMaxAccel * deaccelerationTime * deaccelerationTime;
        }
    }

    public double motionProfileVel(double currentDt) {
//
        if (currentDt > entire_dt)
            return 0;

        // if we're accelerating
        if (currentDt < accelerationDt)
            // use the kinematic equation for acceleration
            return tempMaxAccel *currentDt;

            // if we're cruising
        else if (currentDt < deaccelerationTime) {
            return newMaxVelocity;
        }

        // if we're decelerating
        else {
            deaccelerationTime = currentDt - deaccelerationTime;

            // use the kinematic equations to calculate the instantaneous desired position
            return newMaxVelocity - deaccelerationTime;
        }
    }

    public double motionProfileAccel(double currentDt) {

        if (currentDt > entire_dt)
            return 0;

        // if we're accelerating
        if (currentDt < accelerationDt)
            // use the kinematic equation for acceleration
            return tempMaxAccel;

            // if we're cruising
        else if (currentDt < deaccelerationTime) {

            return 0;
        }

        // if we're decelerating
        else {
            // use the kinematic equations to calculate the instantaneous desired position
            return -tempMaxAccel;
        }
    }

    public double getEntireDt(){
        return entire_dt;
    }

    public boolean isOver(){
        return isOver;
    }

    public boolean isDone(){
        return isDone;
    }
}

