package org.djavelin.cmdbind.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BindCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment env) {

        dispatcher.register(CommandManager.literal("createbind")
                .then(CommandManager.argument("key", StringArgumentType.string())
                        .then(CommandManager.argument("command", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    String key = StringArgumentType.getString(ctx, "key");
                                    String command = StringArgumentType.getString(ctx, "command");
                                    BindManager.addBind(key, command);
                                    ctx.getSource().sendFeedback(() -> Text.literal("Bind created: " + key + " -> " + command), false);
                                    return 1;
                                }))));

        dispatcher.register(CommandManager.literal("listbinds")
                .executes(ctx -> {
                    if (BindManager.getBinds().isEmpty()) {
                        ctx.getSource().sendFeedback(() -> Text.literal("No bindings"), false);
                    } else {
                        BindManager.getBinds().forEach((k, v) ->
                                ctx.getSource().sendFeedback(() -> Text.literal(k + " -> " + v), false)
                        );
                    }
                    return 1;
                }));

        dispatcher.register(CommandManager.literal("deletebind")
                .then(CommandManager.argument("key", StringArgumentType.string())
                        .executes(ctx -> {
                            String key = StringArgumentType.getString(ctx, "key");
                            if (BindManager.removeBind(key)) {
                                ctx.getSource().sendFeedback(() -> Text.literal("Bind deleted: " + key), false);
                            } else {
                                ctx.getSource().sendFeedback(() -> Text.literal("Bind not found"), false);
                            }
                            return 1;
                        })));
    }
}
