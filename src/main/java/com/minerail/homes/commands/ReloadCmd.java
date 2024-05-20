package com.minerail.homes.commands;

import com.minerail.homes.dependency.WorldGuardHook;
import com.minerail.homes.utils.ConfigUtils;
import java.io.IOException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class ReloadCmd implements CommandExecutor {
    public ReloadCmd() {}
    private final ConfigUtils configUtils = new ConfigUtils();
    private final WorldGuardHook worldGuardHook = new WorldGuardHook();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("homes.admin.reload")) {
                try {
                    configUtils.reloadConfig();
                    p.sendMessage(configUtils.builder(configUtils.rlMessage, null, null, null, null));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            } else {
                p.sendMessage(configUtils.builder(configUtils.missingPermission, null, null, null, null));
            }
        } else {
            try {
                configUtils.reloadConfig();
                worldGuardHook.plugin.getLogger().info("Plugin successfully reloaded!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
