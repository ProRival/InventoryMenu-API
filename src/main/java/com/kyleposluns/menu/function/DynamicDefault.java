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

public abstract class DynamicDefault<V> implements Dynamic<V> {

    private final V defaultValue, nullValue;

    protected DynamicDefault(V defaultValue) {
        this(defaultValue, null);
    }

    protected DynamicDefault(V defaultValue, V nullValue) {
        this.defaultValue = defaultValue;
        this.nullValue = nullValue;
    }

    @Override
    public V get(Player p) {
        try {
            V value = p == null ? defaultValue : getValue(p);
            return value == null ? nullValue : value;
        } catch (NullPointerException npe) {
            return nullValue;
        }
    }

    protected abstract V getValue(Player p);
}