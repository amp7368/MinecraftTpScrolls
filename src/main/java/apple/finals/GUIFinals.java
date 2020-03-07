package apple.finals;

import apple.finals.GUIActionsFinal;
import apple.utils.SpaceInventory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GUIFinals {
    public static final String ADMIN = "admin" ;
    public static final String SERVER_NAME = "server";
    public static Set<SpaceInventory> sideGui = new HashSet<SpaceInventory>();
    private static ItemStack itemGray;
    private static ItemStack itemHome;

    public GUIFinals() {
        ItemMeta im;

        // make the filler itemstack
        List<String> loreGray = new ArrayList<String>();
        itemGray = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        im = itemGray.getItemMeta();
        if (im != null) {
            im.setDisplayName("");
            im.setLore(loreGray);
            im.setLocalizedName(GUIActionsFinal.doNothing);
            itemGray.setItemMeta(im);
        }

        // make the home button
        List<String> loreHome = new ArrayList<String>();
        itemHome = new ItemStack(Material.GREEN_TERRACOTTA);
        im = itemHome.getItemMeta();
        if (im != null) {
            im.setDisplayName("Home");
            im.setLore(loreHome);
            im.setLocalizedName(GUIActionsFinal.HOME);
            itemHome.setItemMeta(im);
        }
    }

    public static void initialize() {
        sideGui.add(new SpaceInventory(7, false, new ItemStack(itemGray)));
        sideGui.add(new SpaceInventory(16, false, new ItemStack(itemGray)));
        sideGui.add(new SpaceInventory(17, false, new ItemStack(itemGray)));
        sideGui.add(new SpaceInventory(8, false, itemHome));
    }
    public static boolean containsItemInSideGUI(ItemStack item){
        for(SpaceInventory space:sideGui){
            if (space.getItem().equals(item)){
                return true;
            }
        }return false;
    }
}
