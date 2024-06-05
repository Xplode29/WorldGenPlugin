package me.butter.worldgenplugin.roles.modifiedApi.demons;

import javafx.fxml.LoadException;
import me.butter.worldgenplugin.WorldGenPlugin;
import me.butter.worldgenplugin.roles.modifiedApi.ApiAbstractRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class RuiToCopy extends ApiAbstractRole {

    @Override
    public String[] getDescription() {
        return new String[]{"Soon"};
    }

    public String getName() {
        return "Rui";
    }

    @Override
    public List<Power> getPowers() {
        return new Arrays.asList(
                new RuiCagePower(20 * 60, -1),
                new RuiStrengthString(2 * 60, 3),
                new RuiSlownessString(2 * 60, 3),
                new RuiDetectString(10, 5, getUHCPlayer().getPlayer())
        );
    }

    private static class RuiCagePower extends CommandPower {
        private Location centerOfCage;
        private float radius = 13f;

        public RuiCagePower(int cooldown, int maxUses) {
            super(cooldown, maxUses);
        }

        @Override
        public String getArgument() {
            return "cage";
        }

        @Override
        public boolean onEnable(Player player, String[] args) {
            if (args.length <= 1) {
                player.sendMessage(ChatUtil.errorPrefix("Veuillez utiliser : /" + getArgument() + " <joueur>"));
                return false;
            }

            String arg = args[1];
            Player target = Bukkit.getPlayer(arg);

            if (target == null) {
                player.sendMessage(ChatUtil.errorPrefix("Le joueur §e" + arg + "§r n'existe pas."));
                return false;
            }

            UHCPlayer uhcTarget = UHCAPI.get().getPlayerHandler().getPlayer(target.getUniqueId());
            if (uhcTarget == null || !uhcTarget.getPlayerStatsType().equals(PlayerStatsType.IN_GAME)) {
                player.sendMessage(ChatUtil.errorPrefix("Le joueur §e" + arg + "§r n'existe pas."));
                return false;
            }

            Location playerLoc = player.getLocation();
            playerLoc.setPitch(0);

            double xAdd = playerLoc.getDirection().getX() * 5;
            double zAdd = playerLoc.getDirection().getZ() * 5;
            centerOfCage = player.getLocation().getBlock().getLocation().add(new Vector(xAdd, 0, zAdd));

            for(int y = 0; y <= radius; y++) {
                for(int i = 0; i < 360; i++) {
                    centerOfCage.getWorld().getBlockAt(
                            (int) Math.floor(centerOfCage.getX() + (radius - y) * Math.sin(i * (Math.PI / 180))),
                            (int) (centerOfCage.getY() + y * 0.8),
                            (int) Math.floor(centerOfCage.getZ() + (radius - y) * Math.cos(i * (Math.PI / 180)))
                    ).setType(Material.WEB);
                }
            }

            target.teleport(new Location(centerOfCage.getWorld(), centerOfCage.getX() + xAdd, centerOfCage.getY(), centerOfCage.getZ() + zAdd));

            new CageRunnable(player, centerOfCage, radius);

            return true;
        }

        @Override
        public String getPowerName() {
            return "Cage";
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

                this.runTaskTimer(Pourfendeur.get(), 0, 20);
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
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0, false, false));

                    time++;
                }
            }
        }
    }

    private static class RuiStrengthString extends RightClickPlayerPower {
        public RuiStrengthString(int cooldown, int maxUses) {
            super(cooldown, maxUses);
        }

        @Override
        public ItemStack getItemStack() {
            return format(Material.STRING, "Fils de force").toItemStack();
        }

        @Override
        public String getPowerName() {
            return "Fils de force";
        }

        @Override
        public boolean onEnable(Player player) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2 * 60 * 20, 0, false, false));

            return true;
        }
    }

    private static class RuiSlownessString extends RightClickPlayerPower {
        public RuiSlownessString(int cooldown, int maxUses) {
            super(cooldown, maxUses);
        }

        @Override
        public ItemStack getItemStack() {
            return format(Material.STRING, "Fils de lenteur").toItemStack();
        }

        @Override
        public String getPowerName() {
            return "Fils de lenteur";
        }

        @Override
        public int getDistance() {
            return 20;
        }

        @Override
        public boolean onEnable(Player player) {
            getTarget().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2 * 60 * 20, 0, false, false));

            return true;
        }
    }

    private static class RuiDetectString extends BlockPlacePower {
        StringRunnable stringRunnable;

        public RuiDetectString(int cooldown, int maxUses, Player player) {
            super(cooldown, maxUses);
            this.stringRunnable = new StringRunnable(player);
        }

        @Override
        public ItemStack getItemStack() {
            return format(Material.STRING, "Fils de detection").toItemStack();
        }

        @Override
        public String getPowerName() {
            return "Fils de detection";
        }

        @Override
        public boolean onEnable(Player player) {
            stringRunnable.addString(getBlock.getLocation());
            return true;
        }

        private class StringRunnable extends BukkitRunnable {
            private ArrayList<Location> laidStrings;
            private Player ruiPlayer;

            public StringRunnable(Player player) {
                this.laidStrings = new ArrayList<>();
                this.ruiPlayer = player;
                this.runTaskTimer(Pourfendeur.get(), 0, 20);
            }

            public void addString(Location stringLoc) {
                laidStrings.add(stringLoc);
            }

            @Override
            public void run() {
                for(UHCPlayer uhcPlayer : UHCAPI.get().getPlayerHandler().getPlayerHaveRole()) {
                    for(Location stringLoc : laidStrings) {
                        if(stringLoc.add(new Vector(0, 1, 0)) == uhcPlayer.getPlayer().getLocation()) {

                            //Delete la location du fil dans laidStrings
                            laidStrings.remove(stringLoc);
                            ruiPlayer.sendMessage(ChatUtil.infoPrefix("§c" + uhcPlayer.getPlayer().getName() + " §r est passé sur votre fil, il fait parti du camp des " + uhcPlayer.getCamp().getCamp()));
                        }
                    }
                }
            }
        }
    }
}
