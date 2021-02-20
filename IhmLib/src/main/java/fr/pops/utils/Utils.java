package fr.pops.utils;

public abstract class Utils {

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Get the resource found in the resources folder
     * @param path The path of the resources starting from the root folder
     *             inside the resources folder with "/"
     * @return The complete path of the resource
     */
    public static String getResource(String path) {
        return Utils.class.getResource(path).toString();
    }


}
