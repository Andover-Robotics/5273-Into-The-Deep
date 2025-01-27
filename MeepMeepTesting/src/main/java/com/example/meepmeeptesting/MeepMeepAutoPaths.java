package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepAutoPaths {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot1 = quickBot(meepMeep,1);

        RoadRunnerBotEntity myBot = quickBot(meepMeep,0);

        RoadRunnerBotEntity myBot2 = quickBot(meepMeep, 2);

        RoadRunnerBotEntity myBot3 = quickBot(meepMeep,3);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myBot1)
                .addEntity(myBot2)
                .addEntity(myBot3)
                .start();
    }

    private static RoadRunnerBotEntity quickBot(MeepMeep meepMeep,int quadrant){
        return new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(quadrant>= 2?new ColorSchemeRedDark() : new ColorSchemeBlueDark())
                .build();
    }

}
