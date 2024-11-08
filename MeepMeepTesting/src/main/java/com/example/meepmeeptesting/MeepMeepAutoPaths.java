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

        RoadRunnerBotEntity myBot = quickBot(meepMeep,0);
        myBot.runAction(getPathA(myBot,0));

        RoadRunnerBotEntity myBot2 = quickBot(meepMeep, 2);
        myBot2.runAction(getPathA(myBot2,2));

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot2)
                .addEntity(myBot)
                .start();
    }

    private static RoadRunnerBotEntity quickBot(MeepMeep meepMeep,int quadrant){
        return new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(quadrant>= 2?new ColorSchemeRedDark() : new ColorSchemeBlueDark())
                .build();
    }

    private static int getAngle(int angle, int quadrant) {
        switch(quadrant) {
            case 0: return angle;
            case 1: return 180-angle;
            case 2: return 180+angle;
            case 3: return 360-angle;
            default: return 0;
        }
    }
    private static Action getPathA(RoadRunnerBotEntity myBot, int quadrant){
        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>= 2?-1:1;
        return myBot.getDrive().actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .lineToY(35*yFactor)
                .waitSeconds(3)
                .strafeTo(new Vector2d(48*xFactor,38*yFactor))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55*xFactor,55*yFactor),Math.toRadians(getAngle(45,quadrant)))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(57.5*xFactor,38*yFactor),Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55*xFactor,55*yFactor),Math.toRadians(getAngle(45,quadrant)))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55*xFactor,38*yFactor),Math.toRadians(getAngle(0,quadrant)))
                .strafeTo(new Vector2d(55*xFactor,25*yFactor))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(55*xFactor,55*yFactor),Math.toRadians(getAngle(45,quadrant)))
                .build();
    }
}