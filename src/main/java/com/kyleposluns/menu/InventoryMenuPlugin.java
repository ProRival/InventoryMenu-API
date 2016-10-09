package com.kyleposluns.menu;

import com.kyleposluns.menu.inventorymenu.MenuRepository;
import com.kyleposluns.menu.plugin.ConnectionListener;
import com.kyleposluns.menu.plugin.ExampleMenu;
import com.kyleposluns.menu.plugin.InteractionListener;
import com.kyleposluns.menu.plugin.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Kyle on 10/7/16.
 */
public class InventoryMenuPlugin extends JavaPlugin {

    private static final String ONLY_API = "onlyapi";

    private static InventoryMenuPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        if (!(this.getConfig().getBoolean(ONLY_API))) {
            Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
            Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
            Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

            Bukkit.getScheduler().runTask(InventoryMenuPlugin.get(), () -> {
                MenuRepository.addMenu(new ExampleMenu());
            });
        } else {
            System.out.println("Using InventoryMenu-API Version: " + this.getDescription().getVersion());
        }
    }

    @Override
    public void onDisable() {
        if (!(this.getConfig().getBoolean(ONLY_API))) {
            HandlerList.unregisterAll(this);
        }
        instance = null;
    }

    public static InventoryMenuPlugin get() {
        return instance;
    }
}
