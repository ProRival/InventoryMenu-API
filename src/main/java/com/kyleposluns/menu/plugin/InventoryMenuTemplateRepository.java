package com.kyleposluns.menu.plugin;

import com.kyleposluns.menu.inventorymenu.InventoryMenuTemplate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Kyle on 10/7/16.
 */
public class InventoryMenuTemplateRepository {

    private static final Set<InventoryMenuTemplate> menus = new HashSet<>();

    public static void initTemplates() {
        ExampleMenu.init();
    }

    public static void addMenu(InventoryMenuTemplate menu) {
        menus.add(menu);
    }

    public static boolean isMenuItem(ItemStack is, Player p) {
        return menus.stream().anyMatch((template) -> (template.getDisplayItemStack(p).equals(is)));
    }

    public static void openMenu(ItemStack is, Player p) {
        Optional<InventoryMenuTemplate> oimt = menus.stream().filter((template) -> (template.getDisplayItemStack(p).equals(is))).findFirst();
        if (oimt.isPresent()) {
            oimt.get().construct(p).open();
        }
    }
}