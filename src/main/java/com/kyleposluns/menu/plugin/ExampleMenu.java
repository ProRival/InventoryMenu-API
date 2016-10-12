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
 * Created by Kyle on 10/7/16.
 */
public class ExampleMenu extends Menu {

    public ExampleMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public InventoryMenuTemplateBuilder getMenuBuilder() {
        return (this.menuBuilder == null ? getFullMenu() : this.menuBuilder);
    }

    public ItemStack getDisplayItemStack(Player player) {
        return getMenu().getDisplayItemStack(player);
    }

    @Override
    public void update() {
        this.menuBuilder = mainMenu().component(getTeleportationMenu());
        this.menuManager.addMenu(this);
    }

    public InventoryMenuTemplate getMenu() {
        update();
        return menuBuilder.build();
    }

    private final InventoryMenuTemplateBuilder mainMenu() {
        return menu()
                .title("Example Menu")
                .displayIcon(Material.COMPASS)
                .displayName("Example Menu")
                .exitOnClickOutside(false)
                .description("This is an")
                .description("example menu");
    }

    private final InventoryMenuTemplateBuilder getFullMenu() {
        return mainMenu().component(getTeleportationMenu());
    }

    public InventoryMenuTemplateBuilder getTeleportationMenu() {
        InventoryMenuTemplateBuilder teleportMenu = menu()
                .title("Teleportation Menu")
                .displayName("Teleportation Menu")
                .menuControls(true)
                .exitOnClickOutside(false)
                .glowing(true)
                .exitOnClick(true)
                .displayIcon(Material.MAP);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.isOnline()) continue;
            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setOwner(player.getName());
            is.setItemMeta(meta);
            teleportMenu.component(item()
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
        return teleportMenu;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ExampleMenu;
    }

}
