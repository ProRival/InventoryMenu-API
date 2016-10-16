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

import java.util.*;

import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.stream.Collectors;

/**
 * Represents an interactive menu
 */
public class InventoryMenu extends InventoryMenuComponent implements InventoryHolder {

    private static final int ROWSIZE = 9;

    private final Inventory inventory;
    private final Map<Integer, InventoryMenuComponent> allComponents;
    private final boolean exitOnClickOutside;
    private final boolean exitOnClick;
    private final boolean menuControls;
    private final Player p;
    private final Map<Integer, InventoryMenuComponent> currentComponents;

    protected InventoryMenu(ItemStackWrapper displayItem, String title, Map<Integer, InventoryMenuComponent> components, boolean exitOnClickOutside, boolean exitOnClick, boolean menuControls, Dynamic<Boolean> accessController, Dynamic<Boolean> visibilityController, Player p) {
        super(displayItem, visibilityController, accessController);
        this.p = p;
        this.allComponents = components;
        this.inventory = Bukkit.createInventory(this, calcRows() * ROWSIZE, title);
        this.exitOnClickOutside = exitOnClickOutside;
        this.exitOnClick = exitOnClick;
        this.menuControls = menuControls;
        this.currentComponents = new HashMap<>();
        addMenuControls();
        setParents();
        populateInventory();
    }

    /**
     * Get the owner of the menu.
     * @return The Owner
     */
    public Player getOwner() {
        return p;
    }

    /**
     * Calculate the amount of rows the Inventory needs to have.
     * @return rows
     */
    private int calcRows() {
        OptionalInt oInt = allComponents.keySet().stream().mapToInt(i -> i).max();
        int maxIndex = oInt.orElse(0);

        //Normaly it would be (size + ROWSIZE - 1 ) / ROWSIZE but since we have an index no -1
        //and btw its an integer divison round up thingy -> black magic
        int rows = (Math.max(maxIndex, allComponents.size() - 1) + ROWSIZE) / ROWSIZE;
        return rows;
    }

    /**
     * Set the parent of each component.
     */
    private void setParents() {
        allComponents.values().forEach(component -> component.setParent(this));
    }

    /**
     * Fill the actual inventory.
     */
    protected void populateInventory() {
        inventory.clear();
        currentComponents.clear();
        allComponents.entrySet().stream().filter((entry) -> (entry.getKey() >= 0 && entry.getValue().isVisible(p))).forEach((entry) -> {
            currentComponents.put(entry.getKey(), entry.getValue());
        });
        int current = 0;

        List<Integer> keys = allComponents.keySet().stream().filter(key -> key < 0 && allComponents.get(key).isVisible(p)).collect(Collectors.toList());
        for (int key : keys) {
            InventoryMenuComponent value = allComponents.get(key);
            while (currentComponents.containsKey(current)) {
                current++;
            }
            currentComponents.put(current, value);
        }
        currentComponents.forEach((key, value) -> inventory.setItem(key, value.getDisplayItemWrapper().construct(p)));
    }

    /**
     * Add menu controls to the menu.
     */
    protected void addMenuControls() {
        if (menuControls) {
            InventoryMenuComponent rootComp = getRoot();
            if (rootComp instanceof InventoryMenu) {
                InventoryMenuItem goBackItem = InventoryMenuAPI.item()
                        .displayIcon(Material.ANVIL)
                        .displayName(ChatColor.GREEN + "Go back")
                        .description("Click to go back one menu level")
                        .onClick(event -> {
                            if (getParent() != null) {
                                getParent().open();
                            } else {
                                event.getPlayer().closeInventory();
                            }
                        })
                        .build().construct(p);
                allComponents.put(inventory.getSize() - 1, goBackItem);
                //inventory.setItem(0, goBackItem.getDisplayItemWrapper().construct(p));

            }
        }

    }

    @Override
    public void selected() {
        open();
    }

    /**
     * Open the menu.
     */
    public void open() {
        Player player = p;
        if (!this.hasAccess(p)) {
            player.sendMessage(ChatColor.RED + "You are not allowed to open this InventoryMenu");
        } else {
            Inventory current = player.getOpenInventory().getTopInventory();
            if (current == null) {
                player.openInventory(inventory);
            } else {
                player.closeInventory();
                player.openInventory(inventory);
            }
        }
    }

    /**
     * Close the menu.
     * @param player The target.
     */
    public void close(Player player) {
        if (inventory.getViewers().contains(player)) {
            player.closeInventory();
            inventory.getViewers().remove(player);
        }
    }

    /**
     * Called when the player clicks on an item.
     * @param index the position in the inventory.
     */
    public void selectItem(int index) {
        if (currentComponents.containsKey(index)) {
            InventoryMenuComponent component = currentComponents.get(index);
            if (component.hasAccess(p)) {
                component.selected();
            } else {
                p.closeInventory();
                p.sendMessage(ChatColor.RED + "You don't have access to this");
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Test whether the menu is supposed to close when the player clicks outside of the menu.
     * @return true if the menu is supposed to close when the player clicks outside of the menu.
     */
    public boolean exitOnClickOutside() {
        return exitOnClickOutside;
    }

    /**
     * Test whether the menu is supposed to close when the player selects an item.
     * @return true if the player clicked inside the menu.
     */
    public boolean exitOnClick() {
        return exitOnClick;
    }

    /**
     * Update the contents of the inventory.
     * {@link #populateInventory()}
     */
    public void update() {
        populateInventory();
    }
}
