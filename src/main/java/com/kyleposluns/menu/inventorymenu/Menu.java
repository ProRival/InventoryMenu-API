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
import org.bukkit.inventory.ItemStack;

import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.menu;

/**
 * Abstract representation of a Menu
 */
public abstract class Menu {

    protected MenuManager menuManager;

    public Menu(MenuManager menuManager) {
        if (menuManager == null) {
            throw new IllegalArgumentException("MenuManager cannot be null!");
        }
        this.menuManager = menuManager;
    }

    /**
     * Get the root menu builder with all of its components
     * @return an InventoryMenuTemplateBuilder
     */
    public abstract InventoryMenuTemplateBuilder getMenuBuilder();

    /**
     * Get the ItemStack associated with this menu
     * {@link #getMenuTemplate()}.{@link InventoryMenuTemplate#getDisplayItemStack(Player)}
     * @param p the viewer of the menu
     * @return an ItemStack
     */
    public final ItemStack getDisplayItemStack(Player p) {
        return this.getMenuTemplate().getDisplayItemStack(p);
    }

    /**
     * Get the built version of the menu
     * {@link #getMenuBuilder()}.{@link InventoryMenuTemplateBuilder#build()}
     * @return an InventoryMenuTemplate
     */
    public final InventoryMenuTemplate getMenuTemplate() {
        return this.getMenuBuilder().build();
    }

}
