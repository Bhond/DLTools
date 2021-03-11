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
 * Name: SpeechRecognizer.java
 *
 * Description: Class currently used to process wave files data.
 *              It creates a spectrogram data with frequencies.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 * TODO: Refactor this class.
 *
 ******************************************************************************/
package fr.pops.nn.speechrecognizer;

import fr.pops.nn.datareader.WaveReader;
import fr.pops.nn.popsmath.Complex;
import fr.pops.nn.popsmath.FFT;
import fr.pops.nn.popsmath.Specter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SpeechRecognizer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private static final String SPEECH_FILE = "../../../resources/media/wave/a440.wav"; //TMP
    private final static int SIZE_WINDOW = 2048; // Need to compute these ...
    private final static int SIZE_OVERLAP = 20;
    private static WaveReader p_wr;
    private Complex[] m_inputSignal;
    private List<Complex[]> m_wdwData = new ArrayList<>();
    private List<Complex[]> m_specter = new ArrayList<>();
    private Complex[] m_fft;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     *
     */
    public SpeechRecognizer() {
        //TODO Write properly this method : mainly in the wave reader file to avoid recreating the object, it is only needed to be read once
        this.p_wr = new WaveReader(SPEECH_FILE);
    }

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     *
     * @throws Exception
     */
    public void processSpeech() throws Exception {
        this.p_wr.readFile();
        Complex[] data = FFT.initiateComputation(this.p_wr.getData());
        this.m_inputSignal = data;
        this.prepareSpecter(data, SIZE_WINDOW, SIZE_OVERLAP);
        Specter specter = new Specter(this.m_wdwData);
        specter.computeSpecter();
        this.m_specter = specter.getSpecter();
        specter.buildFrequenciesAmp(this.p_wr.getSampleRate(), this.p_wr.getDuration(), SIZE_WINDOW);

    }

    /**
     * Transform the input signal into a list of windows for computing the spectrogram
     * TODO : Correct the windowing, no value after window 108 ...
     * @param data
     * @param windowSize
     * @param overlapSize
     */
    private void prepareSpecter(Complex[] data, int windowSize, int overlapSize){
        int length = data.length;
        int finalIndex = length;
        int k = 0;
        int firstIndex = 0;
        while(k < finalIndex){
            Complex[] buffer = new Complex[windowSize];
            for (int i = 0 ; i < windowSize; i++){
                if (k < length){
                    buffer[i] = data[k];
                } else {
                    buffer[i] = new Complex(0,0);
                }
                k++;
            }
            this.m_wdwData.add(buffer);

            k-=overlapSize;
            //System.out.println("k = "+k);
        }
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public Complex[] getData(){
        return this.m_inputSignal;
    }

    public Complex[] getFFT(){
        return this.m_fft;
    }
}
