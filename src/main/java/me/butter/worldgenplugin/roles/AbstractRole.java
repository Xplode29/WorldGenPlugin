package me.butter.worldgenplugin.roles;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractRole implements Listener {

    private UUID playerUUID;
    private Camp camp;

    public String getName() {
        return "Test Name";
    }

    public String getLore() {
        return "Soon...";
    }

    public Camp getCamp() {
        return camp;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void update() {}

    public List<String> getCommands() {
        return new ArrayList<>();
    }

    public void runCommand(String[] args) {

    }

    public boolean day() {
        long time = getPlayer().getWorld().getTime();

        return time < 12300 || time > 23850;
    }
}
