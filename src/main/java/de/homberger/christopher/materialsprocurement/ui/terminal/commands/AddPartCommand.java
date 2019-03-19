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
public class AddPartCommand extends Command<MaterialsProcurement> {
    /**
     * Create this console command and compile regex
     */
    public AddPartCommand() {
        super(Pattern.compile(CommandRegex.ADD_PART));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null || assembly.isComponent()) {
            Terminal.printError("BOM not exists");
            return;
        }
        try {
            assembly.addPart(res.group(3), Integer.parseInt(res.group(2)));
        } catch (IllegalArgumentException e) {
            Terminal.printError(e.getMessage());
            return;
        }
        Terminal.printLine("OK");
    }
}