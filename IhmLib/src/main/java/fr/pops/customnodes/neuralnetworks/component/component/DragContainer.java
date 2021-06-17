package fr.pops.customnodes.neuralnetworks.component.component;

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
            new DataFormat("fr.pops.customnodes.neuralnetworks.component.component.Component.add");
    public static final DataFormat AddLink =
            new DataFormat("fr.pops.customnodes.neuralnetworks.component.utils.Link.add");

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
     *
     * @param key
     * @param value
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
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValue (String key) {
        for (Pair<String, Object> data: this.dataPairs) {
            if (data.getKey().equals(key))
                return (T) data.getValue();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List <Pair<String, Object> > getData () { return this.dataPairs; }
}
