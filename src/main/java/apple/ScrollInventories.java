package apple;

import apple.guiTypes.*;
import apple.listeners.InventoryHolderDouble;
import apple.utils.MessageFinals;
import apple.utils.OneToOneMap;
import apple.utils.YMLNavigate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScrollInventories {

    public static Inventory scrollInvAll;
    public static Inventory scrollInvAllEdit;
    public static Inventory MainGUI;
    public static Inventory MainGUIOp;
    public static OneToOneMap<String, Inventory> scrollInvIndividual = new OneToOneMap<String, Inventory>();
    public static OneToOneMap<String, Inventory> scrollInvEditIndividual = new OneToOneMap<String, Inventory>();
    private static JavaPlugin plugin;

    public ScrollInventories(JavaPlugin pl) {
        plugin = pl;
        update();
    }

    public static void update() {
        scrollInvAll = GUIPublic.makeGUIPublic();
        scrollInvAllEdit = GUIPublicEdit.makeGUIPublicEdit();
        MainGUI = initializeMainGUI(false);
        MainGUIOp = initializeMainGUI(true);
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        if (configInv == null) {
            System.err.println("Error getting any inventory from the yml..");
            return;
        }
        ConfigurationSection configInvAll = configInv.getConfigurationSection(YMLNavigate.INVENTORY_ALL);
        if (configInvAll == null) {
            System.err.println(MessageFinals.ERROR_ALL_INVENTORY_GET);
            return;
        }
        invFromConfig(configInvAll, scrollInvAll);
        invFromConfig(configInvAll, scrollInvAllEdit);
    }

    private static Inventory initializeMainGUI(boolean isOp) {
        ItemMeta im;
        int size = 9;
        Inventory gui = GUIMain.makeGUIMain();
        ItemStack privateItem = new ItemStack(Material.RED_TERRACOTTA);
        im = privateItem.getItemMeta();
        if (im != null) {
            im.setDisplayName("Private Scrolls");
            privateItem.setItemMeta(im);
        }
        ItemStack privateItemEdit = new ItemStack(Material.RED_GLAZED_TERRACOTTA);
        im = privateItemEdit.getItemMeta();
        if (im != null) {
            im.setDisplayName("Editing Private Scrolls");
            privateItemEdit.setItemMeta(im);
        }
        ItemStack publicItem = new ItemStack(Material.GREEN_TERRACOTTA);
        im = publicItem.getItemMeta();
        if (im != null) {
            im.setDisplayName("Public Scrolls");
            publicItem.setItemMeta(im);
        }
        if (isOp) {
            ItemStack publicItemEdit = new ItemStack(Material.GREEN_GLAZED_TERRACOTTA);
            im = publicItemEdit.getItemMeta();
            if (im != null) {
                im.setDisplayName("Editing Public Scrolls (CAUTION!!!)");
                publicItemEdit.setItemMeta(im);
            }
            gui.setItem(1, publicItemEdit);
        }

        gui.setItem(8, privateItemEdit);
        gui.setItem(4, privateItem);
        gui.setItem(0, publicItem);

        return gui;
    }

    private static void invFromConfig(ConfigurationSection configMain, Inventory inv) {
        int i = 0;
        ConfigurationSection configInvAllItem = configMain.getConfigurationSection(String.format("%s%d", YMLNavigate.ITEM, i));
        assert configInvAllItem != null;
        // get all the items in inv all
        while (i < 54) {
            ItemStack item = getItemFromConfig(configInvAllItem);
            inv.setItem(i, item);
            configInvAllItem = configMain.getConfigurationSection(String.format("%s%d", YMLNavigate.ITEM, ++i));
        }
    }

    private static ItemStack getItemFromConfig(ConfigurationSection config) {
        if (config == null)
            return new ItemStack(Material.AIR);
        String type = config.getString(YMLNavigate.MATERIAL);
        if (type == null)
            return new ItemStack(Material.AIR);
        Material materialType = Material.getMaterial(type);
        if (materialType == null)
            return new ItemStack(Material.AIR);
        ItemStack item = new ItemStack(materialType);
        item.setAmount(1);

        // get the name of the item
        String name = config.getString(YMLNavigate.NAME);
        if (name != null) {
            ItemMeta im = item.getItemMeta();
            if (im != null) {
                im.setDisplayName(name);
                item.setItemMeta(im);
            }
        }

        List<String> lore = new ArrayList<String>(4);
        ConfigurationSection configLore = config.getConfigurationSection(YMLNavigate.LORE);

        if (configLore == null) {
            return item;
        }

        // get the lore
        int i = 1;
        String loreLine = configLore.getString(String.format("%s%d", YMLNavigate.LINE, i++));
        while (loreLine != null) {
            lore.add(loreLine);
            loreLine = configLore.getString(String.format("%s%d", YMLNavigate.LINE, i++));
        }
        ItemMeta im = item.getItemMeta();

        // wtf happened if this is true
        if (im == null)
            return item;
        im.setLore(lore);
        item.setItemMeta(im);

        // 1 item in the stack
        return item;
    }

    public static Inventory open(Player player, boolean isEditable) throws Exception {
        Inventory scrollInvPrivate;
        if (isEditable)
            scrollInvPrivate = GUIPrivateEdit.makeGUIPrivateEdit();
        else
            scrollInvPrivate = GUIPrivate.makeGUIPrivate();
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        if (configInv == null) {
            throw new Exception("[ScrollsTp] Error getting any inventory from the yml..");
        }
        ConfigurationSection configInvPriv = configInv.getConfigurationSection(YMLNavigate.INVENTORY_PRIVATE);
        if (configInvPriv == null) {
            throw new Exception("[ScrollsTp] Error getting the private inventory from the yml..");
        }
        ConfigurationSection configInvPrivPlay = configInvPriv.getConfigurationSection(player.getUniqueId().toString());
        if (configInvPrivPlay != null) {
            invFromConfig(configInvPrivPlay, scrollInvPrivate);
        }

        // put the inventory in the correct currently opened inventories
        if (isEditable) {
            scrollInvEditIndividual.put(player.getUniqueId().toString(), scrollInvPrivate);
        } else {
            scrollInvIndividual.put(player.getUniqueId().toString(), scrollInvPrivate);
        }
        return scrollInvPrivate;
    }
}
