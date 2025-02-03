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

        RoadRunnerBotEntity myBot = quickBot(meepMeep,0, 0);

        RoadRunnerBotEntity myBot1 = quickBot(meepMeep,1, 1);
        //RoadRunnerBotEntity myBot1a = quickBot(meepMeep,1, 1);

        RoadRunnerBotEntity myBot2 = quickBot(meepMeep, 2, 0);

        RoadRunnerBotEntity myBot3 = quickBot(meepMeep,3, 1);
        //RoadRunnerBotEntity myBot3a = quickBot(meepMeep,3, 1);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myBot1)
                //.addEntity(myBot1a)
                .addEntity(myBot2)
                .addEntity(myBot3)
                //.addEntity(myBot3a)
                .start();
    }

    private static RoadRunnerBotEntity quickBot(MeepMeep meepMeep,int quadrant, int type){
        RoadRunnerBotEntity bot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                // TODO make this accurate
                .setDimensions(13, 15)
                .setColorScheme(quadrant>= 2?new ColorSchemeRedDark() : new ColorSchemeBlueDark())
                .build();
        Action action = null;
        if (type == 2) {
            action = getPark(bot, quadrant);
        } else if (quadrant % 2 == 0) {
            action = getPathBucket(bot, quadrant);
        } else if (type == 1) {
            action = getPathSpecimenOptimized(bot, quadrant);
        } else {
            action = getPathSpecimen(bot, quadrant);
        }
        bot.runAction(action);
        return bot;
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

    private static Action getPark(RoadRunnerBotEntity myBot, int quadrant) {
        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>=2?-1:1;
        return myBot.getDrive().actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .strafeTo(new Vector2d(60 * xFactor, 60 * yFactor))
                .build();
    }

    private static Action getPathSpecimenOptimized(RoadRunnerBotEntity myBot, int quadrant) {
        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>=2?-1:1;
        return myBot.getDrive().actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90,quadrant)))
                .waitSeconds(0.5)
                // TODO figure out static claw positioning (left (90) or right (270))
                // Preloaded samples are at y = 24 inches, and the static claw is 12 inches long
                .strafeToSplineHeading(new Vector2d(38 * xFactor, 35 * yFactor), Math.toRadians(getAngle(270,quadrant)))
                .waitSeconds(1)
                .turn(-Math.toRadians(90))
                // Human player zone is at y = 60 inches
                .strafeTo(new Vector2d(38 * xFactor, 54 * yFactor))
                .strafeToSplineHeading(new Vector2d(48 * xFactor, 35 * yFactor), Math.toRadians(getAngle(270,quadrant)))
                .waitSeconds(1)
                .turn(-Math.toRadians(90))
                .strafeTo(new Vector2d(48 * xFactor, 54 * yFactor))
                .strafeToSplineHeading(new Vector2d(58 * xFactor, 35 * yFactor), Math.toRadians(getAngle(270,quadrant)))
                .waitSeconds(1)
                .turn(-Math.toRadians(90))
                .strafeTo(new Vector2d(58 * xFactor, 54 * yFactor))
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .turn(Math.toRadians(180))
                .build();
    }

    private static Action getPathSpecimen(RoadRunnerBotEntity myBot, int quadrant) {
        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>=2?-1:1;
        return myBot.getDrive().actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(48 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(58 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(69 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(8 * xFactor, 33 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(90, quadrant)))
                .waitSeconds(1)
                .turnTo(Math.toRadians(getAngle(270, quadrant)))
                .build();
    }

    private static Action getPathBucket(RoadRunnerBotEntity myBot, int quadrant){
        int xFactor = quadrant%3==0?1:-1;
        int yFactor = quadrant>=2?-1:1;
        return myBot.getDrive().actionBuilder(new Pose2d(10*xFactor, 60*yFactor, Math.toRadians(getAngle(270,quadrant))))
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(225, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(48 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(225, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(58 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(225, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(69 * xFactor, 38 * yFactor), Math.toRadians(getAngle(270, quadrant)))
                .waitSeconds(0.5)
                .strafeToSplineHeading(new Vector2d(60 * xFactor, 60 * yFactor), Math.toRadians(getAngle(225, quadrant)))
                .waitSeconds(1)
                .turnTo(Math.toRadians(getAngle(270, quadrant)))
                .build();
    }
}
