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

package com.kyleposluns.menu.inventorymenu;

import org.bukkit.entity.Player;

/**
 * Event for when a player clicks on the InventoryMenu
 */
public class InventoryMenuClickEvent {

    private final InventoryMenuItem item;
    private final Player player;

    public InventoryMenuClickEvent(InventoryMenuItem item, Player player) {
        this.item = item;
        this.player = player;
    }

    /**
     * Get the clicked InventoryMenuItem
     * @return InventoryMenuItem associated with the event.
     */
    public InventoryMenuItem getItem() {
        return item;
    }

    /**
     * Get the player who clicked the item.
     * @return the clicker
     */
    public Player getPlayer() {
        return player;
    }
}
