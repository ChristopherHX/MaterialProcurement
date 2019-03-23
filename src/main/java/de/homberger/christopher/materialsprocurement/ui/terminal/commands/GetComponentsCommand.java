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
import de.homberger.christopher.materialsprocurement.ui.terminal.resources.Localisation;
import de.homberger.christopher.materialsprocurement.ui.terminal.util.KeyValueSort;
import de.homberger.christopher.ui.terminal.Command;

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
            System.err.println(Localisation.BNE);
            return;
        }
        Map<Assembly, Integer> assemblies = assembly.getDeepComponents();
        if (assemblies.isEmpty()) {
            System.err.println(Localisation.EMPTY + ", inconsistant database");
        } else {
            StringBuilder builder = new StringBuilder();
            SortedSet<Entry<Assembly, Integer>> sortedassemblies = new TreeSet<>(new KeyValueSort());
            sortedassemblies.addAll(assemblies.entrySet());
            for (Entry<Assembly, Integer> entry : sortedassemblies) {
                if (builder.length() != 0) {
                    builder.append(CommandRegex.ASSEMBLY_LIST_SEPERRATOR);
                }
                builder.append(entry.getKey().getName());
                builder.append(CommandRegex.ASSEMBLY_UNIT_SEPERRATOR);
                builder.append(entry.getValue());
            }
            System.out.println(builder.toString());
        }
    }
}