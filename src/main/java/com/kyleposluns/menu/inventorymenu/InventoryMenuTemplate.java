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
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class InventoryMenuTemplate extends InventoryMenuComponentTemplate<InventoryMenu> {

    private Dynamic<String> title;

    private Map<Integer, InventoryMenuComponentTemplate<? extends InventoryMenuComponent>> components;

    private boolean exitOnClickOutside;

    private boolean exitOnClick;

    private boolean menuControls;

    protected InventoryMenuTemplate() {
        this.title = Dynamic.getConstant("");
        this.components = new HashMap<>();
        this.exitOnClickOutside = true;
        this.menuControls = false;
    }

    /**
     * Set the constant title of the menu
     * @param title the constant title
     */
    public void setTitle(String title) {
        this.title = Dynamic.getConstant(title);
    }

    /**
     * Set the dynamic title of the menu
     * @param title the dynamic title
     */
    public void setTitle(Dynamic<String> title) {
        this.title = Dynamic.getDynamicDefault(title, "Title", "Title");
    }

    /**
     * Add a component to this menu
     * @param position the location of the component in the menu
     * @param component the component
     */
    public void addComponent(int position, InventoryMenuComponentTemplate<? extends InventoryMenuComponent> component) {
        components.put(position, component);
    }

    /**
     * Set the behavior of the menu after it has been clicked on
     * @param exitOnClick (true if the menu should close after it has been clicked)
     */
    public void setExitOnClick(boolean exitOnClick) {
        this.exitOnClick = exitOnClick;
    }

    /**
     * Set the behavior of the menu if it the user clicks outside of it
     * @param exitOnClickOutside (true if the menu should close after the user clicks outside of it)
     */
    public void setExitOnClickOutside(boolean exitOnClickOutside) {
        this.exitOnClickOutside = exitOnClickOutside;
    }

    /**
     * Set whether the menu should have menu controls or not
     * @param menuControls (true if the menu should have menu controls)
     */
    public void setMenuControls(boolean menuControls) {
        this.menuControls = menuControls;
    }

    /**
     * Get the title of the menu
     * @param p the viewer of the menu
     * @return the title
     */
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

        InventoryMenu menu = new InventoryMenu(is, getTitle(p), actualComponents, exitOnClickOutside, exitOnClick, menuControls, super.getAccessController(), super.getVisibilityController(), p);

        return menu;
    }

    /*
    private void addMenuControls(Map<Integer, InventoryMenuComponent> components) {
        components.values().stream()
                .filter(comp -> comp instanceof InventoryMenu)
                .map(comp -> (InventoryMenu) comp)
                .forEach(tempMenu -> tempMenu.addMenuControls());
    }
    */

}
