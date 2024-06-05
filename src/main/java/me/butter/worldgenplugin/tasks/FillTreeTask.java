package me.butter.worldgenplugin.tasks;

import me.butter.worldgenplugin.WorldGenPlugin;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FillTreeTask extends BukkitRunnable {

    private final World world;
    private final int size;
    private int x, y, z;

    public FillTreeTask(int size, World world) {
        this.world = world;
        this.size = size;
        this.x = -size;
        this.y = 60;
        this.z = -size;

        this.runTaskTimer(WorldGenPlugin.getPlugin(WorldGenPlugin.class), 0, 1);
    }

    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            if(x <= size) {
                int r = new Random().nextInt(100);
                if(r < 90) {
                    Block highestBlock = world.getHighestBlockAt(x, z);

                    if(highestBlock.getType() == Material.GRASS ||
                            highestBlock.getType() == Material.DIRT
                    ) {
                        world.generateTree(highestBlock.getLocation(), TreeType.DARK_OAK);
                    }
                }
                this.x++;
            } else if (z <= size) {
                x = -this.size;
                z++;
            } else {
                this.cancel();
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.teleport(new Location(Bukkit.getWorld("world_game"), 0, 100, 0));
                    player.setGameMode(GameMode.SPECTATOR);
                }
                return;
            }

        }
    }
}
