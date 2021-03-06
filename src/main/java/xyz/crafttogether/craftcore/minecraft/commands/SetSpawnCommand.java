package xyz.crafttogether.craftcore.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.crafttogether.craftcore.data.DataHandler;

/**
 * Command which sets the spawn of the server
 */
public class SetSpawnCommand implements CommandExecutor {

    /**
     * Method invoked when the set spawn command is executed
     *
     * @param commandSender The command executor
     * @param command       The command executed
     * @param s             The command label
     * @param args          The command arguments
     * @return True if the command is handled successfully, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command must be executed by a player");
            return true;
        }
        Player p = (Player) commandSender;
        if (p.hasPermission("craftcore.setspawn") || p.isOp()) {
            Location spawn = p.getLocation();
            DataHandler.setSpawnLocation(spawn);
            p.sendMessage(ChatColor.GREEN + "Sever spawn has been set to " + spawn.getBlockX() + ", " +
                    spawn.getBlockY() + ", " + spawn.getBlockY());
        } else {
            p.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
        }
        return true;
    }
}
