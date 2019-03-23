package de.homberger.christopher.materialsprocurement.ui.terminal;

/**
 * CommandRegex / read from spec
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class CommandRegex {
    /**
     * Name of Assemblies
     */
    public static final String ASSEMBLY_NAME = "([a-zA-Z]+)";

    /**
     * Amount of assemblies (limited to max 1000 by spec (no technical issue))
     */
    public static final String ASSEMBLY_AMOUNT = "(1000|[0-9]{1,3})";

    /**
     * Symbol to seperate amount from name
     */
    public static final String ASSEMBLY_UNIT_SEPERRATOR = ":";

    /**
     * Regex part with bundled amount and name
     */
    public static final String ASSEMBLY_UNIT = ASSEMBLY_AMOUNT + ASSEMBLY_UNIT_SEPERRATOR + ASSEMBLY_NAME;

    /**
     * Symbol to seperate list of assembly units
     */
    public static final String ASSEMBLY_LIST_SEPERRATOR = ";";


    /**
     * Add Assembly command like spec
     */
    public static final String ADD_ASSEMBLY = "addAssembly " + ASSEMBLY_NAME
    + "=(" + ASSEMBLY_UNIT + "(" + ASSEMBLY_LIST_SEPERRATOR + ASSEMBLY_UNIT + ")*)";
  
    /**
     * Remove Assembly command like spec
     */
    public static final String REMOVE_ASSEMBLY = "removeAssembly " + ASSEMBLY_NAME;

    /**
     * Print Assembly command like spec
     */
    public static final String PRINT_ASSEMBLY = "printAssembly " + ASSEMBLY_NAME;

    /**
     * get Assemblies command like spec
     */
    public static final String GET_ASSEMBLY = "getAssemblies " + ASSEMBLY_NAME;

    /**
     * get Components command like spec
     */
    public static final String GET_COMPONENTS = "getComponents " + ASSEMBLY_NAME;

    /**
     * add Part command like spec
     */
    public static final String ADD_PART = "addPart " + ASSEMBLY_NAME + "\\+" + ASSEMBLY_UNIT;

    /**
     * remove Part command like spec
     */
    public static final String REMOVE_PART = "removePart " + ASSEMBLY_NAME + "-" + ASSEMBLY_UNIT;
}