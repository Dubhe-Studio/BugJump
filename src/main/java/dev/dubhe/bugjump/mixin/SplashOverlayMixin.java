package dev.dubhe.bugjump.mixin;

import dev.dubhe.bugjump.BugJumpClient;
import dev.dubhe.bugjump.BugJumpLoadingScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(LoadingOverlay.class)
public abstract class SplashOverlayMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @SuppressWarnings("all")
    private static BugJumpLoadingScreen screen;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Minecraft minecraft, ReloadInstance reloadInstance, Consumer<Optional<Throwable>> consumer, boolean bl, CallbackInfo ci) {
        screen = new BugJumpLoadingScreen(this.minecraft);
    }

    @Inject(method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIFFIIII)V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        if (BugJumpClient.config.bugjumpTitle) screen.renderPatches(guiGraphics, f, f >= 1.0f);
    }
}
