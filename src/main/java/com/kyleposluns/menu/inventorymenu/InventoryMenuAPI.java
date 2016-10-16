package com.kyleposluns.menu.inventorymenu;

import com.kyleposluns.menu.InventoryMenuPlugin;

public class InventoryMenuAPI {

    public static InventoryMenuTemplateBuilder menu() {
        return new InventoryMenuTemplateBuilder();
    }

    public static InventoryMenuItemTemplateBuilder item() {
        return new InventoryMenuItemTemplateBuilder();
    }

    /**
     * You can either use this method or set onlyapi in the config to false.
     * Note: This is not a really good way to turn off the plugin.
     */
    public static void onlyAPI(boolean onlyApi) {
        InventoryMenuPlugin.get().setOnlyApi(onlyApi);
    }
}
