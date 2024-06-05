package me.butter.worldgenplugin.roles.demons;

import me.butter.worldgenplugin.WorldGenPlugin;
import me.butter.worldgenplugin.roles.AbstractRole;
import me.butter.worldgenplugin.roles.Camp;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rui extends AbstractRole {

    private Location centerOfCage;
    private float radius = 13f;

    public static boolean removeCobwebs = false;

    @Override
    public String getName() {
        return "Rui";
    }

    @Override
    public Camp getCamp() {
        return Camp.DEMON;
    }

    @Override
    public List<String> getCommands() {
        return Arrays.asList("cage", "cobwebs");
    }

    @Override
    public void update() {
        if(day()) {
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 0));
        } else {
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0));
        }

        if(removeCobwebs) {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 3; y++) {
                    for (int z = -1; z < 2; z++) {
                        Block block = getPlayer().getLocation().add(x, y, z).getBlock();
                        if (block.getType() == Material.WEB) {
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void runCommand(String[] args) {
        switch (args[0]) {
            case "cage":
                if(args.length > 1) {
                    if(Bukkit.getPlayer(args[1]) == null) {
                        getPlayer().sendMessage("Please enter a valid name");
                        return;
                    }

                    Location playerLoc = getPlayer().getLocation();
                    playerLoc.setPitch(0);

                    double xAdd = playerLoc.getDirection().getX() * 5;
                    double zAdd = playerLoc.getDirection().getZ() * 5;
                    centerOfCage = getPlayer().getLocation().getBlock().getLocation().add(new Vector(xAdd, 0, zAdd));

                    for(int y = 0; y <= radius; y++) {
                        for(int i = 0; i < 360; i++) {
                            centerOfCage.getWorld().getBlockAt(
                                    (int) Math.floor(centerOfCage.getX() + (radius - y) * Math.sin(i * (Math.PI / 180))),
                                    (int) (centerOfCage.getY() + y * 0.8),
                                    (int) Math.floor(centerOfCage.getZ() + (radius - y) * Math.cos(i * (Math.PI / 180)))
                            ).setType(Material.WEB);
                        }
                    }
                    Bukkit.getPlayer(args[1]).teleport(new Location(centerOfCage.getWorld(), centerOfCage.getX() + xAdd, centerOfCage.getY(), centerOfCage.getZ() + zAdd));

                    new CageRunnable(getPlayer(), centerOfCage, radius);
                }
                else {
                    getPlayer().sendMessage("Please enter a name");
                }
            case "cobwebs":
                removeCobwebs = !removeCobwebs;
        }
    }

    private class CageRunnable extends BukkitRunnable {
        Player player;
        Location centerOfCage;
        float radius;
        int time = 0;
        int maxTime = 60;

        public CageRunnable(Player player, Location centerOfCage, float radius) {
            this.player = player;
            this.centerOfCage = centerOfCage;
            this.radius = radius;

            this.runTaskTimer(WorldGenPlugin.getPlugin(WorldGenPlugin.class), 0, 20);
        }

        @Override
        public void run() {
            if(time > maxTime || player.getLocation().distance(centerOfCage) > radius) {
                for(int y = 0; y <= radius; y++) {
                    for(int i = 0; i < 360; i++) {
                        centerOfCage.getWorld().getBlockAt(
                                (int) Math.floor(centerOfCage.getX() + (radius - y) * Math.sin(i * (Math.PI / 180))),
                                (int) (centerOfCage.getY() + y * 0.8),
                                (int) Math.floor(centerOfCage.getZ() + (radius - y) * Math.cos(i * (Math.PI / 180)))).setType(Material.AIR);
                    }
                }
                cancel();
            }
            else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0));

                time++;
            }
        }
    }
}
