package me.butter.worldgenplugin.tasks;

import me.butter.worldgenplugin.WorldGenPlugin;
import me.butter.worldgenplugin.roles.Role;
import org.bukkit.scheduler.BukkitRunnable;

public class RolesTask extends BukkitRunnable {

    public RolesTask() {
        this.runTaskTimer(WorldGenPlugin.getPlugin(WorldGenPlugin.class), 0, 1);
    }

    @Override
    public void run() {
        for(Role role : Role.values()) {
            if(role.getRole().getPlayer() != null) {
                role.getRole().update();
            }
        }
    }
}
