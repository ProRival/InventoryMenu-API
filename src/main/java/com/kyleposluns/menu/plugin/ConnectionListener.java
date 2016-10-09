package com.kyleposluns.menu.plugin;

import com.kyleposluns.menu.inventorymenu.MenuRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Kyle on 10/7/16.
 */
public class ConnectionListener implements Listener {

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        event.getPlayer().getInventory().clear();
        //Just do it all at once.
        event.getPlayer().getInventory().addItem(MenuRepository.getMenuItems(event.getPlayer()));
    }

}
