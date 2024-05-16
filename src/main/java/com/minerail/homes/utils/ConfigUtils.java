package com.minerail.homes.utils;

import com.minerail.homes.commands.*;
import java.io.IOException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.minerail.homes.dependencies.Worldguard.plugin;

public class ConfigUtils {
    DataUtils fileManager = new DataUtils(this);

    public static String errorMessage;

    public static String missingPermission;

    public static String rlMessage;

    public static String create_home_success;

    public static String errorIfPlayerIsNot;

    public static String deniedToSethome;

    public static String limitReachedWhileCreatingHome;

    public static String existingHome;

    public static String homeSuccessfullyDeleted;

    public static String homeNotExists;

    public static String requestSendedToSender;

    public static String requestSended;

    public static String requestAlreadySent;

    public static String errorIfOwnerOffline;

    public static String toCuboidOwner;

    public static String autoCancelSender;

    public static String autoCancelOwner;

    public static String missingArgsRequest;

    public static String acceptedToAsker;

    public static String noAgreementToAsker;

    public static String successToCommandSender;

    public static String noAgreementToCommandSender;

    public static String missingArgumentsAccept;

    public static String tpdelay;

    public static String playerMoved;

    public static String teleportedPlayer;

    public static String homeNotExistsWhileTp;

    public static String homesList;

    public static String homesListIsEmpty;

    public ConfigUtils(ReloadCmd reloadCmd) {}

    public ConfigUtils(Sethome sethome) {}

    public ConfigUtils(DataUtils dataUtils) {}

    public ConfigUtils(Home home) {}

    public ConfigUtils(Delhome delhome) {}

    public ConfigUtils(ShowHomes showHomes) {}

    public ConfigUtils(Request request) {}

    public ConfigUtils(Accept accept) {}

    public void reloadConfig() throws IOException, InvalidConfigurationException {
        this.fileManager.setupConfigFiles();
    }

    public boolean wgEnabled() {
        Boolean wg =DataUtils.config.getBoolean("Settings.WorldGuard.enabled");
        if (wg) {
            return true;
        } else {
            return false;
        }
    }

    public boolean limitsEnabled() {
        Boolean limit = DataUtils.config.getBoolean("Settings.Limits.enabled");
        if (limit) {
            return true;
        } else {
            return false;
        }
    }

    public boolean homesGuiEnabled() {
        Boolean gui = DataUtils.config.getBoolean("Settings.Gui.enabled");
        if (gui) {
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public String getPrefix() {
        if (DataUtils.config.getString("Settings.Messages.prefix") != null) {
            return DataUtils.config.getString("Settings.Messages.prefix");
        } else {
            return "";
        }
    }

    public int getPlayerLimits(Player p) {
        int max = DataUtils.config.getInt("Settings.Limits.max");
        int limit = 0;
        if (limitsEnabled()) {
            if (!p.hasPermission("homes.limit.bypass")) {
                for (int i = 0; i < max; i++) {
                    if (p.hasPermission("homes.limit." + i)) {
                        limit = i - 1;
                        break;
                    }
                }
            } else {
                return max;
            }
        }
        return limit;
    }

    public void buildTexts() {
        errorMessage = getPrefix() + DataUtils.config.getString("Settings.Messages.errorMessage");
        missingPermission = getPrefix() + DataUtils.config.getString("Settings.Messages.MissingPermission");
        rlMessage = getPrefix() + DataUtils.config.getString("Settings.Messages.reloadMessage");
        create_home_success = getPrefix() + DataUtils.config.getString("Settings.Messages.createHome.success");
        errorIfPlayerIsNot = getPrefix() + DataUtils.config.getString("Settings.Messages.createHome.deniedToSetHomeIfPlayerIsNotMember");
        deniedToSethome = getPrefix() + DataUtils.config.getString("Settings.Messages.createHome.deniedToSetHome");
        limitReachedWhileCreatingHome = getPrefix() + DataUtils.config.getString("Settings.Messages.createHome.limitReached");
        existingHome = getPrefix() + DataUtils.config.getString("Settings.Messages.createHome.homeExists");
        homeSuccessfullyDeleted = getPrefix() + DataUtils.config.getString("Settings.Messages.deleteHome.success");
        homeNotExists = getPrefix() + DataUtils.config.getString("Settings.Messages.deleteHome.homeNotExist");
        requestSendedToSender = getPrefix() + DataUtils.config.getString("Settings.Messages.request.requestSentToSender");
        requestSended = getPrefix() + DataUtils.config.getString("Settings.Messages.request.requestSent");
        requestAlreadySent = getPrefix() + DataUtils.config.getString("Settings.Messages.request.requestAlreadySent");
        errorIfOwnerOffline = getPrefix() + DataUtils.config.getString("Settings.Messages.request.errorIfOwnerIsOffline");
        toCuboidOwner = getPrefix() + DataUtils.config.getString("Settings.Messages.request.toCuboidOwner");
        autoCancelSender = getPrefix() + DataUtils.config.getString("Settings.Messages.request.auto-cancel-to-sender");
        autoCancelOwner = getPrefix() + DataUtils.config.getString("Settings.Messages.request.auto-cancel-to-owner");
        missingArgsRequest = getPrefix() + DataUtils.config.getString("Settings.Messages.request.MissingArguments");
        acceptedToAsker = getPrefix() + DataUtils.config.getString("Settings.Messages.accept.acceptedToAsker");
        noAgreementToAsker = getPrefix() + DataUtils.config.getString("Settings.Messages.accept.noAgreementToAsker");
        successToCommandSender = getPrefix() + DataUtils.config.getString("Settings.Messages.accept.successToCommandSender");
        noAgreementToCommandSender = getPrefix() + DataUtils.config.getString("Settings.Messages.accept.noAgreementToCommandSender");
        missingArgumentsAccept = getPrefix() + DataUtils.config.getString("Settings.Messages.accept.MissingArguments");
        tpdelay = getPrefix() + DataUtils.config.getString("Settings.Messages.home.delay");
        playerMoved = getPrefix() + DataUtils.config.getString("Settings.Messages.home.player-moved");
        teleportedPlayer = getPrefix() + DataUtils.config.getString("Settings.Messages.home.tpMessage");
        homeNotExistsWhileTp = getPrefix() + DataUtils.config.getString("Settings.Messages.home.homeNotExist");
        homesList = getPrefix() + DataUtils.config.getString("Settings.Messages.homes.show-homes");
        homesListIsEmpty = getPrefix() + DataUtils.config.getString("Settings.Messages.homes.empty");
        plugin.getLogger().info("Messages successfully loaded");
    }

    public ComponentBuilder builder(String s, Player sender, String arg, String argument, String list) {
        if (argument != null) {
            if (s.contains("<home>")) {
                Component comp = MiniMessage.miniMessage().deserialize(s, Placeholder.component("home", Component.text(argument)));
                return Component.text().append(comp);
            }
        } else if (sender != null || arg != null) {
            if (s.contains("<sender>")) {
                Component comp1 = MiniMessage.miniMessage().deserialize(s, Placeholder.component("sender", Component.text(sender.getName())));
                return Component.text().append(comp1);
            }
            if (s.contains("<arg>")) {
                Component comp1 = MiniMessage.miniMessage().deserialize(s, Placeholder.component("arg", Component.text(arg)));
                return Component.text().append(comp1);
            }
        } else if (list != null) {
            if (s.contains("<homes>")) {
                Component comp3 = MiniMessage.miniMessage().deserialize(s, Placeholder.component("homes", Component.text(list.toString())));
                return Component.text().append(comp3);
            }
        } else {
            Component s1 = MiniMessage.miniMessage().deserialize(s);
            return Component.text().append(s1);
        }
        return Component.text().append(MiniMessage.miniMessage().deserialize(s));
    }
}
