package cc.isotopestudio.treasure.cmd;
/*
 * Created by Mars Tan on 7/18/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.treasure.TreasureMap;
import cc.isotopestudio.treasure.util.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static cc.isotopestudio.treasure.Treasure.mapData;
import static cc.isotopestudio.treasure.Treasure.plugin;

public class CommandTreasure implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("treasure")) {
            if (!sender.hasPermission("treasure.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage(S.toPrefixGreen("帮助菜单"));
                sender.sendMessage(S.toYellow("/" + label + " set <ID> - 设置手上的物品为藏宝图"));
                sender.sendMessage(S.toYellow("/" + label + " setpos <ID> - 设置当前位置为藏宝图地点"));
                sender.sendMessage(S.toYellow("/" + label + " reward <ID> <几率> - 将手上物品添加至藏宝图奖励"));
                sender.sendMessage(S.toYellow("/" + label + " give <ID> [player] [数量] - 给与玩家藏宝图"));
                sender.sendMessage(S.toYellow("/" + label + " list - 给与玩家藏宝图"));
                sender.sendMessage(S.toYellow("/" + label + " reload - 重载配置文件"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.onReload();
                sender.sendMessage(S.toPrefixGreen("成功"));
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(S.toPrefixGreen("---- 藏宝图列表 ----"));
                for (String key : mapData.getKeys(false)) {
                    int i = Integer.parseInt(key);
                    sender.sendMessage(" - "
                            + (TreasureMap.isMapSet(i) ? S.toGreen(i + "") : S.toRed(i + "")));
                }
                return true;
            } else if (args.length < 2) {
                sender.sendMessage(S.toPrefixRed("未知指令"));
                return true;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
                if (id < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sender.sendMessage(S.toPrefixRed("ID格式不对"));
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " give <ID> [player] [数量] - 给与玩家藏宝图"));
                    return true;
                }
                if (!TreasureMap.isMapSet(id)) {
                    sender.sendMessage(S.toPrefixRed("藏宝图未完成"));
                    return true;
                }
                Player giveto;
                if (args.length > 2) {
                    giveto = Bukkit.getPlayer(args[2]);
                    if (giveto == null) {
                        sender.sendMessage(S.toPrefixRed("玩家不存在"));
                        return true;
                    }
                } else {
                    if (sender instanceof Player)
                        giveto = (Player) sender;
                    else {
                        sender.sendMessage(S.toPrefixRed("玩家执行的命令"));
                        return true;
                    }
                }
                ItemStack item = TreasureMap.getMapItem(id);
                if (args.length > 3) {
                    int amount;
                    item = item.clone();
                    try {
                        amount = Integer.parseInt(args[3]);
                        if (amount < 0) throw new NumberFormatException();
                    } catch (NumberFormatException e) {
                        sender.sendMessage(S.toPrefixRed("amount格式不对"));
                        return true;
                    }
                    item.setAmount(amount);
                }
                giveto.getInventory().addItem(item);
                sender.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("玩家执行的命令"));
                return true;
            }
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " set <ID> - 设置手上的物品为藏宝图"));
                    return true;
                }
                ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("手中没有物品"));
                    return true;
                }
                TreasureMap.setMapItem(id, item);
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("setpos")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " setpos <ID> - 设置当前位置为藏宝图地点"));
                    return true;
                }
                TreasureMap.setMapPos(id, player.getLocation());
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reward")) {
                if (args.length < 3) {
                    player.sendMessage(S.toYellow("/" + label + " reward <ID> <几率> - 将手上物品添加至藏宝图奖励"));
                    return true;
                }
                ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("手中没有物品"));
                    return true;
                }
                int odd;
                try {
                    odd = Integer.parseInt(args[2]);
                    if (odd < 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("几率格式不对"));
                    return true;
                }
                TreasureMap.addMapReward(id, item, odd);
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            player.sendMessage(S.toPrefixRed("未知指令"));
            return true;
        }
        return false;
    }
}