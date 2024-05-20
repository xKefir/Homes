package com.minerail.homes.commands;

import com.minerail.homes.dependency.WorldGuardHook;
import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Sethome implements CommandExecutor {
    public Sethome() {}
    private final ConfigUtils configUtils = new ConfigUtils();
    private final DataUtils dataUtils = new DataUtils();
    private final WorldGuardHook worldGuardHook = new WorldGuardHook();
    private final Request request = new Request();

    public Map<String, Location> homes = new LinkedHashMap<>();

    public Map<Player, Boolean> playerBooleanMap = new LinkedHashMap<>();

    public Map<Player, String> homesNames = new LinkedHashMap<>();

    public Map<Player, Location> playerLocationMapForRequest = new LinkedHashMap<>();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length != 0 && args.length <= 2) {
                if (!dataUtils.checkIfHomeExists(player, args[0])) {
                    if (configUtils.wgEnabled()) {
                        if (configUtils.limitsEnabled()) {
                            if (worldGuardHook.isPlayerInRegion(player)) {
                                try {
                                    if (dataUtils.getPlayerHomesList(player).size() <= this.configUtils.getPlayerLimits(player)) {
                                        setHome(player, args, null, null);
                                        player.sendMessage(configUtils.builder(configUtils.create_home_success, null, null, null, null));
                                        return true;
                                    }
                                    player.sendMessage(configUtils.builder(configUtils.limitReachedWhileCreatingHome, null, null, null, null));
                                    return true;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                
                            }
                            if (!request.requestsSent.containsKey(player)) {
                                playerBooleanMap.put(player, worldGuardHook.isPlayerInRegion(player));
                                playerLocationMapForRequest.put(player, player.getLocation());
                                homesNames.put(player, args[0]);
                                player.sendMessage(configUtils.builder(configUtils.errorIfPlayerIsNot, null, null, null, null));
                                return true;
                            }
                            for (Map.Entry<Player, Player> entry0 : request.sendedRequests.entrySet()) {
                                if (entry0.getKey().equals(player)) {
                                    Player argument = entry0.getValue();
                                    player.sendMessage(configUtils.builder(configUtils.requestAlreadySent, null, argument.getName(), null, null));
                                    return true;
                                }
                            }
                        } else {
                            if (this.worldGuardHook.isPlayerInRegion(player)) {
                                try {
                                    setHome(player, args, null, null);
                                    player.sendMessage(configUtils.builder(configUtils.create_home_success, null, null, null, null));
                                    return true;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                player.sendMessage(configUtils.builder(configUtils.errorIfPlayerIsNot, null, null, null, null));
                                return true;
                            }
                        }
                    } else {
                        if (configUtils.limitsEnabled()) {
                            try {
                                if (dataUtils.getPlayerHomesList(player).size() <= configUtils.getPlayerLimits(player)) {
                                    setHome(player, args, null, null);
                                    player.sendMessage(configUtils.builder(configUtils.create_home_success, null, null, null, null));
                                    return true;
                                }
                                player.sendMessage(configUtils.builder(configUtils.limitReachedWhileCreatingHome, null, null, null, null));
                                return true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                setHome(player, args, null, null);
                                player.sendMessage(configUtils.builder(configUtils.create_home_success, null, null, null, null));
                                return true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else {
                    player.sendMessage(configUtils.builder(configUtils.existingHome, null, null, null, null));
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public void setHome(Player player, String[] args, String arg, Location loc) throws IOException {
        if (args != null) {
            if (args.length == 1 && dataUtils.data.getLocation(player.getName() + "." + args[0]) == null) {
                save(player, args[0], loc);
            }
        } else if (arg != null && loc != null && dataUtils.data.getLocation(player.getName() + "." + arg) == null) {
            save(player, arg, loc);
        }
    }
    public void save(Player player, String arg, Location loc) throws IOException {
        dataUtils.savePlayerHomeToDataFile(player, arg, loc);
        dataUtils.saveData();
        dataUtils.getPlayerHomesList(player);
    }
}
