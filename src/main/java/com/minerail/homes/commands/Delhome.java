package com.minerail.homes.commands;

import com.minerail.homes.utils.ConfigUtils;
import java.io.IOException;

import com.minerail.homes.utils.DataUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Delhome implements CommandExecutor {

    public Delhome() {}
    private final DataUtils dataUtils = new DataUtils();
    private final ConfigUtils configUtils = new ConfigUtils();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 1) {
                if (dataUtils.checkIfHomeExists(p, args[0])) {
                    dataUtils.deletePlayerHomeFromFile(p, args[0]);
                    p.sendMessage(configUtils.builder(configUtils.homeSuccessfullyDeleted, null, null, null, null));
                    try {
                        dataUtils.saveData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                }
                p.sendMessage(configUtils.builder(configUtils.homeNotExists, null, null, null, null));
                return true;
            }
        }
        return false;
    }
}
