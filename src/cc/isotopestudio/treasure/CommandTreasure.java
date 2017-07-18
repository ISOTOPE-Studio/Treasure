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
                sender.sendMessage(S.toPrefixRed("���ִ�е�����"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("treasure.admin")) {
                player.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(S.toPrefixGreen("�����˵�"));
                player.sendMessage(S.toYellow("/" + label + " set <ID> - �������ϵ���ƷΪ�ر�ͼ"));
                player.sendMessage(S.toYellow("/" + label + " reward <ID> <����> - ��������Ʒ������ر�ͼ����"));
                player.sendMessage(S.toYellow("/" + label + " give <ID> [player] - ������Ҳر�ͼ"));
                player.sendMessage(S.toYellow("/" + label + " reload - ���������ļ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " set <ID> - �������ϵ���ƷΪ�ر�ͼ"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reward")) {
                if (args.length < 3) {
                    player.sendMessage(S.toYellow("/" + label + " reward <ID> <����> - ��������Ʒ������ر�ͼ����"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("�ɹ�����"));
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 2) {
                    player.sendMessage(S.toYellow("/" + label + " reward <ID> <����> - ��������Ʒ������ر�ͼ����"));
                    return true;
                }
                Player giveTO = Bukkit.getPlayer(args[1]);
                if (giveTO == null) {
                } else {
                }
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.onReload();
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            player.sendMessage(S.toPrefixRed("δָ֪��"));
            return true;
        }
        return false;
    }
}