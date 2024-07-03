package org.minerail.homes.Task;

import org.bukkit.scheduler.BukkitRunnable;
import org.minerail.homes.File.PlayerData;
import org.minerail.homes.Homes;

public class PlayerDataSaveTask extends BukkitRunnable {
    @Override
    public void run() {
        PlayerData.saveAll();
    }
    public void start(long interval) {
        this.runTaskTimerAsynchronously(Homes.get(), interval, interval);
    }
}
