package com.kyleposluns.menu.function;

import org.bukkit.entity.Player;

public interface Dynamic<V> {

    public V get(Player p);

    public static <V> Dynamic<V> getConstant(V constant) {
        return (Player p) -> constant;
    }

    public static <V> DynamicDefault<V> getDynamicDefault(Dynamic<V> dynamicValue, V defaultValue) {
        return getDynamicDefault(dynamicValue, defaultValue, null);
    }

    public static <V> DynamicDefault<V> getDynamicDefault(Dynamic<V> dynamicValue, V defaultValue, V nullValue) {
        return new DynamicDefault<V>(defaultValue, nullValue) {
            @Override
            public V getValue(Player p) {
                return dynamicValue.get(p);
            }
        };
    }
}