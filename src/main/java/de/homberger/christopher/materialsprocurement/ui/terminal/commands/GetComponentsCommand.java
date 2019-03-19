package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.Map;
import java.util.SortedSet;
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
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class GetComponentsCommand extends Command<MaterialsProcurement> {
    /**
     * Create this console command and compile regex
     */
    public GetComponentsCommand() {
        super(Pattern.compile(CommandRegex.GET_COMPONENTS));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null || assembly.isComponent()) {
            Terminal.printError("BOM not exists");
            return;
        }
        Map<Assembly, Integer> assemblies = assembly.getDeepComponents();
        if (assemblies.isEmpty()) {
            Terminal.printError("EMPTY");
        } else {
            StringBuilder builder = new StringBuilder();
            SortedSet<Entry<Assembly, Integer>> sortedassemblies = GetAssembliesCommand.CreateKeyValueSort();
            sortedassemblies.addAll(assemblies.entrySet());
            for (Entry<Assembly, Integer> entry : sortedassemblies) {
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