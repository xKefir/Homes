package org.minerail.homes.File.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.minerail.homes.Homes;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerData implements Listener {
    private static final Map<UUID, PlayerData> PLAYER_DATA_MAP = new HashMap<>();
    private final UUID player;
    private final Map<String, Location> homeMap = new HashMap<>();
    private final File playerDataFile;
    private final YamlConfiguration conf;


    private PlayerData(Player player) {
        this.player = player.getUniqueId();
        this.playerDataFile = new File("plugins/Homes/PlayerData", this.player + ".yml");
        this.conf = YamlConfiguration.loadConfiguration(playerDataFile);

        PLAYER_DATA_MAP.put(player.getUniqueId(), this);

        conf.createSection("playerName");
        conf.set("playerName", player.getName());
        ConfigurationSection homesSection = conf.getConfigurationSection("homes");
        if (homesSection != null) {
            homesSection.getKeys(false).forEach(key -> {
                ConfigurationSection section = homesSection.getConfigurationSection(key);
                if (section != null) {
                    homeMap.put(key, deserializeLocation(section));
                }
            });
        }
    }

    public static PlayerData get(Player player) {
        if (!PLAYER_DATA_MAP.containsKey(player.getUniqueId())) {
            return new PlayerData(player);
        }
        return PLAYER_DATA_MAP.get(player.getUniqueId());
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public static void remove(Player player) {
        PLAYER_DATA_MAP.remove(player.getUniqueId());
    }

    public void setHome(String name, Location location) {
        homeMap.put(name, location);
    }

    public boolean hasHome(String name) {
        return homeMap.containsKey(name);
    }

    public Location getHome(String name) {
        return homeMap.get(name);
    }

    public Set<String> getHomes() {
        Set<String> homeSet = homeMap.keySet();
        return new TreeSet<>(homeSet);
    }

    public void removeHome(String name) {
        conf.set("homes." + name, null);
        homeMap.remove(name);
    }

    public void save() {
        ConfigurationSection homesSection = conf.createSection("homes");
        homeMap.forEach((name, location) -> {
            ConfigurationSection section = homesSection.createSection(name);
            serializeLocation(section, location);
        });

        try {
            conf.save(playerDataFile);
        } catch (IOException e) {
            Homes.get().getLogger().warning(e.getMessage());
        }
    }

    public static void saveAll() {
        PLAYER_DATA_MAP.values().forEach(PlayerData::save);
    }

    private Location deserializeLocation(ConfigurationSection section) {
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");
        String worldName = section.getString("world");

        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    private void serializeLocation(ConfigurationSection section, Location location) {
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());
        section.set("world", location.getWorld().getName());
    }
}
