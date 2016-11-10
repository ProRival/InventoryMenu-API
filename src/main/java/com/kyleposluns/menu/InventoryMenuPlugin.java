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

package com.kyleposluns.menu;

import com.kyleposluns.menu.inventorymenu.MenuManager;
import com.kyleposluns.menu.plugin.ConnectionListener;
import com.kyleposluns.menu.plugin.ServerManagerMenu;
import com.kyleposluns.menu.plugin.InteractionListener;
import com.kyleposluns.menu.plugin.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class InventoryMenuPlugin extends JavaPlugin {

    private static String SERVER_MENU = "servermenu";

    private static InventoryMenuPlugin instance;

    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        List<Listener> listeners = new ArrayList<>();

        if (this.getConfig().getBoolean(SERVER_MENU)) {
            listeners.add(new ConnectionListener());
            this.menuManager = new MenuManager(this);
            this.menuManager.addMenu(new ServerManagerMenu(menuManager));
        }
        listeners.add(new InteractionListener());
        listeners.add(new InventoryClickListener());
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }


    public MenuManager getMenuManager() {
        return this.menuManager;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        MenuManager.disposeAll();
        menuManager = null;
        instance = null;
    }

    public static InventoryMenuPlugin get() {
        return instance;
    }
}
