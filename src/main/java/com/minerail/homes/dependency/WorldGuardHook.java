package com.minerail.homes.dependency;

import com.minerail.homes.utils.DataUtils;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardHook {
    public WorldGuardHook(){}
    private final DataUtils dataUtils = new DataUtils();
    public Plugin plugin = Bukkit.getPluginManager().getPlugin("Homes");

    public boolean isPlayerInRegion(Player player) {
        String secure = dataUtils.config.getString("Settings.WorldGuard.secure-type");
        boolean ignored = false;
        Location playerLocation = player.getLocation();
        RegionManager regionManager = getRegionManager(player.getWorld());
        if (regionManager != null) {
            for (ProtectedRegion region : regionManager.getRegions().values()) {
                if (region.contains(BukkitAdapter.asBlockVector(playerLocation))) {
                    if (!player.hasPermission("homes.admin.bypass")) {
                        if (secure.equals("PROTECTIONSTONES")) {
                            for (String ignoredRegions : dataUtils.config.getStringList("Settings.WorldGuard.regions.ignored-regions")) {
                                if (region.getId().equals(ignoredRegions)) {
                                    ignored = true;
                                    break;
                                }
                            }
                            if (!ignored != true) {
                                if (region.isMember(WorldGuardPlugin.inst().wrapPlayer(player)) || region.isOwner(WorldGuardPlugin.inst().wrapPlayer(player))) {
                                    return true;
                                } else if (region.hasMembersOrOwners()) {
                                    return false;
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        }
                        if (secure.equals("DEFAULT")) {
                            for (String regionNameWhitelist : dataUtils.config.getStringList("Settings.WorldGuard.regions.whitelisted-regions")) {
                                if (region.getId().equals(regionNameWhitelist)) {
                                    return true;
                                }
                            }
                            for (String regionNameBlacklist : dataUtils.config.getStringList("Settings.WorldGuard.regions.blacklisted-regions")) {
                                if (region.getId().equals(regionNameBlacklist)) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }return false;
    }

    private static RegionManager getRegionManager(World world) {
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
    }
}
