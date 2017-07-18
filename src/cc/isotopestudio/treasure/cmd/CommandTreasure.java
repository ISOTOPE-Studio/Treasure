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
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage(S.toPrefixGreen("�����˵�"));
                sender.sendMessage(S.toYellow("/" + label + " set <ID> - �������ϵ���ƷΪ�ر�ͼ"));
                sender.sendMessage(S.toYellow("/" + label + " setpos <ID> - ���õ�ǰλ��Ϊ�ر�ͼ�ص�"));
                sender.sendMessage(S.toYellow("/" + label + " reward <ID> <����> - ��������Ʒ������ر�ͼ����"));
                sender.sendMessage(S.toYellow("/" + label + " give <ID> [player] [����] - ������Ҳر�ͼ"));
                sender.sendMessage(S.toYellow("/" + label + " list - ������Ҳر�ͼ"));
                sender.sendMessage(S.toYellow("/" + label + " reload - ���������ļ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.onReload();
                sender.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(S.toPrefixGreen("---- �ر�ͼ�б� ----"));
                for (String key : mapData.getKeys(false)) {
                    int i = Integer.parseInt(key);
                    sender.sendMessage(" - "
                            + (TreasureMap.isMapSet(i) ? S.toGreen(i + "") : S.toRed(i + "")));
                }
                return true;
            } else if (args.length < 2) {
                sender.sendMessage(S.toPrefixRed("δָ֪��"));
                return true;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
                if (id < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sender.sendMessage(S.toPrefixRed("ID��ʽ����"));
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " give <ID> [player] [����] - ������Ҳر�ͼ"));
                    return true;
                }
                if (!TreasureMap.isMapSet(id)) {
                    sender.sendMessage(S.toPrefixRed("�ر�ͼδ���"));
                    return true;
                }
                Player giveto;
                if (args.length > 2) {
                    giveto = Bukkit.getPlayer(args[2]);
                    if (giveto == null) {
                        sender.sendMessage(S.toPrefixRed("��Ҳ�����"));
                        return true;
                    }
                } else {
                    if (sender instanceof Player)
                        giveto = (Player) sender;
                    else {
                        sender.sendMessage(S.toPrefixRed("���ִ�е�����"));
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
                        sender.sendMessage(S.toPrefixRed("amount��ʽ����"));
                        return true;
                    }
                    item.setAmount(amount);
                }
                giveto.getInventory().addItem(item);
                sender.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("���ִ�е�����"));
                return true;
            }
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " set <ID> - �������ϵ���ƷΪ�ر�ͼ"));
                    return true;
                }
                ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("����û����Ʒ"));
                    return true;
                }
                TreasureMap.setMapItem(id, item);
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("setpos")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " setpos <ID> - ���õ�ǰλ��Ϊ�ر�ͼ�ص�"));
                    return true;
                }
                TreasureMap.setMapPos(id, player.getLocation());
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reward")) {
                if (args.length < 3) {
                    player.sendMessage(S.toYellow("/" + label + " reward <ID> <����> - ��������Ʒ������ر�ͼ����"));
                    return true;
                }
                ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("����û����Ʒ"));
                    return true;
                }
                int odd;
                try {
                    odd = Integer.parseInt(args[2]);
                    if (odd < 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("���ʸ�ʽ����"));
                    return true;
                }
                TreasureMap.addMapReward(id, item, odd);
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            player.sendMessage(S.toPrefixRed("δָ֪��"));
            return true;
        }
        return false;
    }
}