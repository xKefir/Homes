package com.minerail.homes.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DataUtils {
    private DataUtils dataUtils;
    public DataUtils() {}
    public void setDataUtils(DataUtils dataUtils) {
        this.dataUtils = dataUtils;
    }
    public Plugin plugin = Bukkit.getPluginManager().getPlugin("Homes");

    public YamlConfiguration config;

    private File file = new File(this.plugin.getDataFolder(), "config.yml");

    public YamlConfiguration data;

    private File dataFile = new File(this.plugin.getDataFolder(), "data.yml");




    public void setupConfigFiles() throws IOException, InvalidConfigurationException {
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
            config = YamlConfiguration.loadConfiguration(file);
            configUtils.buildTexts();
        } else {
            config = YamlConfiguration.loadConfiguration(file);
            configUtils.buildTexts();
        }
        if (!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
            data = YamlConfiguration.loadConfiguration(dataFile);
            plugin.getLogger().info("Successfully created and loaded data.yml file!");
        } else {
            data = YamlConfiguration.loadConfiguration(dataFile);
        }
    }

    public void savePlayerHomeToDataFile(Player player, String homeName, Location loc) {
        if (data.get(player.getName() + "." + homeName) == null) {
            Double x = Double.valueOf(loc.getX());
            double y = loc.getY();
            double z = loc.getZ();
            Float yaw = loc.getYaw();
            Float pitch = loc.getPitch();
            String world = loc.getWorld().getName();
            data.set(player.getName() + "." + homeName + ".x", x);
            data.set(player.getName() + "." + homeName + ".y", y);
            data.set(player.getName() + "." + homeName + ".z", z);
            data.set(player.getName() + "." + homeName + ".yaw", yaw);
            data.set(player.getName() + "." + homeName + ".pitch", pitch);
            data.set(player.getName() + "." + homeName + ".world", world);
            getPlayerHomesList(player);
        }
    }

    public void deletePlayerHomeFromFile(Player player, String homeName) {
        if (data.get(player.getName() + "." + homeName) != null) {
            data.set(player.getName() + "." + homeName, null);
            data.set(player.getName() + "." + homeName + ".x", null);
            data.set(player.getName() + "." + homeName + ".y", null);
            data.set(player.getName() + "." + homeName + ".z", null);
            data.set(player.getName() + "." + homeName + ".yaw", null);
            data.set(player.getName() + "." + homeName + ".pitch", null);
            data.set(player.getName() + "." + homeName + ".world", null);
            getPlayerHomesList(player);
        }
    }

    public Location getPlayerHome(Player player, String homeName) {
        if (data.getConfigurationSection(player.getName() + "." + homeName) != null) {
            if (Bukkit.getWorld(data.getString(player.getName() + "." + homeName + ".world")) != null)
                return new Location(
                        Bukkit.getWorld(data.getString(player.getName() + "." + homeName + ".world")), data
                        .getDouble(player.getName() + "." + homeName + ".x"), data
                        .getDouble(player.getName() + "." + homeName + ".y"), data
                        .getDouble(player.getName() + "." + homeName + ".z"),
                        data.getLong(player.getName() + "." + homeName + ".yaw"),
                        data.getLong(player.getName() + "." + homeName + ".pitch"));
            return null;
        }
        return null;
    }

    public List<String> getPlayerHomesList(Player p) {
        List<String> homes = new ArrayList<>();
        homes.clear();
        if (data.getConfigurationSection(p.getName()) != null)
            for (String home : data.getConfigurationSection(p.getName()).getKeys(false))
                homes.add(home);
        return homes;
    }

    public boolean checkIfHomeExists(Player p, String homeName) {
        if (data.getConfigurationSection(p.getName() + "." + homeName) != null)
            return true;
        return false;
    }

    public void saveData() throws IOException {
        data.save("plugins/Homes/data.yml");
    }
}
