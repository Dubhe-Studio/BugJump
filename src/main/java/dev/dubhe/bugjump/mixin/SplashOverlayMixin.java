package dev.dubhe.bugjump.mixin;

import net.minecraft.client.gui.screen.SplashOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SplashOverlay.class)
public class SplashOverlayMixin{
    @ModifyConstant(method = "<clinit>", constant = @Constant(stringValue = "textures/gui/title/mojangstudios.png"))
    private static String logo(String constant) {
        return "bugjump:textures/gui/title/mojangstudios.png";
    }
}
