package fr.pops.jsonparser;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface Recordable {

    /**
     * Save the input json object to file
     * If the file already exists, its is recreated
     * @param object The json object to save
     * @param file The file to write
     */
    public default void saveToFile(JSONObject object, File file){
        // Initialization
        boolean deleted = false;
        boolean created = false;

        // If the file already exists, delete it
        if (file.exists()){
            deleted = file.delete();
        }

        // Create the file
        try {
            created = file.createNewFile();
            if (!file.canWrite()){
                boolean isWritable = file.setWritable(true);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(object.toString());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save the input json object to file
     * @param object The json object to save
     * @param path The file to write
     */
    public default void saveToFile(JSONObject object, String path){
        // Create the file
        try {
            FileWriter fileWriter = new FileWriter(new File(path));
            fileWriter.write(object.toString(4));
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Load the input json object from file
     * @param path The file to read
     */
    public default JSONObject readFromFile(String path){
        // Create the json object
        JSONObject o = null;
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            if (!content.isEmpty()){
                o = new JSONObject(content);
            }
        } catch (IOException ignored){}
        return o;
    }

    /**
     * Cast the instance of the object into a JSONObject
     */
    public JSONObject record();

    /**
     * Load JSONObject
     */
    public void load(JSONObject jsonObject);
}
