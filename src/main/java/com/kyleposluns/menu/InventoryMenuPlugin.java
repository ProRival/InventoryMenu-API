package com.kyleposluns.menu;

import com.kyleposluns.menu.inventorymenu.MenuManager;
import com.kyleposluns.menu.plugin.ConnectionListener;
import com.kyleposluns.menu.plugin.ExampleMenu;
import com.kyleposluns.menu.plugin.InteractionListener;
import com.kyleposluns.menu.plugin.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyle on 10/7/16.
 */
public class InventoryMenuPlugin extends JavaPlugin {

    private static final String ONLY_API = "onlyapi";

    private static InventoryMenuPlugin instance;

    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        if (!(this.getConfig().getBoolean(ONLY_API))) {
            loadListeners();
            this.menuManager = new MenuManager(this);
            this.menuManager.addMenu(new ExampleMenu());
        } else {
            System.out.println("Using InventoryMenu-API Version: " + this.getDescription().getVersion());
        }
    }

    private void loadListeners() {
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new ConnectionListener());
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
        if (!(this.getConfig().getBoolean(ONLY_API))) {
            HandlerList.unregisterAll(this);
        }
        MenuManager.disposeAll();
        menuManager = null;
        instance = null;
    }

    public static InventoryMenuPlugin get() {
        return instance;
    }
}
