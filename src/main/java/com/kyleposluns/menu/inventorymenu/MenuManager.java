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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

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
        getManagers().add(this);
    }

    /**
     * Get the menu that corresponds with the display item
     *
     * @param itemStack the corresponding item
     * @param p the player associated with the menu
     * @return a Menu if the itemstack matches a menu
     */
    public Menu getMenuByItemStack(ItemStack itemStack, Player p) {
        Optional<Menu> possibleMenu = menus.stream().filter(menu -> menu.getDisplayItemStack(p).equals(itemStack)).findFirst();
        if (possibleMenu.isPresent()) {
            return possibleMenu.get();
        }
        throw new IllegalArgumentException(itemStack.toString() + " does not get a valid menu!");
    }

    /**
     * Get the menu that corresponds with the title
     *
     * Notes:
     * Case sensitive.
     * This method is deprecated because it is an unsafe way of getting a menu.
     *
     * @param title the title
     * @param p the player associated with the menu
     * @return a Menu if the title matches a menu
     */
    @Deprecated
    public Menu getMenuByTitle(String title, Player p) {
        Optional<Menu> possibleMenu = menus.stream().filter(menu -> menu.getMenuTemplate().getTitle(p).equals(title)).findFirst();
        if (possibleMenu.isPresent()) {
            return possibleMenu.get();
        }
        throw new IllegalArgumentException(title + " is not a valid menu!");
    }


    /**
     * Get all of the display items of the menus
     *
     * Note:
     * This method still does what it is supposed to do, it is deprecated
     * because it has almost no practical uses.
     *
     * @param p a player associated with the items
     * @return an array of ItemStacks
     */
    @Deprecated
    public ItemStack[] getMenuItems(Player p) {
        return menus.stream().map(menu -> menu.getDisplayItemStack(p)).collect(Collectors.toList()).toArray(new ItemStack[]{});
    }

    /**
     * Tests whether the provided ItemStack corresponds with a menu
     *
     * @param is the item to test
     * @param p the player associated with the menu
     * @return true if the ItemStack corresponds with a menu
     */
    public boolean isMenuItem(ItemStack is, Player p) {
        return menus.stream().anyMatch((menu) -> (menu.getDisplayItemStack(p).equals(is)));
    }

    /**
     * Open a menu
     * @param is the item associated with a menu
     * @param p the player associated with the menu
     */
    public void openMenu(ItemStack is, Player p) {
        Optional<Menu> oimt = menus.stream().filter((menu) -> (menu.getDisplayItemStack(p).equals(is))).findFirst();
        if (oimt.isPresent()) {
            oimt.get().getMenuTemplate().construct(p).open();
        }
    }

    /**
     * Register a collection of menus
     * @param menus the menus
     */
    public void addMenus(Collection<Menu> menus) {
        menus.forEach(this::addMenu);
    }

    /**
     * Register many menus
     * @param menus the menus
     */
    public void addMenus(Menu... menus) {
        Arrays.stream(menus).forEach(this::addMenu);
    }

    /**
     * Register a single menu
     * @param menu the menu
     */
    public void addMenu(Menu menu) {
        if (disposed) {
            throw new IllegalStateException("Cannot add a new menu when this menu has been disposed!");
        }

        if (disposeOnDisable) {
            throw new IllegalStateException("Cannot add a new menu when this menu is scheduled to dispose!");
        }
        Bukkit.getScheduler().runTask(owningPlugin, () -> menus.add(menu));
    }

    /**
     * Schedule this MenuManager to be disposed when {@link JavaPlugin#onDisable()} is called
     * @param dispose
     */
    public void disposeOnDisable(boolean dispose) {
        this.disposeOnDisable = dispose;
    }

    /**
     * Test whether this MenuManager is scheduled to dispose when {@link JavaPlugin#onDisable()} is called
     * @return
     */
    public boolean isDisposedOnDisable() {
        return this.disposeOnDisable;
    }

    /**
     * Dispose of this MenuManager
     */
    public void dispose() {
        if (disposed) return;
        disposed = true;
        menus.clear();
        menuManagers.remove(this);
    }

    /**
     * Get the owning plugin of this MenuManager
     * @return a Plugin
     */
    public Plugin getOwningPlugin() {
        return this.owningPlugin;
    }

    /**
     * Initialize the MenuManagers list
     */
    public static void initialize() {
        menuManagers = new ArrayList<>();
    }

    /**
     * Get a list of all of the MenuManagers
     * @return a list of the menu managers
     */
    public static List<MenuManager> getManagers() {
        if (menuManagers == null) {
            initialize();
        }
        return menuManagers;
    }

    /**
     * Dispose of all of the MenuManagers
     */
    public static void disposeAll() {
        if (menuManagers != null) {
            for (Iterator<MenuManager> i = menuManagers.iterator(); i.hasNext();) {
                MenuManager mm = i.next();
                i.remove();
                mm.dispose();
            }
        }
    }

}

