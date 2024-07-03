package org.minerail.homes.File.Message;

import org.bukkit.configuration.file.YamlConfiguration;
import org.minerail.homes.Homes;

import java.io.File;

public class MessageProviderLoader {

    private static YamlConfiguration messages;

    public static void create() {
        Homes.get().saveResource("messages.yml", false);
    }

    public static void reload() {
        messages = YamlConfiguration.loadConfiguration(new File(Homes.get().getDataFolder().toPath() + "/messages.yml"));
    }

    public static String getString(String path) {
        return messages.getString(path);
    }

}
