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

    protected abstract V getValue(Player slp);
}