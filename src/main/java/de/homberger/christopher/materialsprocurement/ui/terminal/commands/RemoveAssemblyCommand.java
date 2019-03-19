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
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class RemoveAssemblyCommand extends Command<MaterialsProcurement> {
    /**
     * Create this console command and compile regex
     */
    public RemoveAssemblyCommand() {
        super(Pattern.compile(CommandRegex.REMOVE_ASSEMBLY));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null || assembly.isComponent()) {
            Terminal.printError("BOM not exists");
            return;
        }
        assembly.remove();
        Terminal.printLine("OK");
    }

}