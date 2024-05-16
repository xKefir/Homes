package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import io.papermc.paper.math.FinePosition;
import io.papermc.paper.math.Position;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Home implements TabCompleter, CommandExecutor {
    private Sethome sethome;

    private DataUtils fileManager;

    private ConfigUtils configUtils;

    private Plugin plugin = Bukkit.getPluginManager().getPlugin("Homes");

    List<String> arguments;

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.sethome = new Sethome(this);
        this.fileManager = new DataUtils(this);
        this.configUtils = new ConfigUtils(this);
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 1) {
                String arg = args[0];
                if (fileManager.getPlayerHome(player, args[0]) != null) {
                    Location loc = fileManager.getPlayerHome(player, args[0]);
                    FinePosition finePosition = Position.fine(player.getLocation());
                    if (loc.getWorld().getName() != null)
                        if (DataUtils.config.getInt("Settings.PluginSettings.Teleport.delay-ticks") <= 600) {
                            if (DataUtils.config.getBoolean("Settings.PluginSettings.Teleport.enabled")) {
                                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                                    if (finePosition.equals(Position.fine(player.getLocation()))) {
                                        player.teleport(loc);
                                        player.sendMessage(configUtils.builder(ConfigUtils.teleportedPlayer, null, null, arg, null));
                                    } else {
                                        player.sendMessage(configUtils.builder(ConfigUtils.playerMoved, null, null, null, null));
                                    }
                                }, DataUtils.config.getInt("Settings.PluginSettings.Teleport.delay-ticks"));
                            } else {
                                player.teleport(loc);
                                player.sendMessage(configUtils.builder(ConfigUtils.teleportedPlayer, null, null, arg, null));
                            }
                            player.sendMessage(configUtils.builder(ConfigUtils.tpdelay, null, null, null, null));
                            return true;
                        }
                    player.sendMessage(configUtils.builder(ConfigUtils.homeNotExistsWhileTp, null, null, null, null));
                    return true;
                }
                player.sendMessage(configUtils.builder(ConfigUtils.homeNotExistsWhileTp, null, null, null, null));
                return true;
            }
        }
        return false;
    }

    public Home(Homes homes) {
        this.arguments = new ArrayList<>();
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.arguments.clear();
        this.fileManager = new DataUtils(this);
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (arguments.isEmpty()) {
                List<String> aarr = fileManager.getPlayerHomesList(p);
                for (String home : aarr) {
                    arguments.add(home);
                }
            }
        }
        List<String> result = new ArrayList<>();
        result.clear();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }
        return null;
    }
}
