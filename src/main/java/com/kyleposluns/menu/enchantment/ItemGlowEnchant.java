package com.kyleposluns.menu.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Note: After testing this, it doesn't really work on skulls;
 * i'm not sure why.
 * Created by Kyle on 10/11/16.
 */
public class ItemGlowEnchant extends Enchantment {

    public ItemGlowEnchant(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "ITEM_GLOW";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }
}
