package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepAutoPathTesting1 {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23.5, -60, Math.toRadians(90)))

                .waitSeconds(2)
                .lineToY(-35)
                .waitSeconds(3)
                .strafeTo(new Vector2d(48,-38))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,-55),Math.toRadians(225))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(57.5,-38),Math.toRadians(90))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,-55),Math.toRadians(225))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,-38),Math.toRadians(180))
                .strafeTo(new Vector2d(55,-25))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,-55),Math.toRadians(225))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}