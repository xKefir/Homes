package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.dependencies.Worldguard;
import com.minerail.homes.utils.ConfigUtils;
import java.io.IOException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCmd implements CommandExecutor {
    private ConfigUtils configUtils;

    public ReloadCmd(Homes homes) {}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.configUtils = new ConfigUtils(this);
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("homes.admin.reload")) {
                try {
                    this.configUtils.reloadConfig();
                    p.sendMessage(configUtils.builder(ConfigUtils.rlMessage, null, null, null, null));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            } else {
                p.sendMessage(configUtils.builder(ConfigUtils.missingPermission, null, null, null, null));
            }
        } else {
            try {
                this.configUtils.reloadConfig();
                Worldguard.plugin.getLogger().info("Plugin successfully reloaded!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
