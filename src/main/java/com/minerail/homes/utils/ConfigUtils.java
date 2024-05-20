package com.minerail.homes.utils;

import java.io.IOException;

import com.minerail.homes.dependency.WorldGuardHook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConfigUtils {
    private ConfigUtils configUtils;
    public ConfigUtils() {}
    public void setConfigUtils(ConfigUtils configUtils) {
        this.configUtils = configUtils;
    }
    private DataUtils dataUtils;
    private WorldGuardHook worldGuardHook;

    public String errorMessage;

    public String missingPermission;

    public String rlMessage;

    public String create_home_success;

    public String errorIfPlayerIsNot;

    public String deniedToSethome;

    public String limitReachedWhileCreatingHome;

    public String existingHome;

    public String homeSuccessfullyDeleted;

    public String homeNotExists;

    public String requestSendedToSender;

    public String requestSended;

    public String requestAlreadySent;

    public String errorIfOwnerOffline;

    public String toCuboidOwner;

    public String autoCancelSender;

    public String autoCancelOwner;

    public String missingArgsRequest;

    public String acceptedToAsker;

    public String noAgreementToAsker;

    public String successToCommandSender;

    public String noAgreementToCommandSender;

    public String missingArgumentsAccept;

    public String tpdelay;

    public String playerMoved;

    public String teleportedPlayer;

    public String homeNotExistsWhileTp;

    public String homesList;

    public String homesListIsEmpty;



    public void reloadConfig() throws IOException, InvalidConfigurationException {
        dataUtils.setupConfigFiles();
    }

    public boolean wgEnabled() {
        Boolean wg = dataUtils.config.getBoolean("Settings.WorldGuard.enabled");
        if (wg) {
            return true;
        } else {
            return false;
        }
    }

    public boolean limitsEnabled() {
        Boolean limit = dataUtils.config.getBoolean("Settings.Limits.enabled");
        if (limit) {
            return true;
        } else {
            return false;
        }
    }

    public boolean homesGuiEnabled() {
        Boolean gui = dataUtils.config.getBoolean("Settings.Gui.enabled");
        if (gui) {
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public String getPrefix() {
        if (dataUtils.config.getString("Settings.Messages.prefix") != null) {
            return dataUtils.config.getString("Settings.Messages.prefix");
        } else {
            return "";
        }
    }

    public int getPlayerLimits(Player p) {
        int max = dataUtils.config.getInt("Settings.Limits.max");
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
        errorMessage = getPrefix() + dataUtils.config.getString("Settings.Messages.errorMessage");
        missingPermission = getPrefix() + dataUtils.config.getString("Settings.Messages.MissingPermission");
        rlMessage = getPrefix() + dataUtils.config.getString("Settings.Messages.reloadMessage");
        create_home_success = getPrefix() + dataUtils.config.getString("Settings.Messages.createHome.success");
        errorIfPlayerIsNot = getPrefix() + dataUtils.config.getString("Settings.Messages.createHome.deniedToSetHomeIfPlayerIsNotMember");
        deniedToSethome = getPrefix() + dataUtils.config.getString("Settings.Messages.createHome.deniedToSetHome");
        limitReachedWhileCreatingHome = getPrefix() + dataUtils.config.getString("Settings.Messages.createHome.limitReached");
        existingHome = getPrefix() + dataUtils.config.getString("Settings.Messages.createHome.homeExists");
        homeSuccessfullyDeleted = getPrefix() + dataUtils.config.getString("Settings.Messages.deleteHome.success");
        homeNotExists = getPrefix() + dataUtils.config.getString("Settings.Messages.deleteHome.homeNotExist");
        requestSendedToSender = getPrefix() + dataUtils.config.getString("Settings.Messages.request.requestSentToSender");
        requestSended = getPrefix() + dataUtils.config.getString("Settings.Messages.request.requestSent");
        requestAlreadySent = getPrefix() + dataUtils.config.getString("Settings.Messages.request.requestAlreadySent");
        errorIfOwnerOffline = getPrefix() + dataUtils.config.getString("Settings.Messages.request.errorIfOwnerIsOffline");
        toCuboidOwner = getPrefix() + dataUtils.config.getString("Settings.Messages.request.toCuboidOwner");
        autoCancelSender = getPrefix() + dataUtils.config.getString("Settings.Messages.request.auto-cancel-to-sender");
        autoCancelOwner = getPrefix() + dataUtils.config.getString("Settings.Messages.request.auto-cancel-to-owner");
        missingArgsRequest = getPrefix() + dataUtils.config.getString("Settings.Messages.request.MissingArguments");
        acceptedToAsker = getPrefix() + dataUtils.config.getString("Settings.Messages.accept.acceptedToAsker");
        noAgreementToAsker = getPrefix() + dataUtils.config.getString("Settings.Messages.accept.noAgreementToAsker");
        successToCommandSender = getPrefix() + dataUtils.config.getString("Settings.Messages.accept.successToCommandSender");
        noAgreementToCommandSender = getPrefix() + dataUtils.config.getString("Settings.Messages.accept.noAgreementToCommandSender");
        missingArgumentsAccept = getPrefix() + dataUtils.config.getString("Settings.Messages.accept.MissingArguments");
        tpdelay = getPrefix() + dataUtils.config.getString("Settings.Messages.home.delay");
        playerMoved = getPrefix() + dataUtils.config.getString("Settings.Messages.home.player-moved");
        teleportedPlayer = getPrefix() + dataUtils.config.getString("Settings.Messages.home.tpMessage");
        homeNotExistsWhileTp = getPrefix() + dataUtils.config.getString("Settings.Messages.home.homeNotExist");
        homesList = getPrefix() + dataUtils.config.getString("Settings.Messages.homes.show-homes");
        homesListIsEmpty = getPrefix() + dataUtils.config.getString("Settings.Messages.homes.empty");
        worldGuardHook.plugin.getLogger().info("Messages successfully loaded");
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
