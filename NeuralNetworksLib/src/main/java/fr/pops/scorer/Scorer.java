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
 * Name: Scorer.java
 *
 * Description: Class defining the scorer to compute the performance of the model.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.scorer;

import fr.pops.datareader.DataReader;
import fr.pops.nn.layers.Layer;
import fr.pops.nn.networks.NeuralNetworkProperties;
import fr.pops.popsmath.Matrix;
import fr.pops.popsmath.PopsMath;
import fr.pops.popsmath.Vector;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Scorer implements Serializable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Matrix confusionMatrix;
    protected double precision = 0.0d;
    protected double recall = 0.0d;
    protected double f1 = 0.0d;

    // Listeners
    protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
     public Scorer(){
        // Nothing to be done
     }

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * Fill in the confusion matrix to compute
     * the performance of the neural network
     * @param step
     * @param dr
     * @param outputLayer
     */
    public void fillConfusionMatrix(int step, DataReader dr, Layer outputLayer){
        int valueGuessed = PopsMath.indexOfMinMaxValue((Vector) outputLayer.getActivations(), "max");
        int lbl = PopsMath.indexOfMinMaxValue((Vector) dr.getLabel(step), "max") ;
        this.confusionMatrix.setValue(lbl, valueGuessed, this.confusionMatrix.getValue(lbl, valueGuessed)+1);
    }

    /**
     * Compute the recall value
     */
    private void computeRecall(){
        int nbOfRows = this.confusionMatrix.getNbRows();
        double res = 0;
        for(int i = 0; i < nbOfRows; i++){
            double buf = PopsMath.sumRow(i, this.confusionMatrix) - this.confusionMatrix.getValue(i,i);
            buf = this.confusionMatrix.getValue(i,i) / (this.confusionMatrix.getValue(i,i) + buf);
            res += buf;
        }
        this.setRecall(res / nbOfRows);
    }

    /**
     * Compute the precision
     */
    private void computePrecision(){
        int nbOfColmuns = this.confusionMatrix.getNbColumns();
        double res = 0;
        for(int j = 0; j < nbOfColmuns; j++){
            double buf = PopsMath.sumColumn(j, this.confusionMatrix) - this.confusionMatrix.getValue(j,j);
            buf = this.confusionMatrix.getValue(j,j) / (this.confusionMatrix.getValue(j,j) + buf);
            res += buf;
        }
        this.setPrecision(res / nbOfColmuns);
    }

    /**
     * Compute the F1 score
     */
    public void computeF1(){
        computePrecision();
        computeRecall();
        this.setF1((this.getPrecision() * this.getRecall()) / (this.getPrecision() + this.getRecall()));
    }

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    public Scorer initConfusionMatrix(Layer layer){
        this.confusionMatrix = new Matrix(layer.getActivations().getShape().getSize(),
                                            layer.getActivations().getShape().getSize());
        return this;
    }

    /*****************************************
     *
     * Getter and setters : listeners style
     *
     *****************************************/
    // Precision
    public synchronized double getPrecision(){ return this.precision; }

    public synchronized void setPrecision(double val) {
        double oldValue = this.getPrecision();
        this.precision = val;
        changeSupport.firePropertyChange(NeuralNetworkProperties.PRECISION.getName(), oldValue, this.precision);
    }

    // Recall
    public double getRecall(){ return this.recall; }

    public synchronized void setRecall(double val) {
        double oldValue = this.getRecall();
        this.recall = val;
        changeSupport.firePropertyChange(NeuralNetworkProperties.RECALL.getName(), oldValue, this.recall);
    }

    // F1
    public double getF1(){ return this.f1; }

    public synchronized void setF1(double val) {
        double oldValue = this.getF1();
        this.f1 = val;
        changeSupport.firePropertyChange(NeuralNetworkProperties.F1.getName(), oldValue, this.f1);
    }

    /*****************************************
     *
     * Listeners handling
     *
     *****************************************/
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    /*****************************************
     *
     * Print methods
     *
     *****************************************/
//    public void printScore(){
//        String message = "\n";
//        message += "Precision = " + this.scorer.getPrecision() + "\n";
//        message += "Recall = " + this.scorer.getRecall() + "\n";
//        message += "F1 = " + this.scorer.getF1() + "\n";
//        message += "\n" + "Confusion Matrix : \n" + this.confusionMatrix.toString();
//        System.out.println(message);
//    }
}
