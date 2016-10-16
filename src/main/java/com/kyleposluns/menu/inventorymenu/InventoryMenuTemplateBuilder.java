/*
 *     InventoryMenu-API, a powerful menu API for inecraft.
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

/**
 * Builds an InventoryMenuTemplate
 */
public class InventoryMenuTemplateBuilder extends InventoryMenuComponentTemplateBuilder<InventoryMenu, InventoryMenuTemplate, InventoryMenuTemplateBuilder> {

    private static final int ROWSIZE = 9;
    private int dynamic = -1000;

    protected InventoryMenuTemplateBuilder() {

    }

    /**
     * Set the constant title of the menu
     * @param title the constant title
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder title(String title) {
        buildingObj.setTitle(title);
        return this;
    }

    /**
     * Set the dynamic title of the menu
     * @param title the dynamic title
     * @return this InventoryMenuTemplate Builder
     */
    public InventoryMenuTemplateBuilder title(Dynamic<String> title) {
        buildingObj.setTitle(title);
        return this;
    }

    /**
     * Add a complex item component to the template
     * @param x the x-coordanate
     * @param y the y-coordanate
     * @param itemTemplateBuilder the complex item component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int x, int y, InventoryMenuItemTemplateBuilder itemTemplateBuilder) {
        return component(x, y, itemTemplateBuilder.build());
    }

    /**
     * Add an item component to the template
     * @param x the x-coordanate
     * @param y the y-coordanate
     * @param itemTemplate the item component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int x, int y, InventoryMenuItemTemplate itemTemplate) {
        return component(y * ROWSIZE + x, itemTemplate);
    }

    /**
     * Add a complex  item component to the template
     * @param position the position of the component
     * @param itemTemplateBuilder the complex item component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int position, InventoryMenuItemTemplateBuilder itemTemplateBuilder) {
        return component(position, itemTemplateBuilder.build());
    }

    /**
     * Add an item component to the template
     * @param position the position of the component
     * @param itemTemplate the item component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int position, InventoryMenuItemTemplate itemTemplate) {
        buildingObj.addComponent(position, itemTemplate);
        return this;
    }

    /**
     * Add an item component to the template
     * @param menuItemTemplate the item component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(InventoryMenuItemTemplate menuItemTemplate) {
        return component(--dynamic, menuItemTemplate);
    }

    /**
     * Add a complex item component to the template
     * @param menuItemTemplateBuilder the complex item component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(InventoryMenuItemTemplateBuilder menuItemTemplateBuilder) {
        return component(--dynamic, menuItemTemplateBuilder.build());
    }

    /**
     * Add a complex menu component
     * @param x the x-coordanate
     * @param y the y-coordanate
     * @param menuTemplateBuilder the complex menu component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int x, int y, InventoryMenuTemplateBuilder menuTemplateBuilder) {
        return component(x, y, menuTemplateBuilder.build());
    }

    /**
     * Add a menu component
     * @param x the x-coordanate
     * @param y the y-coordanate
     * @param menuTemplate the menu component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int x, int y, InventoryMenuTemplate menuTemplate) {
        return component(y * ROWSIZE + x, menuTemplate);
    }

    /**
     * Add a complex menu component
     * @param position the position
     * @param menuTemplateBuilder the complex menu component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int position, InventoryMenuTemplateBuilder menuTemplateBuilder) {
        return component(position, menuTemplateBuilder.build());
    }

    /**
     * Add a menu component
     * @param position the position
     * @param menuTemplate the menu component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(int position, InventoryMenuTemplate menuTemplate) {
        buildingObj.addComponent(position, menuTemplate);
        return this;
    }

    /**
     * Add a menu component
     * @param menuTemplate the menu component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(InventoryMenuTemplate menuTemplate) {
        return component(++dynamic, menuTemplate);
    }

    /**
     * Add a complex menu component
     * @param menuTemplateBuilder the complex menu component
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder component(InventoryMenuTemplateBuilder menuTemplateBuilder) {
        return component(++dynamic, menuTemplateBuilder.build());
    }

    /**
     * Set the behavior of the menu after it has been clicked on
     * @param exitOnClickOutside (true if the menu should close after it has been clicked)
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder exitOnClickOutside(boolean exitOnClickOutside) {
        buildingObj.setExitOnClickOutside(exitOnClickOutside);
        return this;
    }

    /**
     * Set the behavior of the menu after it has been clicked on
     * @param exitOnClick (true if the menu should close after it has been clicked)
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder exitOnClick(boolean exitOnClick) {
        buildingObj.setExitOnClick(exitOnClick);
        return this;
    }

    /**
     * Set whether the menu should have menu controls or not
     * @param menuControls (true if the menu should have menu controls)
     * @return this InventoryMenuTemplateBuilder
     */
    public InventoryMenuTemplateBuilder menuControls(boolean menuControls) {
        buildingObj.setMenuControls(menuControls);
        return this;
    }

    @Override
    protected InventoryMenuTemplateBuilder getThis() {
        return this;
    }

    @Override
    protected InventoryMenuTemplate getObj() {
        return new InventoryMenuTemplate();
    }
}
