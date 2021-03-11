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
 * Name: ServerInfoView.java
 *
 * Description: Class defining the server view used to display
 *              info from the server
 *
 * Author: Charles MERINO
 *
 * Date: 15/02/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.client.Client;
import fr.pops.ihmlibcst.StrCst;
import fr.pops.utils.Utils;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class ServerInfoView extends View {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public ServerInfoView(Stage stage, double height, double width){
        // Parent
        super(stage, height, width);

        // Initialization
        this.onInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the view
     */
    @Override
    protected void onInit(){
        // Set root stylesheet
        // Root pane
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_SERVER_INFO_VIEW_CSS));

        // Content
        this.configureContentPane();
    }

    /**
     * Build the content pane
     */
    @Override
    protected void configureContentPane(){

        /** TEMP **/
        Button test = new Button("This is a test");
        test.setOnAction(actionEvent -> Client.getInstance().sendMessageToServer("Clicked"));
        this.rootLayout.getChildren().add(test);

        Sphere sphere = new Sphere();
        sphere.radiusProperty().set(10);
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(Color.RED);
        sphere.materialProperty().setValue(mat);
        sphere.setTranslateX( - 50);
        sphere.setTranslateY( - 50);

        this.rootLayout.getChildren().add(sphere);
        /** TEMP **/

    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
}
