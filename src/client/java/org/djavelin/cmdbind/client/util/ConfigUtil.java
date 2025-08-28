package org.djavelin.cmdbind.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;

public class ConfigUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T readJson(File file, Type type, T def) {
        try (Reader reader = new FileReader(file)) {
            return GSON.fromJson(reader, type);
        } catch (Exception e) {
            return def;
        }
    }

    public static void writeJson(File file, Object obj) {
        file.getParentFile().mkdir();
        try (Writer writer = new FileWriter(file)) {
            GSON.toJson(obj, writer);
        } catch (Exception ignr) {}
    }
}
