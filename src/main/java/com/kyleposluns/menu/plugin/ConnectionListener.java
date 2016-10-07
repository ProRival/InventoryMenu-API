package com.kyleposluns.menu.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Kyle on 10/7/16.
 */
public class ConnectionListener implements Listener {

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        ItemStack item = ExampleMenu.getInstance().getDisplayItemStack(event.getPlayer());
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().addItem(item);
    }

}
