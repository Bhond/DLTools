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
 * Name: Sequence.java
 *
 * Description: New data type to store time series to be used by RNN / LSTM.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.datareader;

import fr.pops.nn.ndarray.INDArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Sequence {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private List<INDArray> sequence = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    public Sequence(){
        // Nothing to be done right now
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    public void add(INDArray X){
        this.sequence.add(X);
    }

    // Add a collection of vector
    public void addAll(Collection<? extends INDArray> c){
        this.sequence.addAll(c);
    }

    // Get the vector at the specified index
    public INDArray get(int index){
        return this.sequence.get(index);
    }

    // Get the sequence length
    public int size(){
        return this.sequence.size();
    }

}
