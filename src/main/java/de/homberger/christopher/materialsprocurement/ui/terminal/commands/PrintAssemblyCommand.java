package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
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
public class PrintAssemblyCommand extends Command<MaterialsProcurement> {
    public PrintAssemblyCommand() {
        super(Pattern.compile(CommandRegex.PRINT_ASSEMBLY));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement game) {
        String name = res.group(1);
        Assembly assembly = game.getAssembly(name);
        if (assembly == null/*  || assembly.getAssemblies().isEmpty() not so strict*/) {
            Terminal.printError("BOM not exists");
            return;
        }
        Map<Assembly, Integer> assemblies = assembly.getAssemblies();
        if (assemblies.isEmpty()) {
            Terminal.printLine("COMPONENT");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Entry<Assembly, Integer> entry : assemblies.entrySet()) {
                if (builder.length() != 0) {
                    builder.append(";");
                }
                builder.append(entry.getKey().getName());
                builder.append(":");
                builder.append(entry.getValue());
            }
            Terminal.printLine(builder.toString());
        }
    }
}