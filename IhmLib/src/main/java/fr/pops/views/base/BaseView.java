/*******************************************************************************
 *
 *                         PPPP     OOOO     PPPP    SSSS
 *                        PP  PP   OO  OO   PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP   SSSS
 *                        PPPP    OO    OO  PPPP        SS
 *                        PP       OO  OO   PP          SS
 *                        PP        OOOO    PP       SSSS
 *
 * Name: BaseView.java
 *
 * Description: Abstract class defining the basic structure for the views
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.views.base;

import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.jsonparser.IRecordable;
import fr.pops.utils.Utils;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseView<controllerT extends BaseController<?,?>> implements IRecordable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // General
    private String name;
    private EnumCst.Views type;
    protected controllerT controller;

    // Basic components
    protected Stage stage;
    protected Region root;

    // Load / save
    protected String fieldsName = "fields";

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected BaseView() {
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     * @param name Name of the view, used in misc occasions
     */
    protected BaseView(Stage stage, String name, EnumCst.Views type){
        // Initialization
        this.stage = stage;
        this.name = name;
        this.type = type;

        // Configure the root layout
        this.configureRoot();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Configure the rootPane
     */
    protected abstract void configureRoot();

    /**
     * Initialize the view
     */
    protected abstract void onInit();

    /**
     * Configure the rootPane
     */
    protected abstract void configureContentPane();

    /**
     * Build the hierarchy of the view
     */
    protected abstract void buildHierarchy();

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Root pane
     * @return The root node of the view
     */
    public Region getRoot(){
        return this.root;
    }

    /**
     * @return Name of the view
     */
    public String getName() {
        return this.name;
    }

    /*****************************************
     *
     * Record / Load
     *
     *****************************************/
    /**
     * Cast the instance of the object into a JSONObject
     */
    @Override
    public final JSONObject record() {
        // Initialization
        Map<String, Object> topBrace = new LinkedHashMap<>();

        // General parameters
        topBrace.put(StrCst.JSON_KEY_CLASS, Utils.stripClassName(this.getClass()));
        topBrace.put(StrCst.JSON_KEY_TYPE, this.type);

        // Transform children
        Map<String, Object> fieldsBrace = this.viewToJsonMap();
        if (fieldsBrace != null){
            topBrace.put(this.fieldsName, fieldsBrace);
        }

        // Return created object
        return new JSONObject(topBrace);
    }

    /**
     * Load JSONObject
     * The json object load from file
     */
    @Override
    public void load(JSONObject jsonObject) {
        this.jsonToView(jsonObject.toMap());
    }

    /**
     * Map the view to json format
     * @return The mapping between object fields and json fields
     */
    protected Map<String, Object> viewToJsonMap(){
        return null;
    }

    /**
     * Cast the json object stored in the fields
     * to a view
     */
    public void jsonToView(Map<String, Object> map){
        // Read top brace
        this.readTopBrace(map);
    }

    /**
     * Cast the json object stored in the fields
     * to a view
     */
    protected final void readTopBrace(Map<String, Object> topBrace){

        // Loop over top brace's fields
        for (String s : topBrace.keySet()){
            if (s.equals(this.fieldsName)){
                Map<String, Object> fields = (Map<String, Object>) topBrace.get(s);
                this.readFields(fields);
            }
        }
    }

    /**
     * Read the fields stored in the json
     * @param fields The fields to read
     */
    protected abstract void readFields(Map<String, Object> fields);

}
