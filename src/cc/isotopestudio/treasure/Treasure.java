package cc.isotopestudio.treasure;

import cc.isotopestudio.treasure.listener.MapListener;
import cc.isotopestudio.treasure.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Treasure extends JavaPlugin {

    private static final String pluginName = "Treasure";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("Treasure").append("]").append(ChatColor.RED).toString();

    public static Treasure plugin;

    public static PluginFile mapData;

    @Override
    public void onEnable() {
        plugin = this;
        mapData = new PluginFile(this, "map.yml");

        this.getCommand("treasure").setExecutor(new CommandTreasure());

        Bukkit.getPluginManager().registerEvents(new MapListener(), this);

        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
        mapData.reload();
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

}
