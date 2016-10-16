package com.kyleposluns.menu.plugin;

import com.kyleposluns.menu.InventoryMenuPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        event.getPlayer().getInventory().clear();
        //Just do it all at once.
        event.getPlayer().getInventory().addItem(InventoryMenuPlugin.get().getMenuManager().getMenuItems(event.getPlayer()));
    }

}
