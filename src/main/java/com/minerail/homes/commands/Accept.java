package com.minerail.homes.commands;

import com.minerail.homes.Homes;
import com.minerail.homes.dependencies.Worldguard;
import com.minerail.homes.utils.ConfigUtils;
import java.io.IOException;
import java.util.Map;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.minerail.homes.dependencies.Worldguard.plugin;

public class Accept implements CommandExecutor {
    private Sethome sethome;

    private Request request;

    private ConfigUtils configUtils;

    private static boolean falseOrTrue;

    public Accept(Homes homes) {}

    public Accept(Request request) {}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            this.sethome = new Sethome(this);
            this.request = new Request(this);
            this.configUtils = new ConfigUtils(this);
            if (args.length == 2) {
                if (plugin.getServer().getPlayer(args[0]) != null) {
                    Player arg = plugin.getServer().getPlayer(args[0]);
                    if (request.sendedRequests.containsValue(p))
                        if (request.requests.containsKey(p))
                            if (request.requests.containsValue(true) && !arg.equals(p))
                                if (args[1].equals("true")) {
                                    arg.sendMessage(configUtils.builder(ConfigUtils.acceptedToAsker, p, null, null, null));
                                    p.sendMessage(configUtils.builder(ConfigUtils.successToCommandSender, null, arg.getName(), null, null));
                                    falseOrTrue = true;
                                    if (Sethome.playerLocationMapForRequest.containsKey(arg)) {
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
                                    arg.sendMessage(configUtils.builder(ConfigUtils.noAgreementToAsker, p, null, null, null));
                                    p.sendMessage(configUtils.builder(ConfigUtils.noAgreementToCommandSender, null, arg.getName(), null, null));
                                    request.clearData(arg, p);
                                    falseOrTrue = false;
                                }
                }
            } else {
                p.sendMessage(configUtils.builder(ConfigUtils.missingArgumentsAccept, null, null, null, null));
                return true;
            }
        }
        return false;
    }

    public boolean getAnswer() {
        return falseOrTrue;
    }
}
