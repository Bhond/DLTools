package fr.pops.customnodes.neuralnetworks.component.link;

import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.scene.layout.AnchorPane;

import java.util.UUID;

public class LinkHandler extends AnchorPane {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public LinkHandler(){
        // General
        this.setId(UUID.randomUUID().toString());
        this.setPrefSize(20,20);
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_CSS_DIRECTORY + "LinkHandler.css"));
        this.getStyleClass().add("handler");
    }

}
