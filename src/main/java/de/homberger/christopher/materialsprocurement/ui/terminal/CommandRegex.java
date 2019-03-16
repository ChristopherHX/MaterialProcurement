package de.homberger.christopher.materialsprocurement.ui.terminal;

/**
 * CommandRegex
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class CommandRegex {
    /**
     * 
     */
    public static final String ASSEMBLY_NAME = "([a-zA-Z]+)";

    /**
     * 
     */
    public static final String ASSEMBLY_AMOUNT = "(1000|[0-9]{1,3})";

    /**
     * 
     */
    public static final String ASSEMBLY_UNIT = ASSEMBLY_AMOUNT + ":" + ASSEMBLY_NAME;


    /**
     * 
     */
    public static final String ADD_ASSEMBLY = "addAssembly " + ASSEMBLY_NAME + "=(" + ASSEMBLY_UNIT + "(;" + ASSEMBLY_UNIT + ")*)";
  
    /**
     * 
     */
    public static final String REMOVE_ASSEMBLY = "removeAssembly " + ASSEMBLY_NAME;

    /**
     * 
     */
    public static final String PRINT_ASSEMBLY = "printAssembly " + ASSEMBLY_NAME;

    /**
     * 
     */
    public static final String GET_ASSEMBLY = "getAssemblies " + ASSEMBLY_NAME;

    /**
     * 
     */
    public static final String GET_COMPONENTS = "getComponents " + ASSEMBLY_NAME;

    /**
     * 
     */
    public static final String ADD_PART = "addPart " + ASSEMBLY_NAME + "\\+" + ASSEMBLY_UNIT;

    /**
     * 
     */
    public static final String REMOVE_PART = "removePart " + ASSEMBLY_NAME + "-" + ASSEMBLY_UNIT;
}