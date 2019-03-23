package de.homberger.christopher.materialsprocurement.ui.terminal.commands;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import de.homberger.christopher.materialsprocurement.main.Assembly;
import de.homberger.christopher.materialsprocurement.main.MaterialsProcurement;
import de.homberger.christopher.materialsprocurement.ui.terminal.CommandRegex;
import de.homberger.christopher.materialsprocurement.ui.terminal.resources.Localisation;
import de.homberger.christopher.ui.terminal.Command;

/**
 * RemovePartCommand
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class RemovePartCommand extends Command<MaterialsProcurement> {
    /**
     * Create this console command and compile regex
     */
    public RemovePartCommand() {
        super(Pattern.compile(CommandRegex.REMOVE_PART));
    }

    @Override
    public void invoke(MatchResult res, MaterialsProcurement procurement) {
        String name = res.group(1);
        Assembly assembly = procurement.getAssembly(name);
        if (assembly == null || assembly.isComponent()) {
            System.err.println(Localisation.BNE);
            return;
        }
        assembly = null;
        try {
            procurement.removePart(name, res.group(3), Integer.parseInt(res.group(2)));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println(Localisation.OK);
    }
}