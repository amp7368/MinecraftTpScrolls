package apple.guiTypes;

import apple.finals.GUIFinals;
import apple.utils.SpaceInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GUI implements InventoryHolder {
    ArrayList<Space> spaces;
    private final Inventory inventory;

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void put(int index, ItemStack item) {
        inventory.setItem(index, item);
    }

    public void add(ItemStack item) {
        inventory.addItem(item);
    }

    public GUI(int size, String name) {
        spaces = new ArrayList<Space>(size);
        for (int i = 0; i < size; i++) {
            spaces.add(new Space(false));
        }
        inventory = Bukkit.createInventory(this, size, name);
    }

    public Space getSpace(int i) {
        if (i < 0 || i > spaces.size())
            return null;
        return spaces.get(i);
    }

    protected void set(int space, boolean editable) {
        spaces.get(space).editable = true;
    }

    protected int getSize() {
        return spaces.size();
    }

    public GUI addHomeGUI() {
        for (SpaceInventory space : GUIFinals.sideGui) {
            int index = space.getIndex();
            inventory.setItem(index, space.getItem());
            spaces.get(index).editable = space.isEditable();
        }
        return this;
    }
}
