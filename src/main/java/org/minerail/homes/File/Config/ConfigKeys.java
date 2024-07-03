package org.minerail.homes.File.Config;

public enum ConfigKeys {
    TELEPORT_DELAY_IS_ENABLED("Main-Settings.Teleport-delay.enabled"),
    TELEPORT_DELAY_TIME_TO_TELEPORT("Main-Settings.Teleport-delay.time-to-teleport"),

    COMMANDS_REQUEST_AUTO_CANCEL_IS_ENABLED("Main-Settings.Commands.Request.auto-cancel"),
    COMMANDS_REQUEST_TIME_TO_AUTO_CANCEL("Main-Settings.Commands.Request.time-to-cancel"),

    MODULE_GUI_IS_ENABLED("Gui.show"),
    GUI_SIZE("Gui.size"),
    GUI_MATERIALS_RANDOM("Gui.Materials.random"),
    GUI_CUSTOM_MATERIALS_IS_ENABLED("Gui.Materials.custom.enabled"),
    GUI_CUSTOM_MATERIALS_TYPE("Gui.Materials.custom.material"),

    MODULE_LIMITS_IS_ENABLED("Limits.enabled"),
    LIMITS_BYPASS_FOR_ADMINS("Limits.bypass-for-admins"),
    LIMITS_MAX_REPETITIONS("Limits.max"),

    CONFIG_VERSION("Config-version");
    final String path;
    ConfigKeys(String path) {
        this.path = path;
    }
    public String getPath() {
        return this.path;
    }
}
