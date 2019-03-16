package de.homberger.christopher.materialsprocurement.main;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Assembly
 */
public final class Assembly implements Comparable<Assembly> {

    private final String name;
    private final Map<Assembly, Integer> assemblies;

    /**
     * Creates an empty Assembly
     */
    public Assembly(String name) {
        this.name = name;
        assemblies = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<Assembly, Integer> getAssemblies() {
        return assemblies;
    }

    private static void getDeepAssemblies(Map<Assembly, Integer> ret, Assembly c, int multiple) {
        for (Entry<Assembly, Integer> entry : c.getAssemblies().entrySet()) {
            if (!entry.getKey().getAssemblies().isEmpty()) {
                Integer old = ret.get(entry.getKey());
                if (old == null) {
                    old = 0;
                }
                int amount = multiple * entry.getValue();
                ret.put(entry.getKey(), old + amount);                    
                getDeepAssemblies(ret, entry.getKey(), amount);
            }
        }
    }

    public Map<Assembly, Integer> getDeepAssemblies() {
        Map<Assembly, Integer> ret = new TreeMap<>();
        getDeepAssemblies(ret, this, 1);
        return ret;
    }

    public Map<Assembly, Integer> getDeepComponents() {
        Map<Assembly, Integer> assemblies = new TreeMap<>();
        Map<Assembly, Integer> ret = new TreeMap<>();
        assemblies.put(this, 1);
        getDeepAssemblies(assemblies, this, 1);
        for (Entry<Assembly, Integer> entry : assemblies.entrySet()) {
            for (Entry<Assembly, Integer> entry2 : entry.getKey().getAssemblies().entrySet()) {
                if (entry2.getKey().getAssemblies().isEmpty()) {
                    Integer old = ret.get(entry2.getKey());
                    if (old == null) {
                        old = 0;
                    }
                    ret.put(entry2.getKey(), old + entry.getValue() * entry2.getValue());  
                }
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && this.getClass() == obj.getClass()) {
            return name.equals(((Assembly)obj).name);
        }
        return false;
    }

    @Override
    public int compareTo(Assembly o) {
        return name.compareTo(o.name);
    }
}