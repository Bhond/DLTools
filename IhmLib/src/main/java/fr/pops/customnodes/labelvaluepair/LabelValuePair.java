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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LabelValuePair extends HBox {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    public enum ORIENTATION { HORIZONTAL, VERTICAL }

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Content
    private HBox horizontalContentBox;
    private VBox verticalContentBox;
    private Label label;
    private Label value;
    private ORIENTATION orientation = ORIENTATION.HORIZONTAL;

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
    public LabelValuePair(String text, double value) {
        this(text, String.valueOf(value));
    }

    /**
     * Ctor
     * @param text The starting text of the pair
     * @param value The starting value of the pair
     */
    public LabelValuePair(String text, int value) {
        this(text, String.valueOf(value));
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
        this.horizontalContentBox = new HBox();
        this.horizontalContentBox.getStyleClass().add(StrCst.STYLE_CLASS_HBOX);
        this.horizontalContentBox.setSpacing(10);
        HBox.setHgrow(this.horizontalContentBox, Priority.NEVER);

        this.verticalContentBox = new VBox();
        this.verticalContentBox.getStyleClass().add(StrCst.STYLE_CLASS_VBOX);
        this.verticalContentBox.setSpacing(10);
        HBox.setHgrow(this.verticalContentBox, Priority.NEVER);

        // Label
        this.label = new Label(text);
        this.label.getStyleClass().add(StrCst.STYLE_CLASS_LABEL_PAIR);
        HBox.setHgrow(this.label, Priority.ALWAYS);

        // Value
        this.value = new Label(value);
        this.value.getStyleClass().add(StrCst.STYLE_CLASS_VALUE_PAIR);
        HBox.setHgrow(this.value, Priority.ALWAYS);

        // Hierarchy
        this.buildHierarchy();
    }

    private void buildHierarchy(){
        Pane contentBox = this.horizontalContentBox;
        this.getChildren().clear();

        switch (this.orientation){
            case HORIZONTAL:
                this.verticalContentBox.getChildren().clear();
                contentBox = this.horizontalContentBox;
                break;
            case VERTICAL:
                this.horizontalContentBox.getChildren().clear();
                contentBox = this.verticalContentBox;
                break;
        }
        contentBox.getChildren().addAll(this.label, this.value);
        this.getChildren().add(contentBox);
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
     * @param value The value to display
     */
    public void setValue(String value){
        this.value.setText(value);
    }

    /**
     * Set the value of the pair
     * @param value The value to display
     */
    public void setValue(int value){
        this.value.setText(String.valueOf(value));
    }

    /**
     * Set the value of the pair
     * @param value The value to display
     */
    public void setValue(double value){
        this.value.setText(String.valueOf(value));
    }

    /**
     * Set the orientation of the display
     * @param orientation The orientation of the display
     */
    public void setOrientation(ORIENTATION orientation){
        if (orientation != this.orientation){
            this.orientation = orientation;
            this.buildHierarchy();
        }
    }
}
