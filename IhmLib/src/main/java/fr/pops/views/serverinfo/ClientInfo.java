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
 * Name: ClientInfo.java
 *
 * Description: Class defining the node containing the client info displayed
 *              by the listview on the ServerInfoView
 *
 * Author: Charles MERINO
 *
 * Date: 02/05/2021
 *
 ******************************************************************************/
package fr.pops.views.serverinfo;

import fr.pops.sockets.cst.EnumCst;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ClientInfo extends HBox {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    public static final ClientInfo HEADER = new ClientInfo("Clients");
    private boolean isHeader = false;
    private EnumCst.ClientTypes type;
    private Label typeLabel;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private ClientInfo(){
        // Nothing to be done
    }

    /**
     * Ctor to display a header for the client info list view
     * @param type The name of of the Type column
     */
    private ClientInfo(String type){
        // Define it as a header
        this.isHeader = true;

        // Type column
        this.typeLabel = new Label(type);

        // Build hierarchy
        this.getChildren().add(this.typeLabel);
    }

    /**
     * Ctor
     * @param type The client's type
     */
    public ClientInfo(EnumCst.ClientTypes type){
        // Initialize info
        this.onInit(type);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the component
     * @param type The client's type
     */
    private void onInit(EnumCst.ClientTypes type){
        // Type
        this.type = type;
        this.typeLabel = new Label(this.type.name());

        // Build hierarchy
        this.getChildren().add(this.typeLabel);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The client's type
     */
    public EnumCst.ClientTypes getType() {
        return this.type;
    }

    /**
     * @return True if this object is a header of a list view
     */
    public boolean isHeader() {
        return this.isHeader;
    }
}
