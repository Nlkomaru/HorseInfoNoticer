package dev.nikomaru.horseinfonoticer.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Horse;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class RenderUtil {

    public static void renderEntityInfo(Entity entity, List<String> infoString, PoseStack matrixStackIn, MultiBufferSource bufferIn,
            int packedLightIn) {
        var mc = Minecraft.getInstance();
        if (Objects.requireNonNull(mc.player).equals(EntityUtil.getRider(entity))) {
            return;
        }
        var d0 = entity.distanceToSqr(Objects.requireNonNull(mc.getCameraEntity()));
        if (d0 >= 2048.0D) {
            return;
        }
        var scale = 0.025f;

        //ランクの取得
        var rank = HorseEntityUtil.getEvaluateRankString((Horse) entity);
        //ランクから色を取得
        var baseColor = HorseInfoStats.calcEvaluateRankColor(rank);


        var titleColor = baseColor;
        if (baseColor == Color.BLACK) {
            titleColor = Color.WHITE;
        }
        var fontColor = Color.WHITE;
        var f = entity.getBbHeight() + 2.0f;
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0, f, 0.0);
        matrixStackIn.mulPose(mc.gameRenderer.getMainCamera().rotation());
        matrixStackIn.scale(-scale, -scale, scale);
        var fontHeight = 10;
        float baseY = (4 - infoString.size()) * fontHeight - ((EntityUtil.getRider(entity) != null) ? fontHeight * 3 : fontHeight);

        var width = mc.font.width(entity.getName().getString());
        for (var s : infoString) {
            width = Math.max(mc.font.width(s), width);
        }
        var widthHarf = width / 2;

        var matrix4f = matrixStackIn.last().pose();
        var f1 = mc.options.getBackgroundOpacity(0.4f);
        var r = (baseColor.getRed() / 255.0F) / 2.0F;
        var g = (baseColor.getGreen() / 255.0F) / 2.0F;
        var b = (baseColor.getBlue() / 255.0F) / 2.0F;
        var j = ((int) (f1 * 255.0F) << 24) + ((int) (r * 255.0F) << 16) + ((int) (g * 255.0F) << 8) + ((int) (b * 255.0F));

        for (var i = 0; i < infoString.size(); i++) {
            mc.font.drawInBatch(infoString.get(i), -widthHarf, (int) baseY + fontHeight * i, (i == 0) ? titleColor.getRGB() : fontColor.getRGB(),
                    false, matrix4f, bufferIn, false, j, packedLightIn);
        }
        matrixStackIn.popPose();
    }

}
