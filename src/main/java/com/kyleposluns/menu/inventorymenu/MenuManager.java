package com.kyleposluns.menu.inventorymenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kyle on 10/10/16.
 */
public class MenuManager {

    private static List<MenuManager> menuManagers;

    private final Set<Menu> menus;

    private boolean disposed, disposeOnDisable;

    private Plugin owningPlugin;

    public MenuManager(Plugin owningPlugin) {
        this.owningPlugin = owningPlugin;
        this.menus = new HashSet<>();
        this.disposed = false;
        this.disposeOnDisable = false;
    }

    public ItemStack[] getMenuItems(Player player) {
        return menus.stream().map(menu -> menu.getDisplayItemStack(player)).collect(Collectors.toList()).toArray(new ItemStack[]{});
    }

    public boolean isMenuItem(ItemStack is, Player p) {
        return menus.stream().anyMatch((menu) -> (menu.getDisplayItemStack(p).equals(is)));
    }

    public void openMenu(ItemStack is, Player p) {
        Optional<Menu> oimt = menus.stream().filter((menu) -> (menu.getDisplayItemStack(p).equals(is))).findFirst();
        if (oimt.isPresent()) {
            oimt.get().getMenuTemplate().construct(p).open();
        }
    }

    public void addMenus(Collection<Menu> menus) {
        menus.forEach(this::addMenu);
    }

    public void addMenus(Menu... menus) {
        Arrays.stream(menus).forEach(this::addMenu);
    }

    public void addMenu(Menu menu) {
        if (disposed) {
            throw new IllegalStateException("Cannot add a new menu when this menu has been disposed!");
        }

        if (disposeOnDisable) {
            throw new IllegalStateException("Cannot add a new menu when this menu is scheduled to dispose!");
        }
        Bukkit.getScheduler().runTask(owningPlugin, () -> menus.add(menu));
    }

    public void disposeOnDisable(boolean dispose) {
        this.disposeOnDisable = dispose;
    }

    public boolean isDisposedOnDisable() {
        return this.disposeOnDisable;
    }

    public void dispose() {
        if (disposed) return;
        disposed = true;
        menus.clear();
        menuManagers.remove(this);
    }

    public Plugin getOwningPlugin() {
        return this.owningPlugin;
    }

    public static void initialize() {
        menuManagers = new ArrayList<>();
    }

    public static List<MenuManager> getManagers() {
        if (menuManagers == null) {
            initialize();
        }
        return menuManagers;
    }

    public static void disposeAll() {
        if (menuManagers != null) {
            for (Iterator<MenuManager> i = menuManagers.iterator(); i.hasNext();) {
                MenuManager mm = i.next();
                i.remove();
            }
        }
    }
}
