package org.minerail.homes;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.minerail.homes.Command.*;
import org.minerail.homes.Command.TabCompleter.TabCompleter;
import org.minerail.homes.File.Config.Config;
import org.minerail.homes.File.Message.MessageProviderLoader;
import org.minerail.homes.File.PlayerData.PlayerData;
import org.minerail.homes.Listener.QuitJoinPlayer;
import org.minerail.homes.Task.PlayerDataSaveTask;


public final class Homes extends JavaPlugin {
    public static Plugin get() {
        return Bukkit.getPluginManager().getPlugin("Homes");
    }

    @Override
    public void onEnable() {
        reloadAll();
        getServer().getPluginManager().registerEvents(new QuitJoinPlayer(), this);
        registerCommands();
        autoSave();
    }

    public static void reloadAll() {
        Config.reload();
        MessageProviderLoader.reload();
    }

    private void autoSave() {
        PlayerDataSaveTask saveTask = new PlayerDataSaveTask();
        saveTask.start(6000L);
    }

    private void registerCommands() {
        getCommand("delhome").setExecutor(new DelhomeCommand());
        getCommand("delhome").setTabCompleter(new TabCompleter());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new TabCompleter());
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("hreload").setExecutor(new ReloadCommand());
        getCommand("sethome").setExecutor(new SethomeCommand());
    }

    @Override
    public void onDisable() {
        PlayerData.saveAll();
    }
}
