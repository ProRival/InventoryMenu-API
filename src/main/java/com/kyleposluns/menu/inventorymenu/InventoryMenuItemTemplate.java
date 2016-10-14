package com.kyleposluns.menu.inventorymenu;


import org.bukkit.entity.Player;

public class InventoryMenuItemTemplate extends InventoryMenuComponentTemplate<InventoryMenuItem> {

    private InventoryMenuClickListener onClick;

    protected InventoryMenuItemTemplate() {

    }

    public InventoryMenuClickListener getOnClick() {
        return onClick;
    }

    public void setOnClick(InventoryMenuClickListener onClick) {
        this.onClick = onClick;
    }

    @Override
    public InventoryMenuItem construct(Player p) {
        ItemStackWrapper isw = getDisplayItemStackWrapper();
        return new InventoryMenuItem(isw, onClick, getVisibilityController(), getAccessController());
    }
}
