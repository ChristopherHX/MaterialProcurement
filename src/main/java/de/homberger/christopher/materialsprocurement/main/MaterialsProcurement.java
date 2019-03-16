package de.homberger.christopher.materialsprocurement.main;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * MaterialsProcurement
 */
public class MaterialsProcurement {
    Set<Assembly> assemblies = new HashSet<>();
    Map<Assembly, Object> shadowassemblies = new WeakHashMap<>();

    public void addAssembly(Assembly assembly) {
        assemblies.add(assembly);
        for (Assembly cassembly : assembly.getAssemblies().keySet()) {
            shadowassemblies.put(cassembly, null);
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
        assembly.getAssemblies().clear();
        assemblies.remove(assembly);
	}
}