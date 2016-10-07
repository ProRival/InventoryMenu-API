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

    protected ItemStackWrapper getDisplayItemWrapper() {
        return displayItem;
    }

    public ItemStack getDisplayItem(Player p) {
        return getDisplayItemWrapper().construct(p);
    }

    public boolean isVisible(Player p) {
        return visibilityController.get(p);
    }

    public boolean hasAccess(Player p) {
        return accessController.get(p);
    }

    public InventoryMenu getParent() {
        return parent;
    }

    protected void setParent(InventoryMenu parent) {
        this.parent = parent;
    }

    public InventoryMenuComponent getRoot() {
        if (parent == null) {
            return this;
        } else {
            return parent.getRoot();
        }

    }

    abstract void selected();
}
