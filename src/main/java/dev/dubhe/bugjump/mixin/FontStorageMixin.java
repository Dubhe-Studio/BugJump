package dev.dubhe.bugjump.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontStorage.class)
public abstract class FontStorageMixin {
    @Shadow
    @Final
    private Int2ObjectMap<FontStorage.GlyphPair> glyphCache;

    @Shadow
    protected abstract FontStorage.GlyphPair findGlyph(int codePoint);

    @Inject(method = "getGlyph", at = @At("HEAD"), cancellable = true)
    private synchronized void getGlyph(int codePoint, boolean validateAdvance, CallbackInfoReturnable<Glyph> cir) {
        cir.setReturnValue(
                this.glyphCache.computeIfAbsent(codePoint, (codePointx) -> {
                    Glyph glyph = this.getEmptyGlyph(codePointx);
                    return glyph == null ? this.getRenderableGlyph(codePointx) : glyph;
                })
        );
    }
}
