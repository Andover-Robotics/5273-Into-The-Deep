package org.firstinspires.ftc.teamcode;

public class MotionProfiler {

    private final double MAX_VELOCITY, MAX_ACCELERATION;
    private boolean isOver = true;
    private boolean isDone = false;
    private double tempMaxAccel, tempMaxVel;
    private double startPos, finalPos, distance, accelerationDt, halfwayDistance, accelerationDistance, newMaxVelocity, deaccelerationDt, cruiseDistance, cruiseDt, deaccelerationTime, entireDt;

    public MotionProfiler(double MAX_VELOCITY, double MAX_ACCELERATION){
        this.MAX_ACCELERATION = MAX_ACCELERATION;
        this.MAX_VELOCITY = MAX_VELOCITY;
    }
    //bro lightning did a python coder make this
    public void initNewProfile(double startPos, double finalPos){
        this.startPos = startPos;
        this.finalPos = finalPos;
        isOver = false;
        distance = finalPos-startPos;

        // if we are going in a backwards direction then max velocity and accelerations have to be backwards too
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
        deaccelerationDt = accelerationDt;

        // calculate the time that we're at max velocity
        cruiseDistance = distance - 2 * accelerationDistance;
        cruiseDt = cruiseDistance / newMaxVelocity;
        deaccelerationTime = accelerationDt + cruiseDt;

        // check if we're still in the motion profile
        entireDt = accelerationDt + cruiseDt + deaccelerationDt;
    }

    public double motionProfilePos(double currentDt) {
//        Return the current reference position based on the given motion profile times, maximum acceleration, velocity, and current time.

        if (currentDt > entireDt) {
            isOver = true;
            isDone = true;
            return finalPos;
        }

        // if we're accelerating
        if (currentDt < accelerationDt)
            // use the kinematic equation for acceleration
            return startPos + 0.5 * tempMaxAccel * currentDt * currentDt;

            // if we're cruising
        else if (currentDt < deaccelerationTime) {
            accelerationDistance = 0.5 * tempMaxAccel * accelerationDt * accelerationDt;
            double cruiseCurrentDt = currentDt - accelerationDt;

            // use the kinematic equation for constant velocity
            return startPos + accelerationDistance + newMaxVelocity * cruiseCurrentDt;
        }

        // if we're decelerating
        else {
            accelerationDistance = 0.5 * tempMaxAccel * accelerationDt * accelerationDt;
            cruiseDistance = newMaxVelocity * cruiseDt;
            deaccelerationTime = currentDt - deaccelerationTime;

            // use the kinematic equations to calculate the instantaneous desired position
            return startPos + accelerationDistance + cruiseDistance + newMaxVelocity * deaccelerationTime - 0.5 * tempMaxAccel * deaccelerationTime * deaccelerationTime;
        }
    }

    public double motionProfileVel(double currentDt) {
        //velocity should be 0 after everything has been finished
        if (currentDt > entireDt)
            return 0;

        // if we're accelerating then the velocity should be accel * elapsed time (basic physics equation)
        if (currentDt < accelerationDt)
            // use the kinematic equation for acceleration
            return tempMaxAccel *currentDt;

            // if we're cruising then it should be running at the set velocity
        else if (currentDt < deaccelerationTime) {
            return newMaxVelocity;
        }

        // if we're decelerating then do it based on the newMaxVelocity and how long the decel has been happening
        else {
            deaccelerationTime = currentDt - deaccelerationTime;

            // use the kinematic equations to calculate the instantaneous desired position
            return newMaxVelocity - deaccelerationTime;
        }
    }

    //returns the amount that you should accelerate by for the current profiler
    public double motionProfileAccel(double currentDt) {

        //if the movement time has passed then you shouldn't accelerate anymore
        if (currentDt > entireDt)
            return 0;

        // if we're accelerating
        if (currentDt < accelerationDt)
            // use the kinematic equation for acceleration
            return tempMaxAccel;

        // if we're cruising then theres no need to accelerate
        else if (currentDt < deaccelerationTime) {
            return 0;
        }

        // only other possibility is if we're decelerating, accelerate the negative amount
        else {
            // use the kinematic equations to calculate the instantaneous desired position
            return -tempMaxAccel;
        }
    }

    public double getEntireDt(){
        return entireDt;
    }

    public boolean isOver(){
        return isOver;
    }

    public boolean isDone(){
        return isDone;
    }
}

