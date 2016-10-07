package com.kyleposluns.menu.inventorymenu;

import com.kyleposluns.menu.function.Dynamic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class InventoryMenuTemplate extends InventoryMenuComponentTemplate<InventoryMenu> {

    private Dynamic<String> title;

    private Map<Integer, InventoryMenuComponentTemplate<? extends InventoryMenuComponent>> components;

    private boolean exitOnClickOutside;

    private boolean menuControls;

    protected InventoryMenuTemplate() {
        this.title = Dynamic.getConstant("");
        this.components = new HashMap<>();
        this.exitOnClickOutside = true;
        this.menuControls = false;
    }

    public void setTitle(String title) {
        this.title = Dynamic.getConstant(title);
    }

    public void setTitle(Dynamic<String> title) {
        this.title = Dynamic.getDynamicDefault(title, "Title", "Title");
    }

    public void addComponent(int position, InventoryMenuComponentTemplate<? extends InventoryMenuComponent> component) {
        components.put(position, component);
    }

    public void setExitOnClickOutside(boolean exitOnClickOutside) {
        this.exitOnClickOutside = exitOnClickOutside;
    }

    public void setMenuControls(boolean menuControls) {
        this.menuControls = menuControls;
    }

    public String getTitle(Player p) {
        return title.get(p);
    }

    @Override
    public InventoryMenu construct(Player p) {
        ItemStackWrapper is = constructDisplayItem();

        //Construct components
        Map<Integer, InventoryMenuComponent> actualComponents = components.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().construct(p)));

        InventoryMenu menu = new InventoryMenu(is, getTitle(p), actualComponents, exitOnClickOutside, menuControls, super.getAccessController(), super.getVisibilityController(), p);

        return menu;
    }

    private void addMenuControls(Map<Integer, InventoryMenuComponent> components) {
        components.values().stream()
                .filter(comp -> comp instanceof InventoryMenu)
                .map(comp -> (InventoryMenu) comp)
                .forEach(tempMenu -> tempMenu.addMenuControls());
    }

}
