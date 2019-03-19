package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.homberger.christopher.materialsprocurement.main.Assembly;
import de.homberger.christopher.materialsprocurement.main.MaterialsProcurement;
import de.homberger.christopher.materialsprocurement.ui.terminal.CommandRegex;
import de.homberger.christopher.ui.terminal.Command;
import edu.kit.informatik.Terminal;

/**
 * AddAssemblyCommand
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class AddAssemblyCommand extends Command<MaterialsProcurement> {
    /**
     * Assembly unit scheme to iterate over the arguments
     */
    private final Pattern ASSEMBLY_UNIT;

    /**
     * Create this console command and compile regex
     */
    public AddAssemblyCommand() {
        super(Pattern.compile(CommandRegex.ADD_ASSEMBLY));
        ASSEMBLY_UNIT = Pattern.compile(CommandRegex.ASSEMBLY_UNIT);
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null) {
            assembly = new Assembly(procurement, name);
        } else if (!assembly.isComponent()) {
            Terminal.printError("BOM already exists");
            return;
        }
        Matcher matcher = ASSEMBLY_UNIT.matcher(res.group(2));
        try {
            while (matcher.find()) {
                String name2 = matcher.group(2);
                if (assembly.containsPart(name2)) {
                    Terminal.printError("Duplicated Parameter");
                    assembly.remove();
                    return;
                }
                assembly.addPart(name2, Integer.parseInt(matcher.group(1)));
            }
        } catch (IllegalArgumentException e) {
            // Print Error and remove / revert the assembly to Component
            Terminal.printError(e.getMessage());
            assembly.remove();
            return;
        }
        procurement.addAssembly(assembly);
        Terminal.printLine("OK");
    }
}