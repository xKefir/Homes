package org.minerail.homes.File.Message;

public enum MessageKey {
    INPUT_TYPE("input-type"),
    LANG("LANGUAGE"),
    PREFIX("prefix"),

    COMMAND_MISSING_ARGUMENT("command.error.missing-argument"),
    MISSING_PERMISSION("command.error.missing-permission"),

    SETHOME_HOME_ALREADY_EXISTS("command.sethome.already-exists"),
    SETHOME_SUCCESSFULLY_CREATED("command.sethome.create-success"),
    SETHOME_LIMIT_WAS_REACHED("command.sethome.limit-reached"),

    DELETE_SUCCESS("command.delhome.delete-success"),
    DELETE_FAILED_INVALID_HOME("command.delhome.delete-failed-invalid-home"),

    HOME_TELEPORT_SUCCESS("command.home.teleport-success"),
    HOME_TELEPORT_FAILED_MOVED("command.home.teleport-failed-moved"),
    HOME_TELEPORT_FAILED_INVALID_HOME("command.home.teleport-failed-invalid-home"),
    HOME_TELEPORT_DELAYED("command.home.teleport-delayed"),

    HOMES_SHOW("command.homes.show"),
    HOMES_GUI_OPENED("command.homes.gui-opened"),
    HOMES_EMPTY("command.homes.empty"),

    RELOAD_SUCCESS("command.reload.reload-success"),
    RELOAD_FAILED("command.reload.reload-failed"),

    VERSION("messages-version");

    private final String path;

    MessageKey(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
