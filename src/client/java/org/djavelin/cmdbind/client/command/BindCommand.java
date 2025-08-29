package org.djavelin.cmdbind.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import org.djavelin.cmdbind.client.key.BindManager;

public class BindCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess) {

        dispatcher.register(ClientCommandManager.literal("createbind")
                .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    String key = StringArgumentType.getString(ctx, "key");
                                    String command = StringArgumentType.getString(ctx, "command");
                                    if (key.length() > 1) {
                                        ctx.getSource().sendFeedback(Text.literal(""));
                                        ctx.getSource().sendFeedback(Text.literal("CmdBind"));
                                        ctx.getSource().sendFeedback(Text.literal("Incorrect key"));
                                        ctx.getSource().sendFeedback(Text.literal(""));
                                    } else {
                                        BindManager.addBind(key, command);
                                        ctx.getSource().sendFeedback(Text.literal(""));
                                        ctx.getSource().sendFeedback(Text.literal("CmdBind"));
                                        ctx.getSource().sendFeedback(Text.literal("Bind created: " + key + " -> " + command));
                                        ctx.getSource().sendFeedback(Text.literal(""));
                                    }
                                    return 1;
                                }))));

        dispatcher.register(ClientCommandManager.literal("listbinds")
                .executes(ctx -> {
                    if (BindManager.getBinds().isEmpty()) {
                        ctx.getSource().sendFeedback(Text.literal(""));
                        ctx.getSource().sendFeedback(Text.literal("Binds:"));
                        ctx.getSource().sendFeedback(Text.literal("No bindings"));
                        ctx.getSource().sendFeedback(Text.literal(""));
                    } else {
                        ctx.getSource().sendFeedback(Text.literal(""));
                        ctx.getSource().sendFeedback(Text.literal("Binds:"));
                        BindManager.getBinds().forEach((k, v) -> {
                            ctx.getSource().sendFeedback(Text.literal(k + " -> " + v));
                        });
                        ctx.getSource().sendFeedback(Text.literal(""));
                    }
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("deletebind")
                .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(ctx -> {
                            String key = StringArgumentType.getString(ctx, "key");
                                if (key.length() > 1) {
                                    ctx.getSource().sendFeedback(Text.literal(""));
                                    ctx.getSource().sendFeedback(Text.literal("CmdBind"));
                                    ctx.getSource().sendFeedback(Text.literal("Incorrect key"));
                                    ctx.getSource().sendFeedback(Text.literal(""));
                                } else {
                                if (BindManager.removeBind(key)) {
                                    ctx.getSource().sendFeedback(Text.literal(""));
                                    ctx.getSource().sendFeedback(Text.literal("CmdBind"));
                                    ctx.getSource().sendFeedback(Text.literal("Bind deleted: " + key));
                                    ctx.getSource().sendFeedback(Text.literal(""));
                                } else {
                                    ctx.getSource().sendFeedback(Text.literal(""));
                                    ctx.getSource().sendFeedback(Text.literal("CmdBind"));
                                    ctx.getSource().sendFeedback(Text.literal("Bind not found"));
                                    ctx.getSource().sendFeedback(Text.literal(""));
                                }
                            }
                            return 1;
                        })));
    }
}
