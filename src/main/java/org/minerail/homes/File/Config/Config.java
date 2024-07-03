package org.minerail.homes.File.Config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.minerail.homes.Homes;

import java.io.File;

public class Config {
    private static YamlConfiguration config;

    public static void create() {
        Homes.get().saveResource("config.yml", false);
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(new File(Homes.get().getDataFolder().toPath() + "/config.yml"));
    }

    public static String getString(ConfigKeys path) {
        return config.getString(path.getPath());
    }

    public static int getInt(ConfigKeys path) {
        return config.getInt(path.getPath());
    }

    public static double getDouble(ConfigKeys path) {
        return config.getDouble(path.getPath());
    }

    public static boolean getBoolean(ConfigKeys path) {
        return config.getBoolean(path.getPath());
    }
}
