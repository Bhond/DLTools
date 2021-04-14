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

import fr.pops.cst.EnumCst;
import fr.pops.cst.IntCst;
import fr.pops.views.MainView;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private final static MainController instance = new MainController();

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private MainView mainView;

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
     * Actions
     *
     *****************************************/
    // Menu bar
    /**
     * Triggers the display of the server view
     * @param actionEvent The clicked action event
     */
    public void onServerViewMenuItemClicked(ActionEvent actionEvent){
        this.mainView.addView(EnumCst.VIEWS.SERVER);
    }

    /**
     * Triggers the display of the neural network view
     * @param actionEvent The clicked action event
     */
    public void onNeuralNetworkViewMenuItemClicked(ActionEvent actionEvent){
        this.mainView.addView(EnumCst.VIEWS.NEURAL_NETWORK);
    }

    /**
     * Triggers the display of the plot view
     * @param actionEvent The clicked action event
     */
    public void onPlotViewMenuItemClicked(ActionEvent actionEvent){
        this.mainView.addView(EnumCst.VIEWS.PLOT);
    }

    /**
     * Triggers the display of the test view
     * @param actionEvent The clicked action event
     */
    public void onTestViewMenuItemClicked(ActionEvent actionEvent){
        this.mainView.addView(EnumCst.VIEWS.TEST);
    }

    /**
     * Triggers the closing of the window which contains the button calling it
     * @param actionEvent The closing action event
     */
    public void onCloseWindow(ActionEvent actionEvent){
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Triggers minimizing the window which contains the button calling it
     * @param actionEvent The minimizing action event
     */
    public void onMinimizeWindow(ActionEvent actionEvent){
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Allows dragging the window
     * @param root The root to drag
     * @param stage The stage
     */
    public void onWindowDragged(AnchorPane root, Stage stage){
        root.setOnMousePressed(p -> root.setOnMouseDragged(dragEvent -> {
            if (p.getButton() == MouseButton.PRIMARY && p.getY() <= IntCst.DEFAULT_MENU_BAR_HEIGHT){
                stage.setX(dragEvent.getScreenX() - p.getSceneX());
                stage.setY(dragEvent.getScreenY() - p.getSceneY());
            }
        }));
    }

    /**
     * Allows resizing the window
     * TODO: Finish it
     * @param root The root to drag
     * @param stage The stage
     */
    public void onWindowResized(AnchorPane root, Stage stage){
        root.setOnMouseMoved(e -> {

            boolean isOnLRBorder = (e.getX() <= 2 || e.getX() >= root.getWidth() - 2);
            boolean isOnNSBorder = (e.getY() <= 2 || e.getY() >= root.getHeight() - 2);

            if (isOnLRBorder && !isOnNSBorder){
                stage.getScene().setCursor(Cursor.H_RESIZE);
            } else if (isOnNSBorder && !isOnLRBorder) {
                stage.getScene().setCursor(Cursor.V_RESIZE);
            } else {
                stage.getScene().setCursor(Cursor.DEFAULT);
            }

        });
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * This class is a singleton
     * @return The only instance of the object
     */
    public static MainController getInstance(){
        return instance;
    }

    /**
     * @return The main view
     */
    public MainView getMainView() {
        return mainView;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the main view
     * @param mainView The main view
     */
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}
