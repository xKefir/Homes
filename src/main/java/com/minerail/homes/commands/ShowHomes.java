package com.minerail.homes.commands;


import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowHomes implements CommandExecutor {

    public ShowHomes() {}
    private final DataUtils dataUtils = new DataUtils();
    private final ConfigUtils configUtils = new ConfigUtils();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (!dataUtils.getPlayerHomesList(p).isEmpty()) {
                p.sendMessage(configUtils.builder(configUtils.homesList, null, null, null, String.join(", ", dataUtils.getPlayerHomesList(p))));
                return true;
            }
            p.sendMessage(configUtils.builder(configUtils.homesListIsEmpty, null, null, null, null));
            return true;
        }
        return false;
    }
}
