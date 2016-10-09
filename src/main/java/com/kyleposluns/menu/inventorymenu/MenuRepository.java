package com.kyleposluns.menu.inventorymenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kyle on 10/7/16.
 */
public class MenuRepository {

    private static final Set<Menu> menus = new HashSet<>();

    public static ItemStack[] getMenuItems(Player player) {
        return menus.stream().map(menu -> menu.getDisplayItemStack(player)).collect(Collectors.toList()).toArray(new ItemStack[]{});
    }

    public static void addMenus(List<Menu> menus) {
        menus.forEach(MenuRepository::addMenu);
    }

    public static void addMenus(Menu... menus) {
        Arrays.stream(menus).forEach(MenuRepository::addMenu);
    }

    public static void addMenu(Menu menu) {
        menus.add(menu);
    }

    public static boolean isMenuItem(ItemStack is, Player p) {
        return menus.stream().anyMatch((menu) -> (menu.getDisplayItemStack(p).equals(is)));
    }

    public static void openMenu(ItemStack is, Player p) {
        Optional<Menu> oimt = menus.stream().filter((menu) -> (menu.getDisplayItemStack(p).equals(is))).findFirst();
        if (oimt.isPresent()) {
            oimt.get().getMenuTemplate().construct(p).open();
        }
    }
}