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
import org.minerail.homes.File.PlayerData.PlayerData;
import org.minerail.homes.Util.PlayerUtil;

public class SethomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length != 0 && args.length != 2) {
                if (!PlayerData.get(player).hasHome(args[0])) {
                    if (!PlayerUtil.get(player).checkLimit()) {
                        player.sendMessage(MessageProvider.get(MessageKey.SETHOME_LIMIT_WAS_REACHED,
                                        Placeholder.component("prefix", MessageProvider.get(MessageKey.PREFIX))
                                )
                        );
                        return true;
                    }
                    PlayerData.get(player).setHome(args[0], player.getLocation());
                    player.sendMessage(MessageProvider.get(MessageKey.SETHOME_SUCCESSFULLY_CREATED,
                                    Placeholder.component("input", Component.text(args[0])),
                                    Placeholder.component("prefix", MessageProvider.get(MessageKey.PREFIX))
                            )
                    );
                    return true;
                } else {
                    player.sendMessage(MessageProvider.get(MessageKey.SETHOME_HOME_ALREADY_EXISTS,
                                    Placeholder.component("prefix", MessageProvider.get(MessageKey.PREFIX))
                            )
                    );
                    return true;
                }
            } else {
                player.sendMessage(MessageProvider.get(MessageKey.COMMAND_MISSING_ARGUMENT,
                                Placeholder.component("prefix", MessageProvider.get(MessageKey.PREFIX))
                        )
                );
                return true;
            }
        }
        return false;
    }
}
