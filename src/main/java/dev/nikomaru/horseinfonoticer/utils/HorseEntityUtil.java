package dev.nikomaru.horseinfonoticer.utils;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

import java.util.List;
import java.util.Objects;

public class HorseEntityUtil {

    public static double getSpeed(AbstractHorse entity) {
        return Objects.requireNonNull(entity.getAttribute(Attributes.MOVEMENT_SPEED)).getValue();
    }

    public static double getJumpStrength(AbstractHorse entity) {
        return Objects.requireNonNull(entity.getAttribute(Attributes.JUMP_STRENGTH)).getValue();
    }


    public static String getEvaluateRankString(AbstractHorse entity) {
        return HorseInfoStats.calcEvaluateRankString(getSpeed(entity), getJumpStrength(entity));
    }

    public static List<String> getStatsStrings(AbstractHorse entity) {
        return HorseInfoFormat.formatHorseStats(
                entity.getHealth(),
                entity.getMaxHealth(),
                getSpeed(entity),
                getJumpStrength(entity));
    }

}
