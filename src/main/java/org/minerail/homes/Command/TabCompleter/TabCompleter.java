package org.minerail.homes.Command.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.minerail.homes.File.PlayerData.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length == 1) {
            Player player = (Player) sender;
            Set<String> homesSet = PlayerData.get(player).getHomes();
            List<String> homesList = new ArrayList<>(homesSet);
            return homesList;
        }
        return null;
    }
}
