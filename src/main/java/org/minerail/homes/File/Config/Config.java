package org.minerail.homes.File.Config;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.minerail.homes.Homes;

import java.io.File;

public class Config {
    private static YamlConfiguration config;

    public static void reload() {
        File config1 = new File(Homes.get().getDataFolder().toPath() + "/config.yml");
        if (!config1.exists()) {
            Homes.get().saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(config1);
    }

    public static String getString(ConfigKeys path) {
        return config.getString(path.getPath());
    }
    public static Material getMaterial(ConfigKeys path) {
        return Material.valueOf(config.getString(path.getPath()));
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
