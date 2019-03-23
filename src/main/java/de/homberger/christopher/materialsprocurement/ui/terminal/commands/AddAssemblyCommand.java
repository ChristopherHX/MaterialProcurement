package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.homberger.christopher.materialsprocurement.main.Assembly;
import de.homberger.christopher.materialsprocurement.main.MaterialsProcurement;
import de.homberger.christopher.materialsprocurement.ui.terminal.CommandRegex;
import de.homberger.christopher.materialsprocurement.ui.terminal.resources.Localisation;
import de.homberger.christopher.ui.terminal.Command;

/**
 * AddAssemblyCommand
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class AddAssemblyCommand extends Command<MaterialsProcurement> {
    /**
     * Assembly unit scheme to iterate over the arguments
     */
    private final Pattern assemblyUnit;

    /**
     * Create this console command and compile regex
     */
    public AddAssemblyCommand() {
        super(Pattern.compile(CommandRegex.ADD_ASSEMBLY));
        assemblyUnit = Pattern.compile(CommandRegex.ASSEMBLY_UNIT);
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null) {
            // No referenced component found create a new one
            assembly = new Assembly(procurement, name);
        } else if (!assembly.isComponent()) {
            System.err.println(Localisation.BAE);
            return;
        }
        procurement.addAssembly(assembly);
        Matcher matcher = assemblyUnit.matcher(res.group(2));
        try {
            // Iterate over all child-assemblies
            while (matcher.find()) {
                String name2 = matcher.group(2);
                if (assembly.containsPart(name2)) {
                    System.err.println(Localisation.DUPPARAM);
                    assembly = null;
                    procurement.removeAssembly(name);
                    return;
                }
                procurement.addPart(name, name2, Integer.parseInt(matcher.group(1)));
            }
        } catch (IllegalArgumentException e) {
            // Print Error and remove / revert the assembly to Component
            System.err.println(e.getMessage());
            assembly = null;
            procurement.removeAssembly(name);
            return;
        }
        System.out.println(Localisation.OK);
    }
}