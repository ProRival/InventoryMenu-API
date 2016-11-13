/*
 *     InventoryMenu-API, a powerful menu API for Minecraft.
 *     Copyright (C) 2016 Kyle Posluns
 *     Copyright (C) 2016 SpleefLeague team and contributors
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kyleposluns.menu.plugin;

import com.kyleposluns.menu.inventorymenu.InventoryMenu;
import com.kyleposluns.menu.inventorymenu.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof InventoryMenu) {
            InventoryMenu menu = (InventoryMenu) inventory.getHolder();
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                    exitMenuIfClickOutSide(menu, player);
                } else {
                    int index = event.getRawSlot();
                    if (index < inventory.getSize()) {
                        exitMenuOnClick(menu, player);
                        menu.selectItem(index);
                    } else {
                        exitMenuIfClickOutSide(menu, player);
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryAction(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player p = (Player) event.getWhoClicked();
            MenuManager.getManagers().stream().filter(menuManager -> event.getCurrentItem() != null && menuManager.isMenuItem(event.getCurrentItem(), p)).forEach(menuManager -> {
                event.setCancelled(true);
            });
        }

    }


    private void exitMenuOnClick(InventoryMenu menu, Player player) {
        if (menu.exitOnClick()) {
            menu.close(player);
        }
    }


    private void exitMenuIfClickOutSide(InventoryMenu menu, Player player) {
        if (menu.exitOnClickOutside()) {
            menu.close(player);
        }
    }
}
