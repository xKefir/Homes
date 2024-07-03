package org.minerail.homes.File.Config;

public enum ConfigKeys {
    TELEPORT_DELAY_IS_ENABLED("Main-Settings.Teleport-delay.enabled"),
    TELEPORT_DELAY_TIME_TO_TELEPORT("Main-Settings.Teleport-delay.time-to-teleport"),
    TELEPORT_BYPASS_ADMIN("Main-Settings.Teleport-delay.bypass-for-admins"),

    COMMANDS_REQUEST_AUTO_CANCEL_IS_ENABLED("Main-Settings.Commands.Request.auto-cancel"),
    COMMANDS_REQUEST_TIME_TO_AUTO_CANCEL("Main-Settings.Commands.Request.time-to-cancel"),

    MODULE_GUI_IS_ENABLED("Gui.show"),
    GUI_SIZE("Gui.size"),
    GUI_TITLE("Gui.title"),
    GUI_ITEM_COLOR_OF_NAME("Gui.item-name-color"),
    GUI_MATERIALS_RANDOM("Gui.Materials.random"),
    GUI_CUSTOM_MATERIALS_TYPE("Gui.Materials.custom.material"),

    MODULE_LIMITS_IS_ENABLED("Limits.enabled"),
    LIMITS_BYPASS_FOR_ADMINS("Limits.bypass-for-admins"),
    LIMITS_MAX_REPETITIONS("Limits.max"),

    HOOK_WORLDGUARD_IS_ENABLED("Hooks.WorldGuard.enabled"),
    HOOK_WORLDGUARD_BYPASS_FOR_ADMINS("Hooks.WorldGuard.bypass-for-admins"),
    HOOK_WORLDGUARD_SECURE_TYPE("Hooks.WorldGuard.secure-type"),
    HOOK_WORLDGUARD_GET_WHITELISTED_REGIONS("Hooks.WorldGuard.whitelisted-regions"),
    HOOK_WORLDGUARD_GET_BLACKLISTED_REGIONS("Hooks.WorldGuard.blacklisted-regions"),
    HOOK_WORLDGUARD_GET_IGNORED_REGIONS("Hooks.WorldGuard.ignored-regions"),

    HOOK_PLOTSQUARED_IS_ENABLED("Hooks.PlotSquared.enabled"),

    HOOK_BENTOBOX_IS_ENABLED("Hooks.BentoBox.enabled"),

    CONFIG_VERSION("Config-version");
    final String path;

    ConfigKeys(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
