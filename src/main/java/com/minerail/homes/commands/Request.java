package com.minerail.homes.commands;

import com.minerail.homes.dependency.WorldGuardHook;
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

public class Request implements CommandExecutor {
    public Request() {}
    private final DataUtils dataUtils = new DataUtils();
    private final ConfigUtils configUtils = new ConfigUtils();
    private final WorldGuardHook worldGuardHook = new WorldGuardHook();
    private final Sethome sethome = new Sethome();
    public Map<Player, Map> requestsDatabase = new LinkedHashMap<>();

    public Map<Player, Boolean> requests = new LinkedHashMap<>();

    public Map<Player, Player> sendedRequests = new LinkedHashMap<>();

    public Map<Player, Boolean> requestsSent = new LinkedHashMap<>();

    public Map<Player, Location> locOfSendedRequest = new LinkedHashMap<>();


    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 1) {
                if (worldGuardHook.plugin.getServer().getPlayer(args[0]) != null) {
                    Player arg = worldGuardHook.plugin.getServer().getPlayer(args[0]);
                    if (!requestsSent.containsKey(p)) {
                        sendedRequests.put(p, arg);
                        requests.put(arg, true);
                        requestsDatabase.put(p, requests);
                        requestsSent.put(p, true);
                        if (arg.isOnline()) {
                            if (!arg.equals(p)) {
                                if (dataUtils.config.getBoolean("Settings.PluginSettings.Commands.Request.auto-cancel.enabled")) {
                                    if (dataUtils.config.getInt("Settings.PluginSettings.Commands.Request.auto-cancel.time-ticks") <= 6000) {
                                        worldGuardHook.plugin.getServer().getScheduler().runTaskLater(worldGuardHook.plugin, task -> {
                                            if (requests.containsKey(arg)) {
                                                clearData(p, arg);
                                                p.sendMessage(configUtils.builder(configUtils.autoCancelSender, null, null, null, null));
                                                arg.sendMessage(configUtils.builder(configUtils.autoCancelOwner, p, null, null, null));
                                            }
                                        }, dataUtils.config.getInt("Settings.PluginSettings.Commands.Request.auto-cancel.time-ticks"));
                                    }
                                }
                                if (!sethome.playerBooleanMap.containsKey(p)) {
                                    if (!sethome.playerBooleanMap.containsValue(false)) {
                                        locOfSendedRequest.put(p, p.getLocation());
                                        arg.sendMessage(configUtils.builder(configUtils.toCuboidOwner, p, null, null, null));
                                        p.sendMessage(configUtils.builder(configUtils.requestSended, null, null, null, null));
                                        return true;
                                    }
                                } else {
                                    arg.sendMessage(configUtils.builder(configUtils.toCuboidOwner, p, null, null, null));
                                    p.sendMessage(configUtils.builder(configUtils.requestSended, null, null, null, null));
                                    return true;
                                }
                            } else {
                                clearData(p, arg);
                                p.sendMessage(configUtils.builder(configUtils.requestSendedToSender, null, null, null, null));
                                return true;
                            }
                        } else {
                            clearData(p, arg);
                            p.sendMessage(configUtils.builder(configUtils.errorIfOwnerOffline, p, null, null, null));
                            return true;
                        }
                    } else {
                        p.sendMessage(configUtils.builder(configUtils.requestAlreadySent, null, arg.getName(), null, null));
                        return true;
                    }
                } else {
                    p.sendMessage(configUtils.builder(configUtils.errorIfOwnerOffline, p, null, null, null));
                    return true;
                }
            } else {
                p.sendMessage(configUtils.builder(configUtils.missingArgsRequest, null, null, null, null));
                return true;
            }
        }
        return false;
    }

    public void clearData(Player p, Player arg) {
        locOfSendedRequest.remove(p);
        sethome.playerBooleanMap.remove(p);
        sethome.playerLocationMapForRequest.remove(p);
        sendedRequests.remove(p);
        requests.remove(arg);
        requestsDatabase.remove(p);
        requestsSent.put(p, true);
    }
}
