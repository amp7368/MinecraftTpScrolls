package apple;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScrollCommand implements CommandExecutor {
    private JavaPlugin plugin;
    private Inventory scrollInvAll;

    public ScrollCommand(ScrollMain plugin) {
        this.plugin = plugin;
        scrollInvAll = Bukkit.createInventory(new InventoryChest(plugin, 54, "Scrolls"), InventoryType.CHEST);
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "channel.yml");
        @NotNull YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection("inventory");
        ConfigurationSection configInvAll = configInv.getConfigurationSection("all");
        int i = 1;
        ConfigurationSection configInvAllItem = configInvAll.getConfigurationSection(String.format("item%d", i++));
        // get all the items in inv all
        while (configInvAllItem != null) {
            ItemStack item = getItemFromConfig(configInvAllItem);
            configInvAllItem = configInvAll.getConfigurationSection(String.format("item%d", i++));
            scrollInvAll.addItem(item);
        }


        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta im = item.getItemMeta();
        assert im != null;
        List<String> lore = new ArrayList<String>(4);

        im.setLore(lore);
        item.setItemMeta(im);
        scrollInvAll.addItem(item);
        PluginCommand command = plugin.getCommand("scroll");
        if (command == null) {
            return;
        }
        command.setExecutor(this);
    }

    private static ItemStack getItemFromConfig(ConfigurationSection config) {
        String type = config.getString("material");
        ItemStack item = new ItemStack(Material.getMaterial(type));
        List<String> lore = new ArrayList<String>(4);

        // get the lore
        int i = 1;
        String loreLine = config.getString(String.format("line%d", i++));
        while (loreLine != null) {
            lore.add(loreLine);
            loreLine = config.getString(String.format("line%d", i++));
        }
        ItemMeta im = item.getItemMeta();
        im.setLore(lore);
        item.setItemMeta(im);

        // 1 item in the stack
        item.setAmount(1);
        return item;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // get the player
        Player player = Bukkit.getServer().getPlayer(commandSender.getName());

        // return if player is non existent
        if (player == null)
            return false;

        // open the scroll inventory
        player.openInventory(scrollInvAll);

        return false;
    }
}
