package fr.pops.customnodes.neuralnetworks.component.link;

import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.scene.layout.AnchorPane;

import java.util.UUID;

public class LinkHandle extends AnchorPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    public enum States { FREE, LINKED }

    private States state = States.FREE;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public LinkHandle(){
        // General
        this.setId(UUID.randomUUID().toString());
        this.setPrefSize(10,10);
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_LINK_HANDLER_CSS));
        this.selectStyle();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Select the style depending of the component's category
     */
    private void selectStyle(){
        this.getStyleClass().clear();
        switch (this.state){
            case FREE:
                this.getStyleClass().add(StrCst.STYLE_CLASS_HANDLER_FREE);
                break;
            case LINKED:
                this.getStyleClass().add(StrCst.STYLE_CLASS_HANDLER_LINKED);
                break;
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The state of the handler
     *         Free : not connected
     *         Linked : connected
     */
    public States getState() {
        return this.state;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the state of handler
     * Free : not connected
     * Linked : connected
     * @param state The new handle's state
     */
    public void setState(States state) {
        this.state = state;
        this.selectStyle();
    }
}
