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


import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryMenuComponent {

    private final ItemStackWrapper displayItem;
    private InventoryMenu parent;
    private final Dynamic<Boolean> visibilityController, accessController;

    public InventoryMenuComponent(ItemStackWrapper displayItem, Dynamic<Boolean> visibilityController, Dynamic<Boolean> accessController) {
        this.displayItem = displayItem;
        this.visibilityController = visibilityController;
        this.accessController = accessController;
    }

    /**
     * Get the ItemStackWrapper associated with the component.
     * @return An ItemStackWrapper
     */
    protected ItemStackWrapper getDisplayItemWrapper() {
        return displayItem;
    }

    /**
     * Get the ItemStack created by the ItemStackWrapper.
     * {@link #getDisplayItemWrapper()}.{@link ItemStackWrapper#construct(Player)}
     * @param p The player associated with the ItemStack
     * @return An ItemStack
     */
    public ItemStack getDisplayItem(Player p) {
        return getDisplayItemWrapper().construct(p);
    }

    /**
     * Test whether a player can view the component
     * @param p The player associated with the component
     * @return true if the player has permission to view the component
     */
    public boolean isVisible(Player p) {
        return visibilityController.get(p);
    }

    /**
     * Test whether a player has access to the component
     * @param p The player associated with the component
     * @return true if the player has permission to use the component
     */
    public boolean hasAccess(Player p) {
        return accessController.get(p);
    }

    /**
     * Get the parent InventoryMenu
     * @return the parent.
     */
    public InventoryMenu getParent() {
        return parent;
    }

    /**
     * Set the parent InventoryMenu
     * @param parent
     */
    protected void setParent(InventoryMenu parent) {
        this.parent = parent;
    }

    /**
     * Get the root component
     * @return the root component
     */
    public InventoryMenuComponent getRoot() {
        if (parent == null) {
            return this;
        } else {
            return parent.getRoot();
        }

    }

    /**
     * Called when the component has been clicked on.
     */
    abstract void selected();

}
