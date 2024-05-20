package com.minerail.homes.commands;

import com.minerail.homes.dependency.WorldGuardHook;
import com.minerail.homes.utils.ConfigUtils;
import java.io.IOException;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class Accept implements CommandExecutor {
    public Accept() {}
    private final Sethome sethome = new Sethome();
    private final Request request = new Request();
    private final ConfigUtils configUtils = new ConfigUtils();
    private final WorldGuardHook worldGuardHook = new WorldGuardHook();
    private static boolean falseOrTrue;


    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 2) {
                if (worldGuardHook.plugin.getServer().getPlayer(args[0]) != null) {
                    Player arg = worldGuardHook.plugin.getServer().getPlayer(args[0]);
                    if (request.sendedRequests.containsValue(p))
                        if (request.requests.containsKey(p))
                            if (request.requests.containsValue(true) && !arg.equals(p))
                                if (args[1].equals("true")) {
                                    arg.sendMessage(configUtils.builder(configUtils.acceptedToAsker, p, null, null, null));
                                    p.sendMessage(configUtils.builder(configUtils.successToCommandSender, null, arg.getName(), null, null));
                                    falseOrTrue = true;
                                    if (sethome.playerLocationMapForRequest.containsKey(arg)) {
                                        for (Map.Entry<Player, String> entry : sethome.homesNames.entrySet()) {
                                            String home = entry.getValue();
                                            try {
                                                sethome.setHome(arg, null, home, null);
                                                sethome.homesNames.remove(arg);
                                                request.clearData(arg, p);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    } else if (request.locOfSendedRequest.containsKey(arg)) {
                                        for (Map.Entry<Player, Location> entry : request.locOfSendedRequest.entrySet()) {
                                            if (entry.getKey().equals(arg)) {
                                                Location loc = entry.getValue();
                                                try {
                                                    this.sethome.setHome(arg, null, p.getName() + "-fromRequest", loc);
                                                    this.request.clearData(arg, p);
                                                } catch (IOException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }
                                    }
                                } else if (args[1].equals("false")) {
                                    arg.sendMessage(configUtils.builder(configUtils.noAgreementToAsker, p, null, null, null));
                                    p.sendMessage(configUtils.builder(configUtils.noAgreementToCommandSender, null, arg.getName(), null, null));
                                    request.clearData(arg, p);
                                    falseOrTrue = false;
                                }
                }
            } else {
                p.sendMessage(configUtils.builder(configUtils.missingArgumentsAccept, null, null, null, null));
                return true;
            }
        }
        return false;
    }

    public boolean getAnswer() {
        return falseOrTrue;
    }
}
