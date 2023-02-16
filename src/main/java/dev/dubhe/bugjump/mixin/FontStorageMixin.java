package dev.dubhe.bugjump.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.RenderableGlyph;
import org.jetbrains.annotations.Nullable;
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
    private Int2ObjectMap<Glyph> glyphCache;

    @Shadow
    @Nullable
    protected abstract Glyph getEmptyGlyph(int codePoint);

    @Shadow
    protected abstract RenderableGlyph getRenderableGlyph(int codePoint);

    @Inject(method = "getGlyph", at = @At("HEAD"), cancellable = true)
    private synchronized void getGlyph(int codePoint, CallbackInfoReturnable<Glyph> cir) {
        cir.setReturnValue(
                this.glyphCache.computeIfAbsent(codePoint, (codePointx) -> {
                    Glyph glyph = this.getEmptyGlyph(codePointx);
                    return glyph == null ? this.getRenderableGlyph(codePointx) : glyph;
                })
        );
    }
}
