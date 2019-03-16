package de.homberger.christopher.ui.terminal;

import java.util.Collection;
import java.util.regex.Pattern;

import de.homberger.christopher.ui.terminal.Command;
import de.homberger.christopher.ui.terminal.CommandRegex;
import de.homberger.christopher.ui.terminal.resources.Localisation;
import edu.kit.informatik.Terminal;

/**
 * Starts the Game with the Terminal UI
 * with its main loop
 * @author Christopher Lukas Homberger
 * @version 0.9.2
 */
public final class ConsoleApp<T> {
    private final Collection<Command<T>> commands;

    public ConsoleApp(Collection<Command<T>> commands) {
        this.commands = commands;
    }

    /**
     * Starts the terminal user interface
     * and handles the Terminal input
     * @param args The ignored arguments of this console application
     */
    public void run(T app) {
        // Compiles the quit regex to speed up matching
        Pattern quit = Pattern.compile(CommandRegex.QUIT_PATTERN);
        // Label to break the inner loop and continue the mainloop
        mainloop: while (true) {
            // wait for next command
            String line = Terminal.readLine();
            // Quit the programm if it matches the quit pattern
            if (quit.matcher(line).matches()) {
                return;
            }
            for (final Command<T> command : commands) {
                // Try to invoke the nth command with that line
                if (command.tryInvoke(line, app)) {
                    // Successfully invoked wait for next command
                    continue mainloop;
                }
            }
            // Prints error if no pattern matches (Invalid Command or Argument)
            Terminal.printError(Localisation.INVALID_COMMAND_OR_ARGUMENT);
        }
    }
}
