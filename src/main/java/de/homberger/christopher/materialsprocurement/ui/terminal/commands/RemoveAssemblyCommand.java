package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

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
public class RemoveAssemblyCommand extends Command<MaterialsProcurement> {
    public RemoveAssemblyCommand() {
        super(Pattern.compile(CommandRegex.REMOVE_ASSEMBLY));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement game) {
        String name = res.group(1);
        Assembly assembly = game.getAssembly(name);
        if (assembly == null || assembly.getAssemblies().isEmpty()) {
            Terminal.printError("BOM not exists");
            return;
        }
        game.removeAssembly(assembly);
        Terminal.printLine("OK");
    }

}