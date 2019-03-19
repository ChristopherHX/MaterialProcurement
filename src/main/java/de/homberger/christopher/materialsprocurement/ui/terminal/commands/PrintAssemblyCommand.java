package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import de.homberger.christopher.materialsprocurement.main.Assembly;
import de.homberger.christopher.materialsprocurement.main.MaterialsProcurement;
import de.homberger.christopher.materialsprocurement.ui.terminal.CommandRegex;
import de.homberger.christopher.materialsprocurement.ui.terminal.resources.Localisation;
import de.homberger.christopher.ui.terminal.Command;
import edu.kit.informatik.Terminal;

/**
 * AddAssemblyCommand
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class PrintAssemblyCommand extends Command<MaterialsProcurement> {
    /**
     * Create this console command and compile regex
     */
    public PrintAssemblyCommand() {
        super(Pattern.compile(CommandRegex.PRINT_ASSEMBLY));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null) {
            Terminal.printError(Localisation.NOE);
        } else if (assembly.isComponent()) {
            Terminal.printLine(Localisation.COMPONENT);
        } else {
            StringBuilder builder = new StringBuilder();
            for (Entry<Assembly, Integer> entry : assembly.getAssemblies().entrySet()) {
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