package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import java.io.IOException;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Delhome implements CommandExecutor {
    private Sethome sethome;

    private DataUtils fileManager;

    private ConfigUtils configUtils;

    public Delhome(Homes homes) {}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.sethome = new Sethome(this);
        this.configUtils = new ConfigUtils(this);
        this.fileManager = new DataUtils(this);
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 1) {
                if (fileManager.checkIfHomeExists(p, args[0])) {
                    fileManager.deletePlayerHomeFromFile(p, args[0]);
                    p.sendMessage(configUtils.builder(ConfigUtils.homeSuccessfullyDeleted, null, null, null, null));
                    try {
                        this.fileManager.saveData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                }
                p.sendMessage(configUtils.builder(ConfigUtils.homeNotExists, null, null, null, null));
                return true;
            }
        }
        return false;
    }
}
