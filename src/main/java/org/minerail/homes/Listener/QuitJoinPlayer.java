package org.minerail.homes.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.minerail.homes.File.PlayerData;

public class QuitJoinPlayer implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        System.out.println("event called quit");
        PlayerData.get(e.getPlayer()).save();
        PlayerData.remove(e.getPlayer());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        System.out.println("event called join");
        PlayerData.get(e.getPlayer());
    }
}
