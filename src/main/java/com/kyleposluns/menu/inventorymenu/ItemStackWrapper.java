package com.kyleposluns.menu.inventorymenu;


import java.lang.reflect.Field;
import java.util.List;

import com.kyleposluns.menu.enchantment.ItemGlowEnchant;
import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * (Thanks to this guy: )
 * @author Jonas
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

    public ItemStack construct(Player p) {
        return constructItemStackFromValues(displayItem.get(p), displayIcon.get(p), displayName.get(p), displayNumber.get(p), glowing.get(p), displayDescription.get(p));
    }

    private ItemStack constructItemStackFromValues(ItemStack baseStack, Material icon, String name, Integer number, Boolean glowing, List<String> description) {
        ItemStack is = baseStack.clone();

        System.out.println("ITEMSTACK WRAPPER LINE 50: " + is.getType().name() + ", " + is.getData().getData());

        if (icon != null) {
            is.setType(icon);
            //is.setData() is not working...
            //Later: is.getData().setData(icon.getData());
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
