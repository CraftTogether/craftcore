package xyz.crafttogether.craftcore.minecraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.crafttogether.craftcore.CraftCore;
import xyz.crafttogether.craftcore.minecraft.events.PlayerMoveBlockEvent;

public class PlayerMove implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerMoveEvent(PlayerMoveEvent event) {
        Location al = event.getTo();
        Location bf = event.getFrom();
        if (al.getBlockX() - bf.getBlockX() != 0 || al.getBlockY() - bf.getBlockY() != 0 || al.getBlockZ() - bf.getBlockZ() != 0) {
            Bukkit.getScheduler().runTaskAsynchronously(CraftCore.getPlugin(), () -> {
                Bukkit.getPluginManager().callEvent(new PlayerMoveBlockEvent(event.getPlayer()));
            });
        }
    }
}
