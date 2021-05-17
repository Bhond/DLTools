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
 * Name: LabelValuePair.java
 *
 * Description: Class defining pairs of Label Value
 *              to display values with a label
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.labelvaluepair;

import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LabelValuePair extends HBox {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Content
    private HBox contentBox;
    private Label label;
    private Label value;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * The default text is: " "
     * The default value is 0
     */
    public LabelValuePair(){
        this("text", "0");
    }

    /**
     * Ctor
     * @param text The starting text of the pair
     * The default value is 0
     */
    public LabelValuePair(String text){
        this.ontInit(text, "0");
    }

    /**
     * Ctor
     * @param text The starting text of the pair
     * @param value The starting value of the pair
     */
    public LabelValuePair(String text, String value){
        this.ontInit(text, value);
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the node
     * @param text The starting text of the pair
     * @param value The starting value of the pair
     */
    private void ontInit(String text, String value){
        // Style sheet
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_LABEL_VALUE_PAIR_CSS));

        // Content
        this.contentBox = new HBox();
        this.contentBox.getStyleClass().add(StrCst.STYLE_CLASS_HBOX);
        this.contentBox.setSpacing(10);
        HBox.setHgrow(this.contentBox, Priority.NEVER);

        // Label
        this.label = new Label(text);
        this.label.getStyleClass().add(StrCst.STYLE_CLASS_LABEL_PAIR);
        HBox.setHgrow(this.label, Priority.ALWAYS);

        // Value
        this.value = new Label(value);
        this.value.getStyleClass().add(StrCst.STYLE_CLASS_VALUE_PAIR);
        HBox.setHgrow(this.value, Priority.ALWAYS);

        // Hierarchy
        this.contentBox.getChildren().addAll(this.label, this.value);
        this.getChildren().add(this.contentBox);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The text of the pair
     */
    public String getText() {
        return this.label.getText();
    }

    /**
     * @return The value of the pair
     */
    public String getValue() {
        return this.value.getText();
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the text of the pair
     * @param text The text to display
     */
    public void setText(String text){
        this.label.setText(text);
    }

    /**
     * Set the value of the pair
     * @param value Ther value to display
     */
    public void setValue(String value){
        this.value.setText(value);

    }
}
