package com.kyleposluns.menu.plugin;

import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.item;
import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.menu;

import com.kyleposluns.menu.InventoryMenuPlugin;
import com.kyleposluns.menu.inventorymenu.InventoryMenuTemplate;
import com.kyleposluns.menu.inventorymenu.InventoryMenuTemplateBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 10/7/16.
 */
public class ExampleMenu {

    private static InventoryMenuTemplateBuilder menuBuilder;
    public static InventoryMenuTemplate menu;

    public static void init() {
        menuBuilder = menu()
                .title("Example Menu")
                .displayIcon(Material.COMPASS)
                .menuControls(true)
                .displayName("Example Menu")
                .description("This is an")
                .description("example menu")
                //Gamemode submenus added by game plugins
                .component(createTeleportationMenu());
        Bukkit.getScheduler().runTask(InventoryMenuPlugin.get(), () -> {
            menu = menuBuilder.build();
            InventoryMenuTemplateRepository.addMenu(menu);
        }); //Gets called after all plugins were loaded
    }

    private static InventoryMenuTemplateBuilder createTeleportationMenu() {
        InventoryMenuTemplateBuilder teleportMenu = menu()
                .title("Teleportation Menu")
                .displayName("Teleportation Menu")
                .displayIcon(Material.MAP);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1);
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setOwner(player.getName());
            is.setItemMeta(meta);
            teleportMenu.component(item()
            .displayName(player.getName())
            .displayItem(is)
            .description((p) -> {
                List<String> description = new ArrayList<>();
                description.add(ChatColor.GRAY + "Click here to teleport to " + p.getName());
                return description;
            })
            .onClick((event) -> {
                Player p = event.getPlayer();
                if (p != player) {
                    p.teleport(player);
                } else {
                    p.sendMessage(ChatColor.RED + "You cannot teleport to yourself!");
                }
                event.getItem().getParent().update();
            }));
        }
        return teleportMenu;
    }

    public static InventoryMenuTemplate getInstance() {
        return menu;
    }

}
