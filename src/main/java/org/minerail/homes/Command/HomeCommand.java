package org.minerail.homes.Command;

import io.papermc.paper.math.FinePosition;
import io.papermc.paper.math.Position;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.minerail.homes.File.Config.Config;
import org.minerail.homes.File.Config.ConfigKeys;
import org.minerail.homes.File.Message.MessageKey;
import org.minerail.homes.File.Message.MessageProvider;
import org.minerail.homes.File.PlayerData.PlayerData;
import org.minerail.homes.Util.PlayerUtil;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length != 0 && args.length != 2) {
                if (PlayerData.get(player).getHome(args[0]) != null) {
                    if (Config.getBoolean(ConfigKeys.TELEPORT_DELAY_IS_ENABLED)) {
                        FinePosition finePosition = Position.fine(player.getLocation());
                        PlayerUtil.get(player).runDelayedTeleport(finePosition, args[0]);
                        return true;
                    } else {
                        PlayerUtil.get(player).teleportToHome(args[0]);
                        return true;
                    }
                } else {
                    player.sendMessage(MessageProvider.get(MessageKey.HOME_TELEPORT_FAILED_INVALID_HOME,
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
