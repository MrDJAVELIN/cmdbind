package org.djavelin.cmdbind;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.djavelin.cmdbind.util.BindCommand;
import org.djavelin.cmdbind.util.BindManager;

public class Cmdbind implements ModInitializer {

    @Override
    public void onInitialize() {
        BindManager.load();

        CommandRegistrationCallback.EVENT.register(BindCommand::register);
    }
}
