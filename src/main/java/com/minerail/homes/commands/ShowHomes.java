package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowHomes implements CommandExecutor {
    private DataUtils fileManager;

    private ConfigUtils configUtils;

    private PaginatedGui homesGui;

    public ShowHomes(Homes homes) {}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.configUtils = new ConfigUtils(this);
        this.fileManager = new DataUtils(this);
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (!this.fileManager.getPlayerHomesList(p).isEmpty()) {
                p.sendMessage(configUtils.builder(ConfigUtils.homesList, null, null, null, String.join(", ", fileManager.getPlayerHomesList(p))));
                return true;
            }
            p.sendMessage(configUtils.builder(ConfigUtils.homesListIsEmpty, null, null, null, null));
            return true;
        }
        return false;
    }
}
