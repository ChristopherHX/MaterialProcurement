package de.homberger.christopher.materialsprocurement.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * MaterialsProcurement
 */
public class MaterialsProcurement {
    Set<Assembly> assemblies = new HashSet<>();
    Map<Assembly, Integer> shadowassemblies = new HashMap<>();

    public void addAssembly(Assembly assembly) {
        assemblies.add(assembly);
        for (Assembly cassembly : assembly.getAssemblies().keySet()) {
            Integer i = shadowassemblies.get(cassembly);
            shadowassemblies.put(cassembly, (i == null ? 0 : i) + 1);
        }
    }

    public Assembly getAssembly(String name) {
        for (Assembly assembly : assemblies) {
            if(assembly.getName().equals(name)) {
                return assembly;
            }
        }
        for (Assembly shadowassembly : shadowassemblies.keySet()) {
            if(shadowassembly != null && shadowassembly.getName().equals(name)) {
                return shadowassembly;
            }
        }
        return null;
    }

	public void removeAssembly(Assembly assembly) {
        for (Assembly assembly2 : assembly.getAssemblies().keySet()) {
            removeShadowRef(assembly2);
        }
        assembly.getAssemblies().clear();
        assemblies.remove(assembly);
	}

    public void removeShadowRef(Assembly assembly2) {
        Integer i = shadowassemblies.get(assembly2);
        int refs = (i == null ? 0 : i) - 1;
        if (refs < 1) {
            shadowassemblies.remove(assembly2);
        } else {
            shadowassemblies.put(assembly2, refs);
        }
    }
}