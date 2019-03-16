package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.Map;
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
 * 
 */
public class AddAssemblyCommand extends Command<MaterialsProcurement> {
    private final Pattern ASSEMBLY_UNIT;

    public AddAssemblyCommand() {
        super(Pattern.compile(CommandRegex.ADD_ASSEMBLY));
        ASSEMBLY_UNIT = Pattern.compile(CommandRegex.ASSEMBLY_UNIT);
    }

    public static boolean checkCycle(Assembly assembly, Assembly cAssembly) {
        if (assembly.equals(cAssembly)) {
            return true;
        }
        for (Assembly assembly3 : cAssembly.getAssemblies().keySet()) {
            if (checkCycle(assembly, assembly3)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement game) {
        String name = res.group(1);
        Assembly assembly = game.getAssembly(name);
        if (assembly == null) {
            assembly = new Assembly(name);
        } else if (!assembly.getAssemblies().isEmpty()) {
            Terminal.printError("BOM already exists");
            return;
        }
        Matcher matcher = ASSEMBLY_UNIT.matcher(res.group(2));
        Map<Assembly, Integer> assemblies = assembly.getAssemblies();
        while (matcher.find()) {
            String name2 = matcher.group(2);
            Assembly assembly2 = game.getAssembly(name2);
            if (assembly2 == null) {
                assembly2 = new Assembly(name2);
            }
            if (checkCycle(assembly, assembly2)) {
                Terminal.printError("You are trying to create a cycle");
                return;
            }
            if (assemblies.put(assembly2, Integer.parseInt(matcher.group(1))) != null) {
                Terminal.printError("Duplicated Parameter");
                return;
            }  
        }
        game.addAssembly(assembly);
        Terminal.printLine("OK");
    }
}