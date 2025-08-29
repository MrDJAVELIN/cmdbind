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
                                    BindManager.addBind(key, command);
                                    ctx.getSource().sendFeedback(Text.literal("Bind created: " + key + " -> " + command));
                                    return 1;
                                }))));

        dispatcher.register(ClientCommandManager.literal("listbinds")
                .executes(ctx -> {
                    if (BindManager.getBinds().isEmpty()) {
                        ctx.getSource().sendFeedback(Text.literal("No bindings"));
                    } else {
                        BindManager.getBinds().forEach((k, v) ->
                                ctx.getSource().sendFeedback(Text.literal(k + " -> " + v))
                        );
                    }
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("deletebind")
                .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(ctx -> {
                            String key = StringArgumentType.getString(ctx, "key");
                            if (BindManager.removeBind(key)) {
                                ctx.getSource().sendFeedback(Text.literal("Bind deleted: " + key));
                            } else {
                                ctx.getSource().sendFeedback(Text.literal("Bind not found"));
                            }
                            return 1;
                        })));
    }
}
