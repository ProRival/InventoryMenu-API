package com.kyleposluns.menu.plugin;

import com.kyleposluns.menu.inventorymenu.MenuRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Kyle on 10/7/16.
 */
public class InteractionListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack is = event.getItem();
            if (is != null) {
                Player p = event.getPlayer();
                if (MenuRepository.isMenuItem(is, p)) {
                    MenuRepository.openMenu(is, p);
                }
            }
        }
    }

}
