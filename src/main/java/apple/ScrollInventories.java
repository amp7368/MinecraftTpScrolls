package apple;

import apple.guiTypes.*;
import apple.finals.MessageFinals;
import apple.utils.OneToOneMap;
import apple.finals.YMLNavigate;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
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
    public static OneToOneMap<String, Inventory> scrollInvQuick = new OneToOneMap<String, Inventory>();
    public static OneToOneMap<String, Inventory> scrollInvEditQuick = new OneToOneMap<String, Inventory>();
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
        if (scrollInvAll.getHolder() instanceof GUI) {
            ((GUI) scrollInvAll.getHolder()).addHomeGUI();
        }
        if (scrollInvAllEdit.getHolder() instanceof GUI) {
            ((GUI) scrollInvAllEdit.getHolder()).addHomeGUI();
        }
    }

    private static Inventory initializeMainGUI(boolean isAdmin) {
        return GUIMain.makeGUIMain(isAdmin);
    }

    private static void invFromConfig(ConfigurationSection configMain, Inventory inv) {
        int i = 0;
        ConfigurationSection configInvAllItem = configMain.getConfigurationSection(String.format("%s%d", YMLNavigate.ITEM, i));
        assert configInvAllItem != null;
        // get all the items in inv all
        while (i < inv.getSize()) {
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

        List<String> lore = new ArrayList<>(4);
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

    public static Inventory openPersonalInv(String inventoryType, Player player, boolean isEditable) throws Exception {
        Inventory scrollInvPersonal;
        if (inventoryType.equals(YMLNavigate.INVENTORY_QUICK)) {
            if (isEditable) {
                scrollInvPersonal = GUIQuickEdit.makeGUIQuickEdit();
                InventoryHolder holder = scrollInvPersonal.getHolder();
                if (holder instanceof GUI) {
                    ((GUI) scrollInvPersonal.getHolder()).addHomeGUI();
                }
            } else {
                scrollInvPersonal = GUIQuick.makeGUIQuick();
                InventoryHolder holder = scrollInvPersonal.getHolder();
                if (holder instanceof GUI) {
                    ((GUI) scrollInvPersonal.getHolder()).addHomeGUI();
                }
            }
        } else {
            if (isEditable) {
                scrollInvPersonal = GUIPrivateEdit.makeGUIPrivateEdit();
                InventoryHolder holder = scrollInvPersonal.getHolder();
                if (holder instanceof GUI) {
                    ((GUI) scrollInvPersonal.getHolder()).addHomeGUI();
                }
            } else {
                scrollInvPersonal = GUIPrivate.makeGUIPrivate();
                InventoryHolder holder = scrollInvPersonal.getHolder();
                if (holder instanceof GUI) {
                    ((GUI) scrollInvPersonal.getHolder()).addHomeGUI();
                }
            }
        }

        // read the yml file for the current inventory
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        if (configInv == null) {
            throw new Exception(MessageFinals.ERROR_ANY_INVENTORY_GET);
        }
        ConfigurationSection configInvPriv = configInv.getConfigurationSection(inventoryType);
        if (configInvPriv == null) {
            EditExit.updateConfig(scrollInvPersonal, inventoryType + "." + player.getUniqueId().toString(), player.getName());
            return scrollInvPersonal;
        }
        ConfigurationSection configInvPrivPlay = configInvPriv.getConfigurationSection(player.getUniqueId().toString());
        if (configInvPrivPlay != null) {
            invFromConfig(configInvPrivPlay, scrollInvPersonal);
        }

        // put the inventory in the correct currently opened inventories
        if (inventoryType.equals(YMLNavigate.INVENTORY_QUICK)) {
            if (isEditable) {
                scrollInvEditQuick.put(player.getUniqueId().toString(), scrollInvPersonal);
            } else {
                scrollInvQuick.put(player.getUniqueId().toString(), scrollInvPersonal);
            }
        } else {
            if (isEditable) {
                scrollInvEditIndividual.put(player.getUniqueId().toString(), scrollInvPersonal);
            } else {
                scrollInvIndividual.put(player.getUniqueId().toString(), scrollInvPersonal);
            }
        }
        InventoryHolder holder = scrollInvPersonal.getHolder();
        if (holder instanceof GUI)
            ((GUI) holder).addHomeGUI();
        return scrollInvPersonal;
    }
}
