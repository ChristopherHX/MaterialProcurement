package de.homberger.christopher.ui.terminal;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command super class of all terminal commands
 * @author Christopher Lukas Homberger
 * @version 0.9.2
 */
public abstract class Command<T> {
    private final Pattern pattern;

    /**
     * Create a new Command with a specfic regex pattern
     * @param pattern Regex pattern for the Command
     */
    public Command(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Try to match the Command and invokes it
     * @param line Terminal line input
     * @param game Game Object to be passed to invoke
     * @return true if it had mached and was invoked
     */
    public boolean tryInvoke(String line, T game) {
        Matcher match = pattern.matcher(line);
        if (match.matches()) {
            invoke(match.toMatchResult(), game);
            return true;
        }
        return false;
    }

    /**
     * Invokes the Command
     * @param res Parsed Arguments
     * @param game to change game state
     */
    public abstract void invoke(MatchResult res, T game);
}