package cc.isotopestudio.treasure;

import cc.isotopestudio.treasure.cmd.CommandTreasure;
import cc.isotopestudio.treasure.listener.MapListener;
import cc.isotopestudio.treasure.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Treasure extends JavaPlugin {

    private static final String pluginName = "Treasure";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("�ر�ͼ").append("]").append(ChatColor.RED).toString();

    public static Treasure plugin;

    public static PluginFile config;
    public static PluginFile mapData;

    @Override
    public void onEnable() {
        plugin = this;
        config = new PluginFile(this, "config.yml","config.yml");
        config.setEditable(false);
        mapData = new PluginFile(this, "map.yml");

        TreasureMap.init();

        this.getCommand("treasure").setExecutor(new CommandTreasure());

        Bukkit.getPluginManager().registerEvents(new MapListener(), this);

        getLogger().info(pluginName + "�ɹ�����!");
        getLogger().info(pluginName + "��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
        config.reload();
        mapData.reload();
        TreasureMap.init();
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "�ɹ�ж��!");
    }

}
