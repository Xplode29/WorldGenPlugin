package me.butter.worldgenplugin;

import me.butter.worldgenplugin.commands.CommandGenerate;
import me.butter.worldgenplugin.commands.CommandGiveRole;
import me.butter.worldgenplugin.commands.CommandPf;
import me.butter.worldgenplugin.event.JoiningEvents;
import me.butter.worldgenplugin.roles.Role;
import me.butter.worldgenplugin.tasks.RolesTask;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldGenPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("generate").setExecutor(new CommandGenerate());
        getCommand("giverole").setExecutor(new CommandGiveRole());
        getCommand("pf").setExecutor(new CommandPf());

        getServer().getPluginManager().registerEvents(new JoiningEvents(), this);
        for(Role role : Role.values()) {
            getServer().getPluginManager().registerEvents(role.getRole(), this);
        }
        Bukkit.broadcastMessage("Hello :!");

        WorldCreator worldCreator = new WorldCreator("world_game");
        worldCreator.createWorld();

        new RolesTask();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
