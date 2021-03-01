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
 * Name: MainController.java
 *
 * Description: Main class to control the main window.
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 13/02/2021
 *
 ******************************************************************************/
package fr.pops.controllers.main;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private final static MainController instance = new MainController();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * MainController is a singleton
     */
    private MainController(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Events
     *
     *****************************************/

    /*****************************************
     *
     * Actions
     *
     *****************************************/
    /**
     * Triggers the closing of the window which contains the button calling it
     * @param actionEvent The closing action event
     */
    public void closeWindow(ActionEvent actionEvent){
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Triggers minimizing the window which contains the button calling it
     * @param actionEvent The minimizing action event
     */
    public void minimizeWindow(ActionEvent actionEvent){
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * This class is a singleton
     * @return The only instance of the object
     */
    public static MainController getInstance(){
        return instance;
    }
}
