package com.kyleposluns.menu.inventorymenu;

public class InventoryMenuAPI {

    public static InventoryMenuTemplateBuilder menu() {
        return new InventoryMenuTemplateBuilder();
    }

    public static InventoryMenuItemTemplateBuilder item() {
        return new InventoryMenuItemTemplateBuilder();
    }

}
