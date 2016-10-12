package com.kyleposluns.menu.inventorymenu;

import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class InventoryMenuComponentTemplateBuilder<C, T extends InventoryMenuComponentTemplate<C>, B extends InventoryMenuComponentTemplateBuilder<C, T, B>> {

    //Needed for super fancy Builder inheritance
    protected B actualBuilder;
    protected T buildingObj;

    protected abstract B getThis();

    protected abstract T getObj();

    public InventoryMenuComponentTemplateBuilder() {
        actualBuilder = getThis();
        buildingObj = getObj();
    }

    public B glowing(boolean glowing) {
        buildingObj.setGlowing(glowing);
        return actualBuilder;
    }

    public B glowing(Dynamic<Boolean> glowing) {
        buildingObj.setGlowing(glowing);
        return actualBuilder;
    }

    public B displayName(String displayName) {
        buildingObj.setDisplayName(displayName);
        return actualBuilder;
    }

    public B displayName(Dynamic<String> displayName) {
        buildingObj.setDisplayName(displayName);
        return actualBuilder;
    }

    public B displayIcon(Material displayIcon) {
        buildingObj.setDisplayIcon(Dynamic.getConstant(displayIcon));
        return actualBuilder;
    }

    public B displayIcon(Dynamic<Material> displayIcon) {
        buildingObj.setDisplayIcon(displayIcon);
        return actualBuilder;
    }

    public B displayItem(ItemStack displayItem) {
        buildingObj.setDisplayItem(displayItem);
        return actualBuilder;
    }

    public B displayItem(Dynamic<ItemStack> displayItem) {
        buildingObj.setDisplayItem(displayItem);
        return actualBuilder;
    }

    public B displayNumber(int displayNumber) {
        buildingObj.setDisplayNumber(displayNumber);
        return actualBuilder;
    }

    public B displayNumber(Dynamic<Integer> displayNumber) {
        buildingObj.setDisplayNumber(displayNumber);
        return actualBuilder;
    }

    public B description(String line) {
        buildingObj.addDescriptionLine(line);
        return actualBuilder;
    }

    public B description(Player p, String line) {
        buildingObj.addDescriptionLine(p, line);
        return actualBuilder;
    }

    public B description(Dynamic<List<String>> description) {
        buildingObj.setDescription(description);
        return actualBuilder;
    }

    public B visibilityController(Dynamic<Boolean> visibilitsController) {
        buildingObj.setVisibilityController(visibilitsController);
        return actualBuilder;
    }


    public B accessController(Dynamic<Boolean> accessController) {
        buildingObj.setAccessController(accessController);
        return actualBuilder;
    }

    public T build() {
        return buildingObj;
    }

}
