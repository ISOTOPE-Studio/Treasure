package cc.isotopestudio.treasure;
/*
 * Created by Mars Tan on 7/18/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.treasure.util.S;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static cc.isotopestudio.treasure.Treasure.plugin;

public class CommandTreasure implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("treasure")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("玩家执行的命令"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("treasure.admin")) {
                player.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(S.toPrefixGreen("帮助菜单"));
                player.sendMessage(S.toYellow("/" + label + " set <ID> - 设置手上的物品为藏宝图"));
                player.sendMessage(S.toYellow("/" + label + " reward <ID> <几率> - 将手上物品添加至藏宝图奖励"));
                player.sendMessage(S.toYellow("/" + label + " give <ID> [player] - 给与玩家藏宝图"));
                player.sendMessage(S.toYellow("/" + label + " reload - 重载配置文件"));
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " set <ID> - 设置手上的物品为藏宝图"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reward")) {
                if (args.length < 3) {
                    player.sendMessage(S.toYellow("/" + label + " reward <ID> <几率> - 将手上物品添加至藏宝图奖励"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("成功屏蔽"));
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " reward <ID> <几率> - 将手上物品添加至藏宝图奖励"));
                    return true;
                }
                Player giveTO = Bukkit.getPlayer(args[1]);
                if (giveTO == null) {
                } else {
                }
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.onReload();
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            player.sendMessage(S.toPrefixRed("未知指令"));
            return true;
        }
        return false;
    }
}