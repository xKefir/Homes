package org.minerail.homes.File.Message;

import org.bukkit.configuration.file.YamlConfiguration;
import org.minerail.homes.Homes;

import java.io.File;

public class MessageProviderLoader {

    private static YamlConfiguration messages;

    public static void reload() {
        File msg1 = new File(Homes.get().getDataFolder().toPath() + "/messages.yml");
        if (!msg1.exists()) {
            Homes.get().saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(msg1);

    }

    public static String getString(String path) {
        return messages.getString(path);
    }

}
