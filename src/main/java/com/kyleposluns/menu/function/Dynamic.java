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

package com.kyleposluns.menu.function;

import org.bukkit.entity.Player;

public interface Dynamic<V> {

    /**
     * Gets the Object associated with the player.
     * @param p The player the stored object is associated with.
     * @return The generic object.
     */
    public V get(Player p);

    /**
     * Get an instance of Dynamic from a constant.
     * @param constant The constant value.
     * @param <V> The Generic type of the object.
     * @return Dynamic Object of a generic object.
     */
    public static <V> Dynamic<V> getConstant(V constant) {
        return (Player p) -> constant;
    }

    /**
     * Get an instance of DynamicDefault from a Dynamic Object and default value.
     * @param dynamicValue The Dynamic value.
     * @param defaultValue The default value.
     * @param <V> The generic type of the objects.
     * @return DynamicDefault of a generic object.
     */
    public static <V> DynamicDefault<V> getDynamicDefault(Dynamic<V> dynamicValue, V defaultValue) {
        return getDynamicDefault(dynamicValue, defaultValue, null);
    }

    /**
     * Get an instance of DynamicDefault from a Dynamic Object, default value, and a null value.
     * @param dynamicValue The Dynamic value.
     * @param defaultValue The default value.
     * @param nullValue The null value.
     * @param <V> The generic type of the objects.
     * @return DynamicDefault of a generic object.
     */
    public static <V> DynamicDefault<V> getDynamicDefault(Dynamic<V> dynamicValue, V defaultValue, V nullValue) {
        return new DynamicDefault<V>(defaultValue, nullValue) {
            @Override
            public V getValue(Player p) {
                return dynamicValue.get(p);
            }
        };
    }
}