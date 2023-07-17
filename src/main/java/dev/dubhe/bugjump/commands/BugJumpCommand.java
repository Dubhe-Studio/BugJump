package dev.dubhe.bugjump.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.dubhe.bugjump.BugJumpClient;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BugJumpCommand {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("bugjump")
                .then(Commands.literal("reload").executes(BugJumpCommand::reload)));
    }

    public static int reload(CommandContext<CommandSourceStack> context) {
        if (!BugJumpClient.CONFIG_FILE.isFile()) {
            try (FileWriter writer = new FileWriter(BugJumpClient.CONFIG_FILE)) {
                GSON.toJson(BugJumpClient.config, writer);
                context.getSource().sendSuccess(()->Component.literal("BugJump's Config is reloaded").withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), false);
            } catch (IOException e) {
                e.printStackTrace();
                context.getSource().sendFailure(Component.literal("BugJump's Config is reload failed").withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            }
        } else {
            try (FileReader reader = new FileReader(BugJumpClient.CONFIG_FILE)) {
                BugJumpClient.config = GSON.fromJson(reader, BugJumpClient.BugJumpConfig.class);
                context.getSource().sendSuccess(()->Component.literal("BugJump's Config is reloaded").withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), false);
            } catch (IOException e) {
                e.printStackTrace();
                context.getSource().sendFailure(Component.literal("BugJump's Config is reload failed").withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            }
        }
        return 0;
    }
}
