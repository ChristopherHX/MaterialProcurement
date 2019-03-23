package de.homberger.christopher.materialsprocurement.ui.terminal.resources;

/**
 * Localisation of the console ui
 * @author Christopher Lukas Homberger
 * @version 0.9.0
 */
public class Localisation {

    /**
     * Command ended sucessfully
     */
    public static final String OK = "OK";
    
    /**
     * Tried to create a new bom without deleting this one
     */
    public static final String BAE = "BOM already exists";

    /**
     * Tried to do somthing without a boom, but require it
     */
    public static final String BNE = "BOM not exists";

    /**
     * Called with 2 or more equal parameters
     */
    public static final String DUPPARAM = "Duplicated Parameter";

    /**
     * Name for empty Assemblies
     */
    public static final String COMPONENT = "COMPONENT";

    /**
     * Assembly wasn't found
     */
    public static final String NOE = "Assembly / Component not exists";

    /**
     * No sub Assemblies
     */
    public static final String EMPTY = "EMPTY";
}