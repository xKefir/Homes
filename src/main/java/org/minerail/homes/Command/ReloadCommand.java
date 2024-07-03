package org.minerail.homes.Command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.minerail.homes.File.Message.MessageKey;
import org.minerail.homes.File.Message.MessageProvider;
import org.minerail.homes.Homes;


public class ReloadCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("homes.admin.reload")) {
                try {
                    Homes.reloadAll();
                    player.sendMessage(MessageProvider.get(MessageKey.RELOAD_SUCCESS,
                                    Placeholder.component(
                                            "prefix", MessageProvider.get(MessageKey.PREFIX)
                                    )
                            )
                    );
                    return true;
                } catch (Exception e) {
                    player.sendMessage(MessageProvider.get(MessageKey.RELOAD_FAILED,
                                    Placeholder.component(
                                            "prefix", MessageProvider.get(MessageKey.PREFIX)
                                    )
                            )
                    );
                    return true;
                }
            } else {
                player.sendMessage(MessageProvider.get(MessageKey.MISSING_PERMISSION,
                                Placeholder.component(
                                        "prefix", MessageProvider.get(MessageKey.PREFIX)
                                )
                        )
                );
                return true;
            }
        }
        return false;
    }
}
