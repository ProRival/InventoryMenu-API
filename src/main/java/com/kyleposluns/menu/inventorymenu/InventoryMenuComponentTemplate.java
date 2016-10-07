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

public abstract class InventoryMenuComponentTemplate<C> {

    //private InventoryMenuTemplate parent;
    private Dynamic<ItemStack> displayItem;
    private Dynamic<String> displayName;
    private Dynamic<Material> displayIcon;
    private Dynamic<Integer> displayNumber;
    private Dynamic<List<String>> displayDescription;
    private Dynamic<Boolean> visibilityController;
    private Dynamic<Boolean> accessController;

    protected InventoryMenuComponentTemplate() {
        this.displayItem = Dynamic.getConstant(new ItemStack(Material.STONE));
        this.displayName = Dynamic.getConstant("");
        this.visibilityController = Dynamic.getConstant(true);
        this.accessController = (Player player) -> player.isOp();
        this.displayIcon = Dynamic.getConstant(Material.STONE);
        this.displayNumber = Dynamic.getConstant(1);
        this.displayDescription = Dynamic.getConstant(new ArrayList<>()); //Always returns the same(!) object
    }

    public abstract C construct(Player p);

    public String getDisplayName(Player p) {
        return displayName.get(p);
    }

    protected ItemStackWrapper getDisplayItemStackWrapper() {
        return constructDisplayItem();
    }

    public ItemStack getDisplayItemStack(Player p) {
        return getDisplayItemStackWrapper().construct(p);
    }

    protected ItemStack getDisplayItem(Player p) {
        return displayItem.get(p);
    }

    public Material getDisplayIcon(Player p) {
        return displayIcon.get(p);
    }

    public int getDisplayNumber(Player p) {
        return displayNumber.get(p);
    }

    public List<String> getDisplayDescription(Player p) {
        return displayDescription.get(p);
    }

    public List<String> getDisplayDescription() {
        return displayDescription.get(null);
    }

    public boolean isVisible(Player p) {
        return visibilityController.get(p);
    }

    protected Dynamic<Boolean> getVisibilityController() {
        return visibilityController;
    }

    public boolean hasAccess(Player p) {
        return accessController.get(p);
    }

    protected Dynamic<Boolean> getAccessController() {
        return accessController;
    }

    protected ItemStackWrapper constructDisplayItem() {
        ItemStackWrapper wrapper = new ItemStackWrapper(displayItem, displayIcon, displayName, displayNumber, displayDescription);
        return wrapper;
    }

    protected void setDisplayItem(ItemStack displayItem) {
        this.displayItem = Dynamic.getConstant(displayItem);
    }

    protected void setDisplayItem(Dynamic<ItemStack> displayItem) {
        this.displayItem = displayItem;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = Dynamic.getConstant(displayName);
    }

    protected void setDisplayName(Dynamic<String> displayName) {
        this.displayName = displayName;
    }

    protected void setDisplayIcon(Material displayIcon) {
        this.displayIcon = Dynamic.getConstant(displayIcon);
    }

    protected void setDisplayIcon(Dynamic<Material> displayIcon) {
        this.displayIcon = displayIcon;
    }

    protected void setDisplayNumber(int displayNumber) {
        this.displayNumber = Dynamic.getConstant(displayNumber);
    }

    protected void setDisplayNumber(Dynamic<Integer> displayNumber) {
        this.displayNumber = displayNumber;
    }

    protected void setVisibilityController(Dynamic<Boolean> visibilityController) {
        this.visibilityController = visibilityController;
    }

    protected void setAccessController(Dynamic<Boolean> accessController) {
        this.accessController = accessController;
    }

    protected void addDescriptionLine(String line) {
        this.getDisplayDescription().add(line);
    }

    protected void addDescriptionLine(Player p, String line) {
        if (displayDescription instanceof DynamicDefault) {
            displayDescription = new Dynamic<List<String>>() {
                private final Map<UUID, List<String>> map = new HashMap<>();
                private final ArrayList<String> oldDefault = (ArrayList) displayDescription.get(null);

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

    protected void setDescription(Dynamic<List<String>> displayDescription) {
        this.displayDescription = displayDescription;
    }
}
