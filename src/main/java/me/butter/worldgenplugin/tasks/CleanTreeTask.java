package me.butter.worldgenplugin.tasks;

import me.butter.worldgenplugin.WorldGenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class CleanTreeTask extends BukkitRunnable {

    private final World world;
    private final int size;
    private int x, y, z;

    public CleanTreeTask(int size, World world) {
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
            if(i % 10 == 0) {
                int progress = 100 * (x + z + 600) / (size * 2 + 600);
                Bukkit.broadcastMessage("Generation Progress: " + progress + " / 100");
            }
            if(x <= size) {
                for(int y = 50; y < 150; y++) {
                    Block block = world.getBlockAt(x, y, z);

                    if(block.getType() == Material.WATER ||
                        block.getType() == Material.STATIONARY_WATER ||
                        block.getType() == Material.LAVA ||
                        block.getType() == Material.STATIONARY_LAVA ||
                        block.getType() == Material.SAND
                    ) {
                        block.setType(Material.DIRT);
                    } else if (block.getType() == Material.SANDSTONE) {
                        block.setType(Material.STONE);
                    } else if (block.getType() == Material.SNOW ||
                        block.getType() == Material.CACTUS ||
                        block.getType() == Material.LEAVES ||
                        block.getType() == Material.LEAVES_2 ||
                        block.getType() == Material.LOG ||
                        block.getType() == Material.LOG_2
                    ) {
                        block.setType(Material.AIR);
                    }

                    world.setBiome(x, z, Biome.ROOFED_FOREST);
                }

                Block highestBlock = world.getHighestBlockAt(x, z);

                if(highestBlock.getType() == Material.GRAVEL ||
                        highestBlock.getType() == Material.STONE ||
                        highestBlock.getType() == Material.SAND
                ) {
                    highestBlock.setType(Material.GRASS);
                }

                this.x++;
            } else if (z <= size) {
                x = -this.size;
                z++;
            } else {
                this.cancel();
                new FillTreeTask(this.size, this.world);
                return;
            }
        }
    }
}
