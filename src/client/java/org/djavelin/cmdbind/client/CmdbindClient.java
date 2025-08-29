package org.djavelin.cmdbind.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.djavelin.cmdbind.client.command.BindCommand;
import org.djavelin.cmdbind.client.key.BindManager;

public class CmdbindClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BindManager.load();

        ClientCommandRegistrationCallback.EVENT.register(BindCommand::register);

        ClientTickEvents.END_CLIENT_TICK.register(KeyHandler::onClientTick);
    }
}
