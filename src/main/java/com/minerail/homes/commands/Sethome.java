package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.dependencies.Worldguard;
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
    private DataUtils fileManager;

    private ConfigUtils configUtils;

    private Worldguard worldGuardIntegration;

    private Request request;

    public Sethome(Homes homes) {}

    public Sethome(Home home) {}

    public static Map<String, Location> homes = new LinkedHashMap<>();

    public static Map<Player, Boolean> playerBooleanMap = new LinkedHashMap<>();

    public static Map<Player, String> homesNames = new LinkedHashMap<>();

    public static Map<Player, Location> playerLocationMapForRequest = new LinkedHashMap<>();

    public Sethome(Delhome delhome) {}

    public Sethome(Request request) {}


    public Sethome(Accept accept) {}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            this.request = new Request(this);
            this.fileManager = new DataUtils(this);
            this.worldGuardIntegration = new Worldguard(this);
            this.configUtils = new ConfigUtils(this);
            if (args.length != 0 && args.length <= 2) {
                if (!this.fileManager.checkIfHomeExists(player, args[0])) {
                    if (this.configUtils.wgEnabled()) {
                        if (this.configUtils.limitsEnabled()) {
                            if (this.worldGuardIntegration.isPlayerInRegion(player))
                                try {
                                    if (this.fileManager.getPlayerHomesList(player).size() <= this.configUtils.getPlayerLimits(player)) {
                                        setHome(player, args, null, null);
                                        player.sendMessage(configUtils.builder(ConfigUtils.create_home_success, null, null, null, null));
                                        return true;
                                    }
                                    player.sendMessage(configUtils.builder(ConfigUtils.limitReachedWhileCreatingHome, null, null, null, null));
                                    return true;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            if (!Request.requestsSent.containsKey(player)) {
                                playerBooleanMap.put(player, worldGuardIntegration.isPlayerInRegion(player));
                                playerLocationMapForRequest.put(player, player.getLocation());
                                homesNames.put(player, args[0]);
                                player.sendMessage(configUtils.builder(ConfigUtils.errorIfPlayerIsNot, null, null, null, null));
                                return true;
                            }
                            for (Map.Entry<Player, Player> entry0 : Request.sendedRequests.entrySet()) {
                                if (entry0.getKey().equals(player)) {
                                    Player argument = entry0.getValue();
                                    player.sendMessage(configUtils.builder(ConfigUtils.requestAlreadySent, null, argument.getName(), null, null));
                                    return true;
                                }
                            }
                        } else {
                            if (this.worldGuardIntegration.isPlayerInRegion(player))
                                try {
                                    setHome(player, args, null, null);
                                    player.sendMessage(configUtils.builder(ConfigUtils.create_home_success, null, null, null, null));
                                    return true;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            player.sendMessage(configUtils.builder(ConfigUtils.errorIfPlayerIsNot, null, null, null, null));
                            return true;
                        }
                    } else {
                        if (this.configUtils.limitsEnabled())
                            try {
                                if (this.fileManager.getPlayerHomesList(player).size() <= this.configUtils.getPlayerLimits(player)) {
                                    setHome(player, args, null, null);
                                    player.sendMessage(configUtils.builder(ConfigUtils.create_home_success, null, null, null, null));
                                    return true;
                                }
                                player.sendMessage(configUtils.builder(ConfigUtils.limitReachedWhileCreatingHome, null, null, null, null));
                                return true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        try {
                            setHome(player, args, null, null);
                            player.sendMessage(configUtils.builder(ConfigUtils.create_home_success, null, null, null, null));
                            return true;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    player.sendMessage(configUtils.builder(ConfigUtils.existingHome, null, null, null, null));
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public void setHome(Player player, String[] args, String arg, Location loc) throws IOException {
        this.fileManager = new DataUtils(this);
        if (args != null) {
            if (args.length == 1 &&
                    DataUtils.data.getLocation(player.getName() + "." + args[0]) == null) {
                this.fileManager.savePlayerHomeToDataFile(player, args[0], player.getLocation());
                this.fileManager.saveData();
                this.fileManager.getPlayerHomesList(player);
            }
        } else if (arg != null && loc != null &&
                DataUtils.data.getLocation(player.getName() + "." + arg) == null) {
            this.fileManager.savePlayerHomeToDataFile(player, arg, loc);
            this.fileManager.saveData();
            this.fileManager.getPlayerHomesList(player);
        }
    }
}
