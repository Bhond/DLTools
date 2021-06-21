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
 * Name: DragContainer.java
 *
 * Description: Class defining the drag container to control the components
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.StrCst;
import javafx.scene.input.DataFormat;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DragContainer implements Serializable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Serial
    private static final long serialVersionUID = -1890998765646621338L;

    // Formats
    public static final DataFormat AddNode =
            new DataFormat(StrCst.DATA_FORMAT_ADD_NODE);
    public static final DataFormat AddLink =
            new DataFormat(StrCst.DATA_FORMAT_ADD_LINK);

    // Data
    private final List<Pair<String, Object>> dataPairs = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public DragContainer(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Add data to the container
     * @param key The key of the data
     * @param value The value of the data
     */
    public void addData (String key, Object value) {
        this.dataPairs.add(new Pair<>(key, value));
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Retrieve the value at the given key in the dictionary
     * @param key The key of the data to retrieve
     * @param <T> The type of data to recover
     * @return The data at the given given key
     */
    public <T> T getValue (String key) {
        for (Pair<String, Object> data: this.dataPairs) {
            if (data.getKey().equals(key))
                return (T) data.getValue();
        }
        return null;
    }

    /**
     * @return The list of stored data apirs
     */
    public List <Pair<String, Object> > getData () { return this.dataPairs; }
}
