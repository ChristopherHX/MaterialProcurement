package de.homberger.christopher.materialsprocurement.main;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * MaterialsProcurement
 * (never store an removed Assembly in a non weak reference outside, causes out of spec side effects)
 * @author Christopher Lukas Homberger
 * @version 0.9.4
 */
public class MaterialsProcurement {
    /**
     * Assemblies explicitly createted strongrefs
     */
    private final Set<Assembly> assemblies = new HashSet<>();
    /**
     * Recycle unused Components automatically from map via gc (WeakHashMap) used as HashSet
     * without non null values
     */
    private final Map<Assembly, Object> shadowassemblies = new WeakHashMap<>();

    /**
     * adds an Assembly as strong reference
     * @param assembly to be added
     */
    public void addAssembly(Assembly assembly) {
        assemblies.add(assembly);
    }
    
    /**
     * Adds an Assembly new parts or remove them
     * 
     * @param assemblyname Assembly to add / remove parts
     * @param name Assembly / Component to add / remove
     * @param amount to add / remove
     * @throws IllegalArgumentException more objects was removed than exists
     */
    public void addPart(String assemblyname, String name, int amount) throws IllegalArgumentException {
        Assembly assembly = getAssembly(assemblyname);
        assembly.addPart(name, amount);
        // cleanup unused components
        assembly = null;
        System.gc();
    }

    /**
     * Returns registrated Assembly references by name
     * @param name of Assembly or Component
     * @return null if none with that name is registrated
     */
    public Assembly getAssembly(String name) {
        for (Assembly shadowassembly : shadowassemblies.keySet()) {
            if (shadowassembly != null && shadowassembly.getName().equals(name)) {
                return shadowassembly;
            }
        }
        return null;
    }

    /**
     * Removes Part effectivly addPart with negative amount
     * @param assemblyname Assembly to add / remove parts
     * @param name Assembly / Component to remove / add
     * @param amount to remove / add
     * @throws IllegalArgumentException more objects was removed than exists
     */
    public void removePart(String assemblyname, String name, int amount) throws IllegalArgumentException {
        addPart(assemblyname, name, -amount);
    }

    /**
     * Removes an Assembly
     * Do not remove Assemblies with strong references (cleanup not working fast enough)
     * @param name of assembly to remove from MaterialsProcurement and revert assembly to weakref Component
     */
    public void removeAssembly(String name) {
        Assembly assembly = getAssembly(name);
        if (!assembly.isComponent()) {
            assembly.removeAllParts();
        }
        assemblies.remove(assembly);
        // cleanup unused components
        assembly = null;
        System.gc();
    }

    /**
     * Register Assembly as Component with a weakref, called from Assembly constructor
     * @param assembly Component to add
     */
    public void registerComponent(Assembly assembly) {
        shadowassemblies.put(assembly, null);
    }
}