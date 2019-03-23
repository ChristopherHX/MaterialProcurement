package de.homberger.christopher.ui.terminal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.regex.Pattern;

import de.homberger.christopher.ui.terminal.Command;
import de.homberger.christopher.ui.terminal.CommandRegex;
import de.homberger.christopher.ui.terminal.resources.Localisation;

/**
 * Starts the Terminal UI with its main loop
 * 
 * @author Christopher Lukas Homberger
 * @version 0.9.2
 * @param T Type of shared app main logic shared across Commands
 */
public final class ConsoleApp<T> {
    private final Collection<Command<T>> commands;

    /**
     * Creates an Console app with specfied commands
     * 
     * @param commands Collection of callable commands beyond integratet quit
     */
    public ConsoleApp(Collection<Command<T>> commands) {
        this.commands = commands;
    }

    /**
     * Starts the terminal user interface and handles the Terminal input
     * 
     * @param app instance of shared logic of the console application
     */
    public void run(T app) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("utf8")));
        // Compiles the quit regex to speed up matching
        Pattern quit = Pattern.compile(CommandRegex.QUIT_PATTERN);
        // Label to break the inner loop and continue the mainloop
        mainloop: while (true) {
            // wait for next command
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
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
            System.err.println(Localisation.INVALID_COMMAND_OR_ARGUMENT);
        }
    }
}
