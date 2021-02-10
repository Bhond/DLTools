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
 * Name: DataReader.java
 *
 * Description: Abstract class to define the storage of the data
 *              used by the neural networks
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.datareader;

import fr.pops.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

public abstract class DataReader {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected int nbOfTrainingSamples = 1;
    protected int nbOfLabels = 1;

    /*****************************************
     *
     * Actual data
     *
     *****************************************/
    protected List<INDArray> dataset = new ArrayList<>();
    protected List<INDArray> labels = new ArrayList<>();
    protected List<Character> dictionary = new ArrayList<>();

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public int getNbOfTrainingSamples(){return this.nbOfTrainingSamples;}

    public INDArray getSample(int i){return this.dataset.get(i);}

    public INDArray getLabel(int i){return this.labels.get(i);}

    public Character getFromDictionary(int index){ return this.dictionary.get(index); }

}
