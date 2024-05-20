package com.minerail.homes;

import com.minerail.homes.commands.*;
import com.minerail.homes.utils.ConfigUtils;
import com.minerail.homes.utils.DataUtils;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.IOException;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {
    private DataUtils dataUtils;
    private ConfigUtils configUtils;

    public void onEnable() {
        reigsterClasses();
        try {
            dataUtils.setupConfigFiles();
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        getCommand("sethome").setExecutor(new Sethome());
        getCommand("home").setExecutor(new Home());
        getCommand("home").setTabCompleter(new Home());
        getCommand("delhome").setExecutor(new Delhome());
        getCommand("delhome").setTabCompleter(new Home());
        getCommand("accept").setExecutor(new Accept());
        getCommand("request").setExecutor(new Request());
        getCommand("hreload").setExecutor(new ReloadCmd());
        getCommand("homes").setExecutor(new ShowHomes());
        getLogger().info("Connecting to WorldGuard..");
        if (!setupWG()) {
            getLogger().info("Disabling plugin. You need a WorldGuard plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Successfully connected to WorldGuard!");
        getLogger().info("Plugin successfully loaded!");
    }

    private void reigsterClasses() {
        dataUtils = new DataUtils();
        configUtils = new ConfigUtils();
        dataUtils.setDataUtils(dataUtils);
        configUtils.setConfigUtils(configUtils);
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
