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
 * Name: Main.java
 *
 * Description: Class starting Pops IHM client
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.ihmmain;

import fr.pops.client.Client;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static boolean isStandAlone = false;

    public static void main(String[] args) {
        // Manage args and options
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-sa")){ // Stand alone
                    isStandAlone = true;
            }
        }

        // Launch app
        launch(args);
    }

    public void start(Stage stage) {
        Client client = Client.getInstance();
        client.init(stage, isStandAlone);
        client.start();
    }
}