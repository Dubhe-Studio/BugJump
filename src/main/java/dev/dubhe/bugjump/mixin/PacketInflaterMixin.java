package dev.dubhe.bugjump.mixin;

import net.minecraft.network.CompressionDecoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CompressionDecoder.class)
public abstract class PacketInflaterMixin {
    @ModifyConstant(method = "decode",constant = @Constant(intValue = 2097152))
    private int decode(int old) {
        return 0x7FFFFFFF;
    }
}
