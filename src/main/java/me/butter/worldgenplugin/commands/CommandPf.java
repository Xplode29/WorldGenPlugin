package me.butter.worldgenplugin.commands;

import me.butter.worldgenplugin.roles.Role;
import me.butter.worldgenplugin.tasks.CleanTreeTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPf implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();

            if(strings.length == 0) {
                player.sendMessage("Use: /pf cage-");
            }

            for(Role role : Role.values()) {
                if(role.getRole().getPlayer() == player) {
                    if(role.getRole().getCommands().contains(strings[0])) {
                        role.getRole().runCommand(strings);
                    } else {
                        player.sendMessage("You don't have this command !");
                    }
                } else {
                    player.sendMessage("You cant do this !");
                }
            }
        }

        return true;
    }
}
