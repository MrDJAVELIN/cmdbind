package org.djavelin.cmdbind.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.djavelin.cmdbind.client.command.BindCommand;
import org.djavelin.cmdbind.client.key.BindManager;

public class CmdbindClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BindManager.load();

        CommandRegistrationCallback.EVENT.register(BindCommand::register);

        ClientTickEvents.END_CLIENT_TICK.register(KeyHandler::onClientTick);
    }
}
