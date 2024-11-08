package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepAutoPaths {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, -60, Math.toRadians(90)))
                .lineToY(-35)
                .waitSeconds(3)
                .strafeTo(new Vector2d(-48,-38))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-55),Math.toRadians(225))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-57.5,-38),Math.toRadians(90))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-55),Math.toRadians(225))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-38),Math.toRadians(180))
                .strafeTo(new Vector2d(-55,-25))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(-55,-55),Math.toRadians(225))
                .build());

        RoadRunnerBotEntity myBot2 = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(new ColorSchemeBlueDark())
                .build();
        myBot2.runAction(myBot2.getDrive().actionBuilder(new Pose2d(10, 60, Math.toRadians(270)))
                .lineToY(35)
                .waitSeconds(3)
                .strafeTo(new Vector2d(48,38))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,55),Math.toRadians(45))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(57.5,38),Math.toRadians(270))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,55),Math.toRadians(45))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,38),Math.toRadians(0))
                .strafeTo(new Vector2d(55,25))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55,55),Math.toRadians(45))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot2)
                .addEntity(myBot)
                .start();
    }
}