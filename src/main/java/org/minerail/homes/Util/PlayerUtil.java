package org.minerail.homes.Util;

import io.papermc.paper.math.FinePosition;
import io.papermc.paper.math.Position;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.minerail.homes.File.Config.Config;
import org.minerail.homes.File.Config.ConfigKeys;
import org.minerail.homes.File.Message.MessageKey;
import org.minerail.homes.File.Message.MessageProvider;
import org.minerail.homes.File.PlayerData.PlayerData;
import org.minerail.homes.Homes;


public class PlayerUtil {
    public static void teleportToHome(Player player, String args) {
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
    public static boolean checkLimit(Player player) {
        if (player.hasPermission("homes.limit.bypass") &&
                Config.getBoolean(ConfigKeys.LIMITS_BYPASS_FOR_ADMINS) &&
                Config.getBoolean(ConfigKeys.MODULE_LIMITS_IS_ENABLED)) {
            return true;
        } else {
            for (int i = 0; i < Config.getInt(ConfigKeys.LIMITS_MAX_REPETITIONS); i++) {
                if (player.hasPermission("homes.limit." + i)) {
                    int permLimit = i - 1;
                    if (PlayerData.get(player).getHomes().size() <= permLimit) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    public static void runDelayedTeleport(Player player, FinePosition finePosition, String args) {
        if (checkPermsission(player, "homes.delay.bypass") && Config.getBoolean(ConfigKeys.TELEPORT_BYPASS_ADMIN)) {
            teleportToHome(player, args);
        } else {
            player.sendMessage(MessageProvider.get(MessageKey.HOME_TELEPORT_DELAYED,
                            Placeholder.component("prefix", MessageProvider.get(MessageKey.PREFIX))
                    )
            );
            Homes.get().getServer().getScheduler().runTaskLater(Homes.get(), () -> {
                if (finePosition.equals(Position.fine(player.getLocation()))) {
                    PlayerUtil.teleportToHome(player, args);
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
    }
    private static boolean checkPermsission(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        }
        return false;
    }
}
