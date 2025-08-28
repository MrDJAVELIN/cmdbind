package org.djavelin.cmdbind.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.djavelin.cmdbind.key.ConfigUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BindManager {
    private static final File FILE = new File("config/cmdbinds.json");
    private static final Gson GSON = new Gson();
    private static Map<String, String> binds = new HashMap<>();

    public static void load() {
        if (FILE.exists()) {
            Type type = new TypeToken<Map<String,String>>(){}.getType();
            binds = ConfigUtil.readJson(FILE, type, new HashMap<>());
        }
    }

    public static void save () {
        ConfigUtil.writeJson(FILE, binds);
    }

    public static void addBind (String key, String command) {
        binds.put(key.toUpperCase(), command);
        save();
    }

    public static boolean removeBind(String key) {
        if (binds.remove(key.toUpperCase()) != null) {
            save();
            return true;
        }
        return false;
    }

    public static Map<String, String> getBinds() {
        return binds;
    }

    public static String getCommand(String key) {
        return binds.get(key.toUpperCase());
    }
}
