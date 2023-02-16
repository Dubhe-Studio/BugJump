package dev.dubhe.bugjump.mixin;

import dev.dubhe.bugjump.BugJumpClient;
import dev.dubhe.bugjump.BugJumpLoadingScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceReload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SplashOverlay.class)
public class SplashOverlayMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    private static BugJumpLoadingScreen screen;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(MinecraftClient client, ResourceReload monitor, Consumer<Optional<Throwable>> exceptionHandler, boolean reloading, CallbackInfo ci) {
        screen = new BugJumpLoadingScreen(this.client);
    }

    @Inject(method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci,
                        int i, int j, long l, float f) {
        if (BugJumpClient.config.bugjumpTitle) screen.renderPatches(matrices, delta, f >= 1.0f);
    }
}
