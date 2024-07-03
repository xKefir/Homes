package org.minerail.homes.Command;

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
import org.minerail.homes.File.PlayerData.PlayerData;
import org.minerail.homes.Inventory.HomesInventory;

public class HomesCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!PlayerData.get(player).getHomes().isEmpty()) {
                if (Config.getBoolean(ConfigKeys.MODULE_GUI_IS_ENABLED)) {
                    HomesInventory.get(player).open();
                    player.sendMessage(MessageProvider.get(MessageKey.HOMES_GUI_OPENED,
                            Placeholder.component(
                                    "prefix", MessageProvider.get(MessageKey.PREFIX)
                            )
                        )
                    );
                    return true;
                }
                player.sendMessage(MessageProvider.get(MessageKey.HOMES_SHOW,
                                Placeholder.component(
                                        "homes", Component.text(
                                                String.join(", ", PlayerData.get(player).getHomes()
                                                )
                                        )
                                ),
                                Placeholder.component(
                                        "prefix", MessageProvider.get(MessageKey.PREFIX)
                                )
                        )
                );
                return true;
            } else {
                player.sendMessage(MessageProvider.get(MessageKey.HOMES_EMPTY,
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
