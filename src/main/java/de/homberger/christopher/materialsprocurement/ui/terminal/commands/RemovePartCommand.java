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
public class RemovePartCommand extends Command<MaterialsProcurement> {
    public RemovePartCommand() {
        super(Pattern.compile(CommandRegex.REMOVE_PART));
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
                amount = entry.getValue() - amount;
                if (amount < 0) {
                    Terminal.printError("Cannot remove more elements than it contains");
                }
                break;
            }
        }
        if (pAssembly == null) {
            Terminal.printError("Not Found");
            return;
        }
        if (amount == 0) {
            assembly.getAssemblies().remove(pAssembly);
            game.removeShadowRef(pAssembly);
        } else {
            assembly.getAssemblies().put(pAssembly, amount);
        }
        Terminal.printLine("OK");
    }
}