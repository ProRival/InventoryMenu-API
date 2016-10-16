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

public class InventoryMenuItem extends InventoryMenuComponent {

    private final InventoryMenuClickListener onClick;

    public InventoryMenuItem(ItemStackWrapper displayItem, InventoryMenuClickListener onClick, Dynamic<Boolean> visibilityController, Dynamic<Boolean> accessController) {
        super(displayItem, visibilityController, accessController);
        this.onClick = onClick;
    }

    @Override
    protected void selected() {
        if (onClick != null) {
            onClick.onClick(new InventoryMenuClickEvent(this, this.getParent().getOwner()));
        }
    }
}
