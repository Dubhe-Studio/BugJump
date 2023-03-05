package dev.dubhe.bugjump.mixin;

import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin {

    @ModifyConstant(method = "readNbt()Lnet/minecraft/nbt/NbtCompound;", constant = @Constant(longValue = 2097152L))
    private long readNbt(long old) {
        return 0x7FFFFFFFL;
    }
}
