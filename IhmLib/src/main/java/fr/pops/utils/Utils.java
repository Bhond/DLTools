package fr.pops.utils;

import org.json.JSONObject;

import java.util.Map;

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

    /**
     * Get the resource found in the resources folder
     * @param path The path of the resources starting from the root folder
     *             inside the resources folder with "/"
     * @return The complete path of the resource without the "file:/" prefix
     */
    public static String getResourceWithoutFilePrefix(String path) {
        return Utils.class.getResource(path).toString().substring(6);
    }

    /*****************************************
     *
     * String formatter
     *
     *****************************************/
    /**
     * @return The class's name stripped from the package's name
     */
    public static String stripClassName(Class classz){
        String[] fullName = classz.getName().split("\\.");
        return fullName[fullName.length-1]; // Return last
    }

    /*****************************************
     *
     * JSON utils
     *
     *****************************************/
    /**
     * Concatenate two json objects
     * @param jsonObject0 The first json object
     * @param jsonObject1 The second json object
     * @return The concatenation of the two input jsons
     */
    public static JSONObject concatenate(JSONObject jsonObject0, JSONObject jsonObject1){
        // Build map from json object
        Map<String, Object> map = jsonObject0.toMap();
        // Append second map to the first one
        map.putAll(jsonObject1.toMap());
        return new JSONObject(map);
    }

}
