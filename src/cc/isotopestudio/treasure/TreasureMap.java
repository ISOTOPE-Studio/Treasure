package cc.isotopestudio.treasure;
/*
 * Created by Mars Tan on 7/18/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.treasure.util.Util;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static cc.isotopestudio.treasure.Treasure.mapData;

public abstract class TreasureMap {

    public static Map<Integer, ItemStack> MAPS = new HashMap<>();

    public static void init() {
        for (String id : mapData.getKeys(false)) {
            if (mapData.isSet(id + ".item"))
                MAPS.put(Integer.parseInt(id), mapData.getItemStack(id + ".item"));
        }
    }

    public static boolean isMapSet(int id) {
        return mapData.isConfigurationSection(String.valueOf(id)) && mapData.isSet(id + ".loc")
                && mapData.isSet(id + ".item") && mapData.isConfigurationSection(id + ".reward");
    }

    public static void setMapPos(int id, Location loc) {
        mapData.set(id + ".loc", Util.locationToString(loc));
        mapData.save();
    }

    public static Location getMapPos(int id) {
        return Util.stringToLocation(mapData.getString(id + ".loc"));
    }

    public static ItemStack getMapItem(int id) {
        return mapData.getItemStack(id + ".item");
    }

    public static void setMapItem(int id, ItemStack item) {
        mapData.set(id + ".item", item);
        MAPS.put(id, item);
        mapData.save();
    }

    public static void addMapReward(int id, ItemStack item, int odd) {
        mapData.set(id + ".reward." + System.currentTimeMillis() + ".odd", odd);
        mapData.set(id + ".reward." + System.currentTimeMillis() + ".item", item);
        mapData.save();
    }

    public static Map<ItemStack, Integer> getMapReward(int id) {
        ConfigurationSection config = mapData.getConfigurationSection(id + ".reward");
        Map<ItemStack, Integer> map = new HashMap<>();
        for (String s : config.getKeys(false)) {
            map.put(config.getItemStack(s + ".item"), config.getInt(s + ".odd"));
        }
        return map;
    }

    public static int getMapId(ItemStack map) {
        for (Integer id : MAPS.keySet()) {
            if (map.isSimilar(MAPS.get(id))) return id;
        }
        return -1;
    }

}
