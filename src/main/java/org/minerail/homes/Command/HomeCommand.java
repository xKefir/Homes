package org.minerail.homes.Command;

import io.papermc.paper.math.FinePosition;
import io.papermc.paper.math.Position;
import net.kyori.adventure.text.Component;
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
import org.minerail.homes.File.PlayerData;
import org.minerail.homes.Homes;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length != 0) {
                if (PlayerData.get(player).getHome(args[0]) != null) {
                    if (Config.getBoolean(ConfigKeys.TELEPORT_DELAY_IS_ENABLED)) {
                        FinePosition finePosition = Position.fine(player.getLocation());
                        player.sendMessage(MessageProvider.get(MessageKey.HOME_TELEPORT_DELAYED,
                                Placeholder.component("prefix", MessageProvider.get(MessageKey.PREFIX))
                                )
                        );
                        runTask(finePosition, player, args[0]);
                        return true;
                    } else {
                        teleport(player, args[0]);
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

    private void runTask(FinePosition finePosition, Player player, String args) {
        Homes.get().getServer().getScheduler().runTaskLater(Homes.get(), () -> {
            if (finePosition.equals(Position.fine(player.getLocation()))) {
                teleport(player, args);
            } else {
                player.sendMessage(MessageProvider.get(MessageKey.HOME_TELEPORT_FAILED_MOVED,
                            Placeholder.component(
                                "prefix", MessageProvider.get(MessageKey.PREFIX)
                            )
                        )
                );
            }
        }, Config.getInt(ConfigKeys.TELEPORT_DELAY_TIME_TO_TELEPORT));
    }

    private void teleport(Player player, String args) {
        player.teleport(PlayerData.get(player).getHome(args));
        player.sendMessage(MessageProvider.get(MessageKey.HOME_TELEPORT_SUCCESS,
                    Placeholder.component(
                            "input", Component.text(args)
                    ),
                    Placeholder.component(
                            "prefix", MessageProvider.get(MessageKey.PREFIX)
                    )
                )
        );
    }
}
