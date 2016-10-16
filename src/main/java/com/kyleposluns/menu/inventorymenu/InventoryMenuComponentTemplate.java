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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kyleposluns.menu.function.Dynamic;
import com.kyleposluns.menu.function.DynamicDefault;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract representation of a component template.
 * @param <C> InventoryMenu (Item)
 */
public abstract class InventoryMenuComponentTemplate<C> {

    //private InventoryMenuTemplate parent;
    private Dynamic<ItemStack> displayItem;
    private Dynamic<String> displayName;
    private Dynamic<Material> displayIcon;
    private Dynamic<Integer> displayNumber;
    private Dynamic<List<String>> description;
    private Dynamic<Boolean> glowing;
    private Dynamic<Boolean> visibilityController;
    private Dynamic<Boolean> accessController;

    protected InventoryMenuComponentTemplate() {
        this.displayItem = Dynamic.getConstant(new ItemStack(Material.STONE));
        this.displayName = Dynamic.getConstant("");
        this.glowing = Dynamic.getConstant(false);
        this.visibilityController = Dynamic.getConstant(true);
        this.accessController = Dynamic.getConstant(true);
        this.displayIcon = Dynamic.getConstant(Material.STONE);
        this.displayNumber = Dynamic.getConstant(1);
        this.description = Dynamic.getConstant(new ArrayList<>()); //Always returns the same(!) object
    }

    /**
     * Construct a generic menu component
     * @param p The player associated with the component
     * @return A generic menu component
     */
    public abstract C construct(Player p);

    /**
     * Get the display name of the component.
     * @param p The player associated with the component
     * @return the displayname
     */
    public String getDisplayName(Player p) {
        return displayName.get(p);
    }

    /**
     * Get the ItemStackWrapper associated with the component
     * {@link #constructDisplayItem()}
     * @return an ItemStackWrapper
     */
    protected ItemStackWrapper getDisplayItemStackWrapper() {
        return constructDisplayItem();
    }

    /**
     * Get the ItemStack associated with the component
     * {@link #getDisplayItemStackWrapper()}.{@link ItemStackWrapper#construct(Player)}
     * @param p The player associated with the component
     * @return the wrapped ItemStack
     */
    public ItemStack getDisplayItemStack(Player p) {
        return getDisplayItemStackWrapper().construct(p);
    }

    /**
     * Get the raw ItemStack. (Not recommended for use)
     * @param p The player associated with the component
     * @return the raw ItemStack
     */
    protected ItemStack getDisplayItem(Player p) {
        return displayItem.get(p);
    }

    /**
     * Get the raw material. (Not recommended for use)
     * @param p The player associated with the component
     * @return the raw Material
     */
    public Material getDisplayIcon(Player p) {
        return displayIcon.get(p);
    }

    /**
     * Get the raw amount of the component
     * (Amount of the ItemStack. (i.e) ItemStack#setAmount((int i)))
     * @param p The player associated with the component
     * @return the raw amount
     */
    public int getDisplayNumber(Player p) {
        return displayNumber.get(p);
    }

    /**
     * Get the description of the component
     * @param p The player associated with the component
     * @return the description
     */
    public List<String> getDisplayDescription(Player p) {
        return description.get(p);
    }

    /**
     * Get the description of the component
     * @return The description (as a constant)
     */
    public List<String> getDisplayDescription() {
        return description.get(null);
    }

    /**
     * Test whether a player has access to view the component
     * @param p The player associated with the component
     * @return true if the player has access to view the component
     */
    public boolean isVisible(Player p) {
        return visibilityController.get(p);
    }

    /**
     * Get the Dynamic VisibilityController
     * @return the visibility controller
     */
    protected Dynamic<Boolean> getVisibilityController() {
        return visibilityController;
    }

    /**
     * Test whether a player has access to use the component
     * @param p The player associated with the component
     * @return true if the player has access to use the component
     */
    public boolean hasAccess(Player p) {
        return accessController.get(p);
    }

    /**
     * Get the Dynamic "glow controller"
     * @return the "glow controller"
     */
    protected Dynamic<Boolean> isGlowing() {
        return this.glowing;
    }

    /**
     * Get the Dynamic access controller
     * @return the access controller
     */
    protected Dynamic<Boolean> getAccessController() {
        return accessController;
    }

    /**
     * Wrap the ItemStack associated with the component
     * @return an ItemStackWrapper
     */
    protected ItemStackWrapper constructDisplayItem() {
        ItemStackWrapper wrapper = new ItemStackWrapper(displayItem, displayIcon, displayName, displayNumber, glowing, description);
        return wrapper;
    }

    /**
     * Constantly set whether the item will glow or not
     * @param glowing true if the item is supposed to glow
     */
    protected void setGlowing(boolean glowing) {
        this.glowing = Dynamic.getConstant(glowing);
    }

    /**
     * Dynamically set whether the item will glow or not
     * @param glowing true if the item is supposed to glow
     */
    protected void setGlowing(Dynamic<Boolean> glowing) {
        this.glowing = glowing;
    }

    /**
     * Set the constant display item
     * @param displayItem the constant item
     */
    protected void setDisplayItem(ItemStack displayItem) {
        this.displayItem = Dynamic.getConstant(displayItem);
    }

    /**
     * Set the dynamic display item
     * @param displayItem the dynamic item
     */
    protected void setDisplayItem(Dynamic<ItemStack> displayItem) {
        this.displayItem = displayItem;
    }

    /**
     * Set the constant display name
     * @param displayName the constant name
     */
    protected void setDisplayName(String displayName) {
        this.displayName = Dynamic.getConstant(displayName);
    }

    /**
     * Set the dynamic display name
     * @param displayName the dynamic name
     */
    protected void setDisplayName(Dynamic<String> displayName) {
        this.displayName = displayName;
    }

    /**
     * Set the constant display icon
     * @param displayIcon the constant material
     */
    protected void setDisplayIcon(Material displayIcon) {
        this.displayIcon = Dynamic.getConstant(displayIcon);
    }

    /**
     * Set the dynamic display icon
     * @param displayIcon the dynamic meterial
     */
    protected void setDisplayIcon(Dynamic<Material> displayIcon) {
        this.displayIcon = displayIcon;
    }

    /**
     * Set the constant display number
     * @param displayNumber the constant amount
     */
    protected void setDisplayNumber(int displayNumber) {
        this.displayNumber = Dynamic.getConstant(displayNumber);
    }

    /**
     * Set the dynamic display number
     * @param displayNumber the dynamic amount
     */
    protected void setDisplayNumber(Dynamic<Integer> displayNumber) {
        this.displayNumber = displayNumber;
    }

    /**
     * Set the condition for a player to see the menu
     * @param visibilityController the dynamic visibility controller
     */
    protected void setVisibilityController(Dynamic<Boolean> visibilityController) {
        this.visibilityController = visibilityController;
    }

    /**
     * Set the condition for a player to use the menu
     * @param accessController the dynamic access controller
     */
    protected void setAccessController(Dynamic<Boolean> accessController) {
        this.accessController = accessController;
    }

    /**
     * Add a line of text to the display item's description
     * @param line the text
     */
    protected void addDescriptionLine(String line) {
        this.getDisplayDescription().add(line);
    }

    /**
     * Dynamically add a line of text to the display item's description
     * @param p the player associated with the component
     * @param line the text
     */
    protected void addDescriptionLine(Player p, String line) {
        if (description instanceof DynamicDefault) {
            description = new Dynamic<List<String>>() {
                private final Map<UUID, List<String>> map = new HashMap<>();
                private final ArrayList<String> oldDefault = (ArrayList) description.get(null);

                @Override
                public List<String> get(Player p) {
                    List<String> result;
                    if (p == null) {
                        result = oldDefault;
                    } else if (map.containsKey(p.getUniqueId())) {
                        result = map.get(p.getUniqueId());
                    } else {
                        result = (List<String>) oldDefault.clone();
                        map.put(p.getUniqueId(), result);
                    }
                    return result;
                }
            };
        }
        this.getDisplayDescription(p).add(line);
    }

    /**
     * Dynamically set the description of the display item
     * @param description the description
     */
    protected void setDescription(Dynamic<List<String>> description) {
        this.description = description;
    }
}
