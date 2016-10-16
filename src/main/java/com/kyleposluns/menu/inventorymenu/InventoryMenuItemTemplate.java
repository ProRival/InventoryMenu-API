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
 * Template for an InventoryMenuItem
 */
public class InventoryMenuItemTemplate extends InventoryMenuComponentTemplate<InventoryMenuItem> {

    private InventoryMenuClickListener onClick;

    protected InventoryMenuItemTemplate() {

    }

    /**
     * Get the listener associated with the item
     * @return An InventoryMenuClickListener
     */
    public InventoryMenuClickListener getOnClick() {
        return onClick;
    }

    /**
     * Set the listener associated with the item
     * @param onClick the listener
     */
    public void setOnClick(InventoryMenuClickListener onClick) {
        this.onClick = onClick;
    }

    @Override
    public InventoryMenuItem construct(Player p) {
        ItemStackWrapper isw = getDisplayItemStackWrapper();
        return new InventoryMenuItem(isw, onClick, getVisibilityController(), getAccessController());
    }
}
