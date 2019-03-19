package de.homberger.christopher.materialsprocurement.ui.terminal;

import java.util.Arrays;

import de.homberger.christopher.materialsprocurement.main.MaterialsProcurement;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.AddAssemblyCommand;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.AddPartCommand;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.GetAssembliesCommand;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.GetComponentsCommand;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.PrintAssemblyCommand;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.RemoveAssemblyCommand;
import de.homberger.christopher.materialsprocurement.ui.terminal.commands.RemovePartCommand;
import de.homberger.christopher.ui.terminal.ConsoleApp;

/**
 * Entry point of the MaterialsProcurementApp
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public final class MaterialsProcurementApp {

    /**
     * Prohibit object creation from outside
     */
    private MaterialsProcurementApp() {
    }

    /**
     * Instance of the MaterialsProcurementApp main logic to share across the Commands
     * @param args The arguments of the program. ( unused )
     */
    public static void main(String[] args) {
        MaterialsProcurement materialsprocurement = new MaterialsProcurement();
        ConsoleApp<MaterialsProcurement> app = new ConsoleApp<MaterialsProcurement>(Arrays.asList(
        new AddAssemblyCommand(), new RemoveAssemblyCommand(), new PrintAssemblyCommand(),
        new GetAssembliesCommand(), new GetComponentsCommand(), new AddPartCommand(), new RemovePartCommand()));
        app.run(materialsprocurement);
    }
}
