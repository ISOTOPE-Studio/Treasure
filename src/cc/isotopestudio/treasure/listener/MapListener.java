package cc.isotopestudio.treasure.listener;
/*
 * Created by Mars Tan on 7/18/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.treasure.TreasureMap;
import cc.isotopestudio.treasure.util.S;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cc.isotopestudio.treasure.Treasure.config;

public class MapListener implements Listener {

    @EventHandler
    public void onClickMap(PlayerInteractEvent event) {
        ItemStack map = event.getPlayer().getItemInHand();
        int id = TreasureMap.getMapId(map);
        if (id < 0) return;
        if (!TreasureMap.isMapSet(id)) return;
        Location loc = TreasureMap.getMapPos(id);
        Player player = event.getPlayer();
        if (loc.distance(player.getLocation()) < 2) {
            int count = player.getItemInHand().getAmount();
            player.getInventory().setItemInHand(null);
            Map<ItemStack, Integer> box = TreasureMap.getMapReward(id);
            List<ItemStack> items = new ArrayList<>(box.keySet());
            List<Integer> luckList = new ArrayList<>(box.values());
            Map<Integer, ItemStack> extra = new HashMap<>();
            for (int j = 0; j < count; j++) {
                int i;
                double sum = 0;
                double[] luckAcc = new double[luckList.size()];
                for (i = 0; i < luckList.size(); i++) {
                    double v = luckList.get(i);
                    luckAcc[i] = sum + v;
                    sum += v;
                }
                sum = luckAcc[luckList.size() - 1];
                double point = random(sum);
                i = 0;
                while (true) {
                    if (luckAcc[i] < point) {
                        i++;
                        continue;
                    }
                    break;
                }
                ItemStack item = items.get(i);
                extra.putAll(player.getInventory().addItem(item));
            }
            if (extra.size() > 0) {
                for (ItemStack extraItem : extra.values()) {
                    player.getWorld().dropItem(player.getLocation(), extraItem);
                }
            }
            player.sendMessage(S.toPrefixGreen("恭喜你发现宝藏"));
            player.updateInventory();
        } else {
            String worldName = loc.getWorld().getName();
            if (config.isSet("worlds." + worldName)) {
                worldName = ChatColor.translateAlternateColorCodes('&',
                        config.getString("worlds." + worldName));
            }
            player.sendMessage(S.toPrefixYellow("世界: ")
                    + S.toBoldGold(worldName)
                    + S.toYellow(" x: ") + S.toBoldGold("" + loc.getBlockX())
                    + S.toYellow(" y: ") + S.toBoldGold("" + loc.getBlockY())
                    + S.toYellow(" z: ") + S.toBoldGold("" + loc.getBlockZ()));
        }
    }

    private static double random(double max) {
        return Math.random() * max;
    }
}
