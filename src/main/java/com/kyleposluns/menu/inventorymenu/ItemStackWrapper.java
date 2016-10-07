package com.kyleposluns.menu.inventorymenu;


import java.util.List;

import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jonas
 */
public class ItemStackWrapper {

    private final Dynamic<ItemStack> displayItem;
    private final Dynamic<String> displayName;
    private final Dynamic<Material> displayIcon;
    private final Dynamic<Integer> displayNumber;
    private final Dynamic<List<String>> displayDescription;

    protected ItemStackWrapper(Dynamic<ItemStack> displayItem, Dynamic<Material> displayIcon, Dynamic<String> displayName, Dynamic<Integer> displayNumber, Dynamic<List<String>> displayDescription) {
        this.displayItem = displayItem;
        this.displayIcon = displayIcon;
        this.displayName = displayName;
        this.displayNumber = displayNumber;
        this.displayDescription = displayDescription;
    }

    public ItemStack construct(Player p) {
        return constructItemStackFromValues(displayItem.get(p), displayIcon.get(p), displayName.get(p), displayNumber.get(p), displayDescription.get(p));
    }

    private ItemStack constructItemStackFromValues(ItemStack baseStack, Material icon, String name, Integer number, List<String> description) {
        ItemStack is = baseStack.clone();

        if (icon != null) {
            is.setType(icon);
            //is.setData() is not working...
            //Later: is.getData().setData(icon.getData());
        }

        if (number != null) {
            is.setAmount(number);
        }

        ItemMeta im = is.getItemMeta();

        if (name != null) {
            im.setDisplayName(name);
        }
        if (!description.isEmpty()) {
            im.setLore(description);
        }

        is.setItemMeta(im);

        return is;
    }
}
