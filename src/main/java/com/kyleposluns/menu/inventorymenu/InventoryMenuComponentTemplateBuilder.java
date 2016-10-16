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

import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Builds an InventoryMenuComponentTemplate
 * @param <C> InventoryMenu (Item)
 * @param <T> The component template
 * @param <B> The template builder
 */
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

    /**
     * Constantly set whether the item will glow or not
     * @param glowing true if the item is supposed to glow
     * @return The generic builder
     */
    public B glowing(boolean glowing) {
        buildingObj.setGlowing(glowing);
        return actualBuilder;
    }

    /**
     * Dynamically set whether the item will glow or not
     * @param glowing true if the item is supposed to glow
     * @return The generic builder
     */
    public B glowing(Dynamic<Boolean> glowing) {
        buildingObj.setGlowing(glowing);
        return actualBuilder;
    }

    /**
     * Set the constant display name
     * @param displayName the constant name
     * @return The generic builder
     */
    public B displayName(String displayName) {
        buildingObj.setDisplayName(displayName);
        return actualBuilder;
    }

    /**
     * Set the dynamic display name
     * @param displayName the dynamic name
     * @return The generic builder
     */
    public B displayName(Dynamic<String> displayName) {
        buildingObj.setDisplayName(displayName);
        return actualBuilder;
    }

    /**
     * Set the constant display icon
     * @param displayIcon the constant material
     * @return The generic builder
     */
    public B displayIcon(Material displayIcon) {
        buildingObj.setDisplayIcon(displayIcon);
        return actualBuilder;
    }

    /**
     * Set the dynamic display icon
     * @param displayIcon the dynamic material
     * @return The generic builder
     */
    public B displayIcon(Dynamic<Material> displayIcon) {
        buildingObj.setDisplayIcon(displayIcon);
        return actualBuilder;
    }

    /**
     * Set the constant display item
     * @param displayItem the constant item
     * @return The generic builder
     */
    public B displayItem(ItemStack displayItem) {
        buildingObj.setDisplayItem(displayItem);
        return actualBuilder;
    }

    /**
     * Set the dynamic display item
     * @param displayItem the dynamic item
     * @return The generic builder
     */
    public B displayItem(Dynamic<ItemStack> displayItem) {
        buildingObj.setDisplayItem(displayItem);
        return actualBuilder;
    }

    /**
     * Set the constant display number
     * @param displayNumber the constant amount
     * @return The generic builder
     */
    public B displayNumber(int displayNumber) {
        buildingObj.setDisplayNumber(displayNumber);
        return actualBuilder;
    }

    /**
     * Set the dynamic display number
     * @param displayNumber the dynamic amount
     * @return The generic builder
     */
    public B displayNumber(Dynamic<Integer> displayNumber) {
        buildingObj.setDisplayNumber(displayNumber);
        return actualBuilder;
    }

    /**
     * Add a line of text to the display item's description
     * @param line the text
     * @return The generic builder
     */
    public B description(String line) {
        buildingObj.addDescriptionLine(line);
        return actualBuilder;
    }

    /**
     * Dynamically add a line of text to the display item's description
     * @param p the player associated with the component
     * @param line the text
     * @return The generic builder
     */
    public B description(Player p, String line) {
        buildingObj.addDescriptionLine(p, line);
        return actualBuilder;
    }

    /**
     * Dynamically set the description of the display item
     * @param description the description
     * @return The generic builder
     */
    public B description(Dynamic<List<String>> description) {
        buildingObj.setDescription(description);
        return actualBuilder;
    }

    /**
     * Set the condition for a player to see the menu
     * @param visibilityController the dynamic visibility controller
     * @return The generic builder
     */
    public B visibilityController(Dynamic<Boolean> visibilityController) {
        buildingObj.setVisibilityController(visibilityController);
        return actualBuilder;
    }


    /**
     * Set the condition for a player to use the menu
     * @param accessController the dynamic access controller
     * @return The generic builder
     */
    public B accessController(Dynamic<Boolean> accessController) {
        buildingObj.setAccessController(accessController);
        return actualBuilder;
    }

    /**
     * Build the component template
     * @return An InventoryMenuComponentTemplate
     */
    public T build() {
        return buildingObj;
    }

}
