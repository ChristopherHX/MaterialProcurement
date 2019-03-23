package de.homberger.christopher.materialsprocurement.ui.terminal.util;

import java.util.Comparator;
import java.util.Map.Entry;

import de.homberger.christopher.materialsprocurement.main.Assembly;

/**
 * KeyValueSort sort material list by key and value instead of only the key ( Default )
 * @author Christopher Lukas Homberger
 * @version 0.9.3
 */
public class KeyValueSort implements Comparator<Entry<Assembly, Integer>> {
    @Override
    public int compare(Entry<Assembly, Integer> l, Entry<Assembly, Integer> r) {
        int k = r.getValue().compareTo(l.getValue());
        return k == 0 ? l.getKey().compareTo(r.getKey()) : k;
    }
}