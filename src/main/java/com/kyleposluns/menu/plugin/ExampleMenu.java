package com.kyleposluns.menu.plugin;

import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.item;
import static com.kyleposluns.menu.inventorymenu.InventoryMenuAPI.menu;

import com.kyleposluns.menu.inventorymenu.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 10/12/16.
 */
public class ExampleMenu extends Menu {

    private static Map<GameMode, MaterialData> dataMap;

    public ExampleMenu(MenuManager menuManager) {
        super(menuManager);
    }

    private InventoryMenuTemplateBuilder getTeleportationMenu() {
        InventoryMenuTemplateBuilder teleportationComponent = menu()
                .title("Teleportation Menu")
                .displayName(ChatColor.GREEN + "Teleportation Menu")
                .exitOnClickOutside(false)
                .menuControls(true)
                .displayIcon(Material.SNOW_BALL);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.isOnline()) continue;
            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setOwner(player.getName());
            is.setItemMeta(meta);
            teleportationComponent.component(item()
                    .displayName(player.getName())
                    .displayItem(is)
                    .displayIcon(is.getType())
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
        return teleportationComponent;
    }

    private InventoryMenuTemplateBuilder getGameModeSelectorMenu() {
        InventoryMenuTemplateBuilder gameModeComponent = menu()
                .title("Game Mode Selector")
                .displayName(ChatColor.LIGHT_PURPLE + "Game Mode Selector")
                .description("Click to open the Game Mode Selector!")
                .exitOnClickOutside(false)
                .menuControls(true)
                .accessController(p -> p.hasPermission("minecraft.command.gamemode") || p.hasPermission("bukkit.command.gamemode"))
                .visibilityController(p -> p.hasPermission("minecraft.command.gamemode") || p.hasPermission("bukkit.command.gamemode"))
                .displayIcon(Material.STONE_AXE)
                .exitOnClick(true)
                .glowing(true);


        for (GameMode gameMode : GameMode.values()) {
            String name = StringUtils.capitalize(gameMode.name().toLowerCase());
            gameModeComponent.component(item()
                    .displayName(ChatColor.AQUA + name)
                    .description("Set your game mode to: " + name + " mode!")
                    .displayItem(p -> {
                        ItemStack is = new ItemStack(Material.STAINED_GLASS, 1, (short) 1, dataMap.get(gameMode).getData());
                        if (p.getGameMode().equals(gameMode)) {
                            is.addUnsafeEnchantment(ItemStackWrapper.ITEMGLOW, 1);
                        }
                        return is;
                    })
                    .onClick(event -> {
                        Player player = event.getPlayer();
                        if (player.getGameMode().equals(gameMode)) {
                            player.sendMessage(ChatColor.RED + "You are already in Game Mode: " + name);
                        } else {
                            player.setGameMode(gameMode);
                            player.sendMessage(ChatColor.GREEN + "Game Mode set to: " + name);
                        }
                    }));
        }
        return gameModeComponent;
    }

    private InventoryMenuTemplateBuilder getServerStatusMenu() {
        InventoryMenuTemplateBuilder statusMenu = menu()
                .title("Server Status")
                .displayName(ChatColor.GOLD + "Server Status")
                .description("Click to open the Server Status menu!")
                .displayIcon(Material.REDSTONE_COMPARATOR)
                .visibilityController(p -> p.hasPermission("bukkit.command.stop") || p.hasPermission("minecraft.command.stop"))
                .accessController(p -> p.hasPermission("bukkit.command.stop") || p.hasPermission("minecraft.command.stop"))
                .menuControls(true)
                .exitOnClickOutside(true)
                .component(item()
                        .displayName(ChatColor.RED + "Stop")
                        .description("Click to stop the server!")
                        .description("(Will restart if a restart script exists)")
                        .displayIcon(Material.REDSTONE)
                        .onClick(event -> Bukkit.getScheduler().scheduleSyncDelayedTask(this.menuManager.getOwningPlugin(), () -> Bukkit.shutdown(), 1L)))
                .component(item()
                        .displayName(ChatColor.GREEN + "Reload")
                        .description("Click to reload the server!")
                        .description(ChatColor.RESET + "" + ChatColor.YELLOW + "Warning: Not recommended!")
                        .displayIcon(Material.REDSTONE_TORCH_ON)
                        .onClick(event -> Bukkit.getScheduler().scheduleSyncDelayedTask(this.menuManager.getOwningPlugin(), () -> Bukkit.reload(), 1L)));
        return statusMenu;

    }


    @Override
    public InventoryMenuTemplateBuilder getMenuBuilder() {
        InventoryMenuTemplateBuilder mainBuilder = menu()
                .title("Example Menu")
                .displayName("Example Menu")
                .description("A menu for various things!")
                .menuControls(true)
                .displayIcon(Material.COMPASS)
                .exitOnClickOutside(true)
                .component(getTeleportationMenu())
                .component(getServerStatusMenu())
                .component(getGameModeSelectorMenu());

        return mainBuilder;
    }

    static {
        dataMap = new HashMap<>();
        dataMap.put(GameMode.ADVENTURE, new MaterialData(Material.STAINED_GLASS, (byte) 5));
        dataMap.put(GameMode.CREATIVE, new MaterialData(Material.STAINED_GLASS, (byte) 14));
        dataMap.put(GameMode.SPECTATOR, new MaterialData(Material.STAINED_GLASS, (byte) 11));
        dataMap.put(GameMode.SURVIVAL, new MaterialData(Material.STAINED_GLASS, (byte) 4));
    }

}