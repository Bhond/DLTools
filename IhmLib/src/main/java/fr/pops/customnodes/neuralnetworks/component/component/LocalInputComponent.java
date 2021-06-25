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
 * Name: LocalInputComponent.java
 *
 * Description: Class defining the component used to display the local input
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.beans.test.TestBean;
import fr.pops.cst.EnumCst;
import fr.pops.customnodes.plot.LinePlot;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class LocalInputComponent extends Component {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String path;
    private LinePlot activationPlot;
    private Button showFileChooserButton;
    private FileChooser fileChooser;
    private File displayedFile;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public LocalInputComponent(){
        super(EnumCst.ComponentTypes.INPUT_LOCAL, new TestBean());

        // File chooser and button
        this.configureFileChooser();

    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    // Configure the file chooser and the button showing it
    private void configureFileChooser(){
        // File chooser
        this.fileChooser = new FileChooser();

        // Available extensions
        FileChooser.ExtensionFilter mnistExtensionFilter = new FileChooser.ExtensionFilter("MNIST", "*.idx3-ubyte", "*.idx1-ubyte");
        FileChooser.ExtensionFilter waveExtensionFilter = new FileChooser.ExtensionFilter("WAVE", "*.wav");
        this.fileChooser.getExtensionFilters().addAll(mnistExtensionFilter, waveExtensionFilter);

        // Button opening the file chooser
        this.showFileChooserButton = new Button("Browse file");
        this.showFileChooserButton.setOnAction(actionEvent -> {
            this.displayedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        });

        // Add it to hierarchy
        this.beanProperties.addExtraNodes(this.showFileChooserButton);
    }
}
