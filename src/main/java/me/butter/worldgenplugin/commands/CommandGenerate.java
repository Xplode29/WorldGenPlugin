package me.butter.worldgenplugin.commands;

import me.butter.worldgenplugin.tasks.CleanTreeTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandGenerate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.broadcastMessage("Starting Generation");

        new CleanTreeTask(300, Bukkit.getWorld("world_game"));

        return true;
    }
}
