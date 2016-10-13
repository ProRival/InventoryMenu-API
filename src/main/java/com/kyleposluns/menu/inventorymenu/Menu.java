package com.kyleposluns.menu.inventorymenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Kyle on 10/9/16.
 */
public abstract class Menu {

    protected MenuManager menuManager;

    public Menu(MenuManager menuManager) {
        if (menuManager == null) {
            throw new IllegalArgumentException("MenuManager cannot be null!");
        }
        this.menuManager = menuManager;}

    public abstract InventoryMenuTemplateBuilder getMenuBuilder();

    public ItemStack getDisplayItemStack(Player p) {
        return getMenuTemplate().getDisplayItemStack(p);
    }

    public InventoryMenuTemplate getMenuTemplate() {
        return getMenuBuilder().build();
    }

}
