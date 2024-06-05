package me.butter.worldgenplugin.commands;

import me.butter.worldgenplugin.roles.AbstractRole;
import me.butter.worldgenplugin.roles.Role;
import me.butter.worldgenplugin.tasks.CleanTreeTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandGiveRole implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            if(strings.length >= 2) {
                Player playerAimed = Bukkit.getPlayer(strings[1]);
                if (playerAimed == null) {
                    Bukkit.broadcastMessage("Please provide a valid Player Name");
                    return true;
                }
                for(Role role : Role.values()) {
                    if(strings[0].equalsIgnoreCase(role.getRole().getName())) {
                        role.getRole().setPlayerUUID(playerAimed.getUniqueId());

                        player.sendMessage(playerAimed.getDisplayName() + " has now the role " + role.getRole().getName());
                        playerAimed.sendMessage("You are now the role " + role.getRole().getName());
                    }
                }
            }
        }
        return true;
    }
}
