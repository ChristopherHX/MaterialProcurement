package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

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
public class AddPartCommand extends Command<MaterialsProcurement> {
    public AddPartCommand() {
        super(Pattern.compile(CommandRegex.ADD_PART));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement game) {
        String name = res.group(1);
        Assembly assembly = game.getAssembly(name);
        if (assembly == null || assembly.getAssemblies().isEmpty()) {
            Terminal.printError("BOM not exists");
            return;
        }
        int amount = Integer.parseInt(res.group(2));
        String pname = res.group(3);
        Assembly pAssembly = null;
        for (Entry<Assembly, Integer> entry : assembly.getAssemblies().entrySet()) {
            if (entry.getKey().getName().equals(pname)) {
                pAssembly = entry.getKey();
                amount += entry.getValue();
                break;
            }
        }
        if (pAssembly == null) {
            if((pAssembly = game.getAssembly(pname)) == null) {
                Terminal.printError("Unknown part");
                return;
            }
            if (AddAssemblyCommand.checkCycle(assembly, pAssembly)) {
                Terminal.printError("You are trying to create a cycle");
                return;
            }
        }
        assembly.getAssemblies().put(pAssembly, amount);
        Terminal.printLine("OK");
    }
}