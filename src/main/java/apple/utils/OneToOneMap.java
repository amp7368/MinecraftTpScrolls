package apple.utils;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * is a one to one mapping of keys to basically keys
 *
 * @param <E> the unique key
 * @param <F> the unique val
 */
public class OneToOneMap<E, F> {
    ArrayList<E> keyList = new ArrayList<E>();
    ArrayList<F> valList = new ArrayList<F>();

    /**
     * puts a key val pair in the map
     *
     * @param key the key to put in the map
     * @param val the val to put in the map
     * @return true if adding was successful
     */
    public boolean put(E key, F val) {
        if (keyList.contains(key) || valList.contains(val)) {
            return false;
        }
        keyList.add(key);
        valList.add(val);
        return true;
    }

    /**
     * pops a key value pair using a key
     *
     * @param key the key to which the key value pair is attributed
     * @return the val corresponding to that key
     */
    public F popKey(E key) {
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            if (keyList.get(i).equals(key)) {
                keyList.remove(i);
                return valList.remove(i);
            }
        }
        return null;
    }

    /**
     * pops a value key pair using a val
     *
     * @param val the val to which the key value pair is attributed
     * @return the key corresponding to that val
     */
    public E popVal(F val) {
        int size = valList.size();
        for (int i = 0; i < size; i++) {
            if (valList.get(i).equals(val)) {
                valList.remove(i);
                return keyList.remove(i);
            }
        }
        return null;
    }

    /**
     * get a key using a val
     *
     * @param val the val used to get the key
     * @return the corresponding key
     */
    public E getFromVal(F val) {
        int size = valList.size();
        for (int i = 0; i < size; i++) {
            if (valList.get(i).equals(val)) {
                return keyList.get(i);
            }
        }
        return null;
    }

    /**
     * get a val using a key
     *
     * @param key the key used to get the val
     * @return the corresponding val
     */
    public F getFromKey(E key) {
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            if (keyList.get(i).equals(key)) {
                return valList.get(i);
            }
        }
        return null;
    }

    /**
     * see if the set contains the key: key
     *
     * @param key the key which we are comparing against
     * @return true if the set contains key: key
     */
    public boolean containsKey(E key) {
        return keyList.contains(key);
    }

    /**
     * see if the set contains the val: val
     *
     * @param val the val which we are comparing against
     * @return true if the set contains val: val
     */
    public boolean containsVal(F val) {
        return valList.contains(val);
    }

    public Iterable<F> getValues() {
        return valList;
    }
}
