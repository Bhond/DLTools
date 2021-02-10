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
 * Name: Specter.java
 *
 * Description: Class building spectrograms from FFT.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 * TODO: Review the implementation / its use.
 *
 ******************************************************************************/
package fr.pops.popsmath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Specter {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private List<Complex[]> inputData;
    private List<Complex[]> fftData = new ArrayList<>();
    private Double[] frequencies;
    private Double[] time;
    private Double[][] amp;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     *
     * @param data
     */
    public Specter(List<Complex[]> data){
        this.inputData = data;
    }

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     *
     */
    public void computeSpecter(){

        this.fftData =  this.inputData.stream()
                                          .map(FFT::fft)
                                          .collect(Collectors.toList());

    }

    /**
     *
     * @param Fs
     * @param duration
     * @param wdwSize
     */
    public void buildFrequenciesAmp(double Fs, double duration, int wdwSize){
        int maxIndex = wdwSize / 2;
        double timeStep = 0.5 * duration / this.fftData.size();
        double c = Fs / wdwSize;
        double max = 0;
        this.frequencies = new Double[maxIndex];
        this.time = new Double[this.fftData.size()];
        this.amp = new Double[maxIndex][this.fftData.size()];
        for (int i = 0; i < maxIndex; i++){
            this.frequencies[i] = i * c;
        }

        for (int i = 0; i < this.fftData.size(); i++){
            this.time[i] = i * timeStep;
        }

        for (int i = 0; i < this.fftData.size(); i++){
            for (int j = 0; j < maxIndex; j++){
                this.amp[j][i] = this.fftData.get(i)[j].getRadius();
                if (this.amp[j][i] > max){
                    max = this.amp[j][i];
                }
            }
        }
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public List<Complex[]> getSpecter(){
        return this.fftData;
    }

}
