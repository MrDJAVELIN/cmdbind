package org.djavelin.cmdbind.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.djavelin.cmdbind.util.BindManager;

import java.util.HashSet;
import java.util.Set;

public class KeyHandler {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Set<Integer> pressed = new HashSet<>();

    public static void onClientTick(MinecraftClient client) {
        if (client.currentScreen != null) return;

        long window = client.getWindow().getHandle();

        for (String key : BindManager.getBinds().keySet()) {
            InputUtil.Key inputKey = InputUtil.fromTranslationKey("key.keyboard." + key.toLowerCase());

            int keyCode = inputKey.getCode();
            if (keyCode == InputUtil.UNKNOWN_KEY.getCode()) {
                continue;
            }

            boolean isPressed = InputUtil.isKeyPressed(window, keyCode);

            if (isPressed && !pressed.contains(keyCode)) {
                String command = BindManager.getCommand(key);
                if (command != null && client.player != null) {
                    client.player.networkHandler.sendChatCommand(command);
                }
                pressed.add(keyCode);
            } else if (!isPressed && pressed.contains(keyCode)) {
                pressed.remove(keyCode);
            }
        }
    }
}
