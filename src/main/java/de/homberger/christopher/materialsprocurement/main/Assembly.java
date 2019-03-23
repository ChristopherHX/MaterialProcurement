package de.homberger.christopher.materialsprocurement.main;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Assembly / Component sortable by name
 * @author Christopher Lukas Homberger
 * @version 0.9.4
 */
public final class Assembly implements Comparable<Assembly> {
    /**
     * name of this Assembly
     */
    private final String name;
    /**
     * Assemblies directly included in this Assembly
     */
    private final Map<Assembly, Integer> assemblies;
    /**
     * Reference to MaterialsProcurement manager which coordinates all modifications
     */
    private final MaterialsProcurement procurement;

    /**
     * Creates an empty Assembly and register it to MaterialsProcurement manager
     * @param procurement Reference to MaterialsProcurement manager which coordinates all modifications
     * @param name of this Assembly
     */
    public Assembly(MaterialsProcurement procurement, String name) {
        this.name = name;
        assemblies = new TreeMap<>();
        this.procurement = procurement;
        procurement.registerComponent(this);
    }

    /**
     * Get its name
     * @return its name
     */
    public String getName() {
        return name;
    }

    /**
     * @return an unmodifiable Map of directly contained Assemblies
     */
    public Map<Assembly, Integer> getAssemblies() {
        return Collections.unmodifiableMap(assemblies);
    }

    /**
     * Internally
     * Fill recursivly an Map with all assembly contained in- or directly
     * @param ret empty map to fill
     * @param c current Assembly for lookup
     * @param multiple how many times the parent is included in the root assembly
     */
    private static void getDeepAssemblies(Map<Assembly, Integer> ret, Assembly c, int multiple) {
        for (Entry<Assembly, Integer> entry : c.getAssemblies().entrySet()) {
            if (!entry.getKey().isComponent()) {
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

    /**
     * @return a unmodifiable Map of all assemblies ( without components )
     * included directly or indirectly by this Assembly
     */
    public Map<Assembly, Integer> getDeepAssemblies() {
        Map<Assembly, Integer> ret = new TreeMap<>();
        getDeepAssemblies(ret, this, 1);
        return Collections.unmodifiableMap(ret);
    }

    /**
     * @return a unmodifiable Map of all components included directly or indirectly by this Assembly
     */
    public Map<Assembly, Integer> getDeepComponents() {
        Map<Assembly, Integer> assemblies = new TreeMap<>();
        Map<Assembly, Integer> ret = new TreeMap<>();
        assemblies.put(this, 1);
        getDeepAssemblies(assemblies, this, 1);
        for (Entry<Assembly, Integer> entry : assemblies.entrySet()) {
            for (Entry<Assembly, Integer> entry2 : entry.getKey().getAssemblies().entrySet()) {
                if (entry2.getKey().isComponent()) {
                    Integer old = ret.get(entry2.getKey());
                    if (old == null) {
                        old = 0;
                    }
                    ret.put(entry2.getKey(), old + entry.getValue() * entry2.getValue());  
                }
            }
        }
        return Collections.unmodifiableMap(ret);
    }

    /**
     * @return only true if its child-assemblies are empty
     */
    public boolean isComponent() {
        return assemblies.isEmpty();
    }

    /**
     * Gets Parts by name
     * @param name of the Part
     * @return the part and amount with the name or null
     */
    private Entry<Assembly, Integer> getPart(String name) {
        for (Entry<Assembly, Integer> entry : getAssemblies().entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Check if this Assembly contains an Part with this name
     * @param name of the Part
     * @return true if contained
     */
    public boolean containsPart(String name) {
        return getPart(name) != null;
    }

    /**
     * Internal Removes all parts from this Assembly
     */
    protected void removeAllParts() {
        assemblies.clear();
    }
    
    /**
     * Detect cylclic Assemblies
     * @param cAssembly current Assembly for checking
     */
    private boolean checkCycle(Assembly cAssembly) {
        if (equals(cAssembly)) {
            return true;
        }
        for (Assembly assembly : cAssembly.assemblies.keySet()) {
            if (checkCycle(assembly)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Internal Adds an Assembly new parts or remove them
     * 
     * @param name Assembly / Component to add / remove
     * @param amount to add / remove
     * @throws IllegalArgumentException more objects was removed than exists
     */
    protected void addPart(String name, int amount) throws IllegalArgumentException {
        // Have to copy parameter, because of checkstyle
        int camount = amount;
        Assembly pAssembly = null;
        Entry<Assembly, Integer> entry = getPart(name);
        if (entry != null) {
            pAssembly = entry.getKey();
            // Add current amount to reflect total amount
            camount += entry.getValue();
        }
        if (camount < 0) {
            throw new IllegalArgumentException("You are trying to remove more than available");            
        } else if (pAssembly == null) {
            pAssembly = procurement.getAssembly(name);
            if (pAssembly == null) {
                pAssembly = new Assembly(procurement, name);
            } else if (checkCycle(pAssembly)) {
                throw new IllegalArgumentException("You are trying to create a cycle");
            }
        }
        if (camount == 0) {
            assemblies.remove(pAssembly);
            if (isComponent()) {
                // Now its a component remove strong reference
                procurement.removeAssembly(this.name);
            }
        } else {
            assemblies.put(pAssembly, camount);
        }
    }

    @Override
    /**
     * Hashcode from name
     */
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    /**
     * Equal by name
     */
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            // Ugly with requied spaces before dot (.) but checkstyle require it
            return name.equals(((Assembly) obj) .name);
        }
        return false;
    }

    @Override
    /**
     * Sort default by name
     */
    public int compareTo(Assembly o) {
        return name.compareTo(o.name);
    }
}