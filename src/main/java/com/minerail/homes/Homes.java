package com.minerail.homes;

import com.minerail.homes.commands.*;
import com.minerail.homes.dependencies.Worldguard;
import com.minerail.homes.utils.DataUtils;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.IOException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {
    public DataUtils fileManager;

    public void onEnable() {
        this.fileManager = new DataUtils(this);
        try {
            this.fileManager.setupConfigFiles();
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        getCommand("sethome").setExecutor(new Sethome(this));
        getCommand("home").setExecutor(new Home(this));
        getCommand("home").setTabCompleter(new Home(this));
        getCommand("delhome").setExecutor(new Delhome(this));
        getCommand("delhome").setTabCompleter(new Home(this));
        getCommand("accept").setExecutor(new Accept(this));
        getCommand("request").setExecutor(new Request(this));
        getCommand("hreload").setExecutor(new ReloadCmd(this));
        getCommand("homes").setExecutor(new ShowHomes(this));
        Worldguard.plugin.getLogger().info("Connecting to WorldGuard..");
        if (!setupWG()) {
            Worldguard.plugin.getLogger().info("Disabling plugin. You need a WorldGuard plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Worldguard.plugin.getLogger().info("Successfully connected to WorldGuard!");
        Worldguard.plugin.getLogger().info("Plugin successfully loaded!");
    }

    private boolean setupWG() {
        if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            return false;
        }
        RegisteredServiceProvider<WorldGuardPlugin> rsp = getServer().getServicesManager().getRegistration(WorldGuardPlugin.class);
        if (rsp == null) {
            return true;
        }
        WorldGuardPlugin wg = rsp.getProvider();
        return (wg != null);
    }

    public void onDisable() {}
}
