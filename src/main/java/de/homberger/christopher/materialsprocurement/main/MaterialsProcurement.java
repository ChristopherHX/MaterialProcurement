package de.homberger.christopher.materialsprocurement.main;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * MaterialsProcurement
 * (never store an removed Assembly in a non weak reference outside, causes out of spec side effects)
 * @author Christopher Lukas Homberger
 * @version 0.9.2
 */
public class MaterialsProcurement {
    /**
     * Assemblies explicitly createtes strongrefs
     */
    private final Set<Assembly> assemblies = new HashSet<>();
    /**
     * Recycle unused Components automatically via gc (weakref)
     */
    private final Map<Assembly, Object> shadowassemblies = new WeakHashMap<>();

    /**
     * adds an Assembly as strong reference (which is not a component)
     * @param assembly to be added
     */
    public void addAssembly(Assembly assembly) {
        if (!assembly.isComponent()) {
            assemblies.add(assembly);
        }
    }

    /**
     * Returns registrated Assembly references by name
     * @param name of Assembly or Component
     * @return null if none with that name is registrated
     */
    public Assembly getAssembly(String name) {
        for (Assembly shadowassembly : shadowassemblies.keySet()) {
            if(shadowassembly != null && shadowassembly.getName().equals(name)) {
                return shadowassembly;
            }
        }
        return null;
    }

    /**
     * Removes an Assembly
     * @param assembly to remove from MaterialsProcurement and revert assembly to weakref Component
     */
	public void removeAssembly(Assembly assembly) {
        if (assembly.isComponent()) {
            assemblies.remove(assembly);
        } else {
            assembly.remove();
        }
	}

    /**
     * Register Assembly as Component with a weakref, called from Assembly constructor
     * @param assembly Component to add
     */
	public void registerComponent(Assembly assembly) {
        shadowassemblies.put(assembly, null);
	}
}