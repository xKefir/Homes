package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.minerail.homes.dependencies.Worldguard.plugin;

public class Request implements CommandExecutor {
    private Sethome sethome;

    private ConfigUtils configUtils;


    private Accept accept;

    public Request(Sethome setHome) {}

    public Request(Homes homes) {}

    public static Map<Player, Map> requestsDatabase = new LinkedHashMap<>();

    public static Map<Player, Boolean> requests = new LinkedHashMap<>();

    public static Map<Player, Player> sendedRequests = new LinkedHashMap<>();

    public static Map<Player, Boolean> requestsSent = new LinkedHashMap<>();

    public static Map<Player, Location> locOfSendedRequest = new LinkedHashMap<>();

    public Request(Accept accept) {}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            this.sethome = new Sethome(this);
            this.configUtils = new ConfigUtils(this);
            this.accept = new Accept(this);
            if (args.length == 1) {
                if (plugin.getServer().getPlayer(args[0]) != null) {
                    Player arg = plugin.getServer().getPlayer(args[0]);
                    if (!requestsSent.containsKey(p)) {
                        sendedRequests.put(p, arg);
                        requests.put(arg, Boolean.valueOf(true));
                        requestsDatabase.put(p, requests);
                        requestsSent.put(p, Boolean.valueOf(true));
                        if (arg.isOnline()) {
                            if (!arg.equals(p)) {
                                if (DataUtils.config.getBoolean("Settings.PluginSettings.Commands.Request.auto-cancel.enabled"))
                                    if (DataUtils.config
                                            .getInt("Settings.PluginSettings.Commands.Request.auto-cancel.time-ticks") <= 6000)
                                        plugin.getServer().getScheduler().runTaskLater(plugin, task -> {
                                            if (requests.containsKey(arg)) {
                                                clearData(p, arg);
                                                p.sendMessage(configUtils.builder(ConfigUtils.autoCancelSender, null, null, null, null));
                                                arg.sendMessage(configUtils.builder(ConfigUtils.autoCancelOwner, p, null, null, null));
                                            }
                                        }, DataUtils.config.getInt("Settings.PluginSettings.Commands.Request.auto-cancel.time-ticks"));
                                if (!Sethome.playerBooleanMap.containsKey(p)) {
                                    if (!Sethome.playerBooleanMap.containsValue(Boolean.valueOf(false))) {
                                        locOfSendedRequest.put(p, p.getLocation());
                                        arg.sendMessage(configUtils.builder(ConfigUtils.toCuboidOwner, p, null, null, null));
                                        p.sendMessage(configUtils.builder(ConfigUtils.requestSended, null, null, null, null));
                                        return true;
                                    }
                                } else {
                                    arg.sendMessage(configUtils.builder(ConfigUtils.toCuboidOwner, p, null, null, null));
                                    p.sendMessage(configUtils.builder(ConfigUtils.requestSended, null, null, null, null));
                                    return true;
                                }
                            } else {
                                clearData(p, arg);
                                p.sendMessage(configUtils.builder(ConfigUtils.requestSendedToSender, null, null, null, null));
                                return true;
                            }
                        } else {
                            clearData(p, arg);
                            p.sendMessage(configUtils.builder(ConfigUtils.errorIfOwnerOffline, p, null, null, null));
                            return true;
                        }
                    } else {
                        p.sendMessage(configUtils.builder(ConfigUtils.requestAlreadySent, null, arg.getName(), null, null));
                        return true;
                    }
                } else {
                    p.sendMessage(configUtils.builder(ConfigUtils.errorIfOwnerOffline, p, null, null, null));
                    return true;
                }
            } else {
                p.sendMessage(configUtils.builder(ConfigUtils.missingArgsRequest, null, null, null, null));
                return true;
            }
        }
        return false;
    }

    public void clearData(Player p, Player arg) {
        this.sethome = new Sethome(this);
        locOfSendedRequest.remove(p);
        sethome.playerBooleanMap.remove(p);
        sethome.playerLocationMapForRequest.remove(p);
        sendedRequests.remove(p);
        requests.remove(arg);
        requestsDatabase.remove(p);
        requestsSent.put(p, true);
    }
}
