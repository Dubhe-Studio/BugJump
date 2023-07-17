package dev.dubhe.bugjump.mixin;

import net.minecraft.client.gui.font.CodepointMap;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontSet.class)
public abstract class FontSetMixin {
    @Shadow @Final private CodepointMap<BakedGlyph> glyphs;

    @Shadow protected abstract BakedGlyph computeBakedGlyph(int i);

    @Inject(method = "getGlyph", at = @At("HEAD"), cancellable = true)
    private synchronized void getGlyph(int i, CallbackInfoReturnable<BakedGlyph> cir) {
        cir.setReturnValue(
                this.glyphs.computeIfAbsent(i, this::computeBakedGlyph)
        );
    }
}