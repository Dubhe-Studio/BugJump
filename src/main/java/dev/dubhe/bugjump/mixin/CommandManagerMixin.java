package dev.dubhe.bugjump.mixin;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.bugjump.commands.BugJumpCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public abstract class CommandManagerMixin {
    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"))
    private void register(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo ci) {
        BugJumpCommand.register(this.dispatcher);
    }
}
