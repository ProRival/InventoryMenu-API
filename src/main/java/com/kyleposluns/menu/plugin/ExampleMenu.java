package com.kyleposluns.menu.plugin;

import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.item;
import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.menu;

import com.kyleposluns.menu.inventorymenu.InventoryMenuTemplate;
import com.kyleposluns.menu.inventorymenu.InventoryMenuTemplateBuilder;
import com.kyleposluns.menu.inventorymenu.Menu;
import com.kyleposluns.menu.inventorymenu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

/**
 * Created by Kyle on 10/12/16.
 */
public class ExampleMenu extends Menu {

    public ExampleMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public InventoryMenuTemplateBuilder getMenuBuilder() {
        InventoryMenuTemplateBuilder builder = menu()
                .title("Spectator Menu")
                .displayName("Spectator Menu")
                .exitOnClick(true)
                .description("Click the head of the player")
                .description("you want to teleport to!")
                .displayIcon(Material.COMPASS)
                .exitOnClickOutside(false);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.isOnline()) continue;
            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setOwner(player.getName());
            is.setItemMeta(meta);
            builder.component(item()
                    .displayName(player.getName())
                    .displayItem(is)
                    .displayIcon(is.getType())
                    .glowing(true)
                    .description((p) -> {
                        return Arrays.asList(ChatColor.GRAY + "Click here to teleport to " + player.getName());
                    })
                    .onClick((event) -> {
                        Player p = event.getPlayer();
                        if (p != player) {
                            p.teleport(player);
                            p.sendMessage(ChatColor.AQUA + "Woosh!!!");
                        } else {
                            p.sendMessage(ChatColor.RED + "You cannot teleport to yourself!");
                        }
                        event.getItem().getParent().update();
                    }));
        }
        return builder;
    }

    //The getMenuBuilder() method is now called every time getMenu is called, which is every time someone opens a menu :D
    public InventoryMenuTemplate getMenu() {
        return getMenuBuilder().build();
    }


    @Override
    public ItemStack getDisplayItemStack(Player p) {
        return getMenu().getDisplayItemStack(p);
    }
}