package fr.pops.utils;

import fr.pops.math.PopsMath;
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

    /*****************************************
     *
     * Colors
     *
     *****************************************/
    /**
     * Cast color from hexa format to double array
     * @param color The color in hexa format
     * @return The color in a double rgb array
     */
    public static double[] hexToDoubleColor(String color){
        return new double[]{
            ((double) Integer.parseUnsignedInt(color.substring(0, 2), 16)) / 255,
            ((double) Integer.parseUnsignedInt(color.substring(2, 4), 16)) / 255,
            ((double) Integer.parseUnsignedInt(color.substring(4, 6), 16)) / 255
        };
    }

    /**
     * Get a color from a color ramp
     * @param start Starting color
     * @param end Ending color
     * @param linearCoeff A value between 0 & 1 defining the position of the cursor
     *                    in the interpolation between the starting color and the
     *                    ending color
     */
    public static double[] getColorFromRamp(String start, String end, double linearCoeff){
        double[] col0 = Utils.hexToDoubleColor(start);
        double[] col1 = Utils.hexToDoubleColor(end);

        return new double[]{
                PopsMath.lerp(col0[0], col1[0], linearCoeff),
                PopsMath.lerp(col0[1], col1[1], linearCoeff),
                PopsMath.lerp(col0[2], col1[2], linearCoeff),
        };

    }
}
