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


import java.lang.reflect.Field;
import java.util.List;

import com.kyleposluns.menu.enchantment.ItemGlowEnchant;
import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Builds an ItemStack from dynamic values
 */
public class ItemStackWrapper {

    public static final Enchantment ITEMGLOW;

    private final Dynamic<ItemStack> displayItem;
    private final Dynamic<String> displayName;
    private final Dynamic<Material> displayIcon;
    private final Dynamic<Integer> displayNumber;
    private final Dynamic<Boolean> glowing;
    private final Dynamic<List<String>> displayDescription;

    protected ItemStackWrapper(Dynamic<ItemStack> displayItem, Dynamic<Material> displayIcon, Dynamic<String> displayName, Dynamic<Integer> displayNumber, Dynamic<Boolean> glowing, Dynamic<List<String>> displayDescription) {
        this.displayItem = displayItem;
        this.displayIcon = displayIcon;
        this.displayName = displayName;
        this.displayNumber = displayNumber;
        this.glowing = glowing;
        this.displayDescription = displayDescription;
    }

    /**
     * Construct an ItemStack from wrapped values
     * @param p the player associated with the values
     * @return an ItemStack from the wrapped values
     */
    public ItemStack construct(Player p) {
        return constructItemStackFromValues(displayItem.get(p), displayIcon.get(p), displayName.get(p), displayNumber.get(p), glowing.get(p), displayDescription.get(p));
    }

    /**
     * Construct an ItemStack
     * @param baseStack the base item
     * @param icon the base Material
     * @param name the name of the item
     * @param number the amount of items
     * @param glowing whether the item should glow or not
     * @param description the description of the item
     * @return the wrapped ItemStack
     */
    private ItemStack constructItemStackFromValues(ItemStack baseStack, Material icon, String name, Integer number, Boolean glowing, List<String> description) {
        ItemStack is = baseStack.clone();

        if (icon != null && icon != Material.STONE) {
            is.setType(icon);
        }


        if (number != null) {
            is.setAmount(number);
        }

        ItemMeta im = is.getItemMeta();

        if (glowing) {
            im.addEnchant(ITEMGLOW, 1, true);
        }

        if (name != null) {
            im.setDisplayName(name);
        }
        if (!description.isEmpty()) {
            im.setLore(description);
        }

        is.setItemMeta(im);

        return is;
    }

    static {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ITEMGLOW = new ItemGlowEnchant(255);

        if (Enchantment.getByName(ITEMGLOW.getName()) == null && Enchantment.getById(ITEMGLOW.getId()) == null) {
            Enchantment.registerEnchantment(ITEMGLOW);
        }
    }
}
