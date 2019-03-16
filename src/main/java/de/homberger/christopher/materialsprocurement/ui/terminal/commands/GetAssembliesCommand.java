package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
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
public class GetAssembliesCommand extends Command<MaterialsProcurement> {
    public GetAssembliesCommand() {
        super(Pattern.compile(CommandRegex.GET_ASSEMBLY));
    }

    /**
     * Creates a KeyValue Sorted TreeSet for sorted output
     */
    public static SortedSet<Entry<Assembly, Integer>> CreateKeyValueSort() {
        return new TreeSet<Entry<Assembly, Integer>>((Entry<Assembly, Integer> l, Entry<Assembly, Integer> r) -> {
            int k = r.getValue().compareTo(l.getValue());
            return k == 0 ? l.getKey().compareTo(r.getKey()) : k;
        });
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement game) {
        String name = res.group(1);
        Assembly assembly = game.getAssembly(name);
        if (assembly == null || assembly.getAssemblies().isEmpty()) {
            Terminal.printError("BOM not exists");
            return;
        }
        Map<Assembly, Integer> assemblies = assembly.getDeepAssemblies();
        if (assemblies.isEmpty()) {
            Terminal.printLine("EMPTY");
        } else {
            StringBuilder builder = new StringBuilder();
            SortedSet<Entry<Assembly, Integer>> sortedassemblies = CreateKeyValueSort();
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