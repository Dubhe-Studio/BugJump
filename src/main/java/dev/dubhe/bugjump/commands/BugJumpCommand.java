package dev.dubhe.bugjump.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.dubhe.bugjump.BugJumpClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BugJumpCommand {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("bugjump")
                .then(CommandManager.literal("reload").executes(BugJumpCommand::reload)));
    }

    public static int reload(CommandContext<ServerCommandSource> context) {
        if (!BugJumpClient.CONFIG_FILE.isFile()) {
            try (FileWriter writer = new FileWriter(BugJumpClient.CONFIG_FILE)) {
                GSON.toJson(BugJumpClient.config, writer);
                context.getSource().sendMessage(Text.of("BugJump's Config is reloaded").getWithStyle(Style.EMPTY.withColor(Formatting.GREEN)).get(0));
            } catch (IOException e) {
                e.printStackTrace();
                context.getSource().sendMessage(Text.of("BugJump's Config is reload failed").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0));
            }
        } else {
            try (FileReader reader = new FileReader(BugJumpClient.CONFIG_FILE)) {
                BugJumpClient.config = GSON.fromJson(reader, BugJumpClient.BugJumpConfig.class);
                context.getSource().sendMessage(Text.of("BugJump's Config is reloaded").getWithStyle(Style.EMPTY.withColor(Formatting.GREEN)).get(0));
            } catch (IOException e) {
                e.printStackTrace();
                context.getSource().sendMessage(Text.of("BugJump's Config is reload failed").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0));
            }
        }
        return 0;
    }
}
