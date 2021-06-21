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
 * Name: EnumCst.java
 *
 * Description: Abstract class storing Constant Enum values.
 *
 * Author: Charles MERINO
 *
 * Date: 03/03/2021
 *
 ******************************************************************************/
package fr.pops.cst;

public abstract class EnumCst {

    /*****************************************
     *
     * View
     *
     *****************************************/
    public enum Views { MAIN,  NETWORK_INFO, STOCK, NEURAL_NETWORK }

    /*****************************************
     *
     * View
     *
     *****************************************/
    public enum ModelSteppingFamily { FAMILY_1_ON_1, FAMILY_1_ON_10, FAMILY_1_ON_20, FAMILY_1_ON_100, FAMILY_1_ON_500, FAMILY_1_ON_1000 }

    /*****************************************
     *
     * Components
     *
     *****************************************/
    public enum ComponentCategories { NONE, INPUTS, LAYERS }

    public enum ComponentTypes {
        DRAG(ComponentCategories.NONE, "Drag"),
        INPUT_LOCAL(ComponentCategories.INPUTS, "Local"),
        LAYER_DENSE(ComponentCategories.LAYERS, "Dense"),
        LAYER_CONVOLUTION(ComponentCategories.LAYERS, "Convolution"),
        LAYER_POOLING(ComponentCategories.LAYERS, "Pooling"),
        LAYER_LSTM(ComponentCategories.LAYERS, "LSTM");

        // Attribute
        ComponentCategories category;
        String str;

        // Ctor
        private ComponentTypes(ComponentCategories category, String str){
            this.category = category;
            this.str = str;
        }

        // Method
        @Override
        public String toString(){
            return this.str;
        }

        // Getter
        public ComponentCategories getCategory() {
            return this.category;
        }
    }

    /*****************************************
     *
     * Operations
     *
     *****************************************/
    public enum ListViewOps { ADD, REMOVE }
}
