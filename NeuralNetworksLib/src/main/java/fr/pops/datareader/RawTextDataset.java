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
 * Name: RawTextDataSet.java
 *
 * Description: Read raw text files, build a dictionary containing
 *              each distinct individual letter from the text and store the whole
 *              text in sequence-like data to be learnt by RNN / LSTM.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 * TODO: correct this class with sequences
 *
 ******************************************************************************/
package fr.pops.datareader;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class RawTextDataset extends DataReader {

    // Attributes
    private final static String ROOT_PATH = "../../../resources/data/Text/"; // For now
    private String fullPath;
    private int  sequenceLength = 50;

    // Objects
    private String fullText = "";
    private List<Character> textAsList = new ArrayList<>();

    // Ctor
    public RawTextDataset(String filename){
        // Invoke parent
        super();

        // Complete path
        this.fullPath = ROOT_PATH + filename;
    }

    public DataReader buildDataset() throws IOException {
        this.readFile();
        this.textToObj();
        this.buildDictionary();
        this.buildData();
        this.buildLabels();
        this.nbOfTrainingSamples = this.dataset.size();
        return this;
    }

    // Read the file
    private void readFile() throws IOException {
        // Create Stream
        File file = new File(getClass().getResource(this.fullPath).getFile());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        // Read lines
        String string;
        while ((string = bufferedReader.readLine()) != null){
            this.fullText += string;
        }
    }

    // Retrieve all characters from the file
    private void textToObj(){
        this.textAsList = this.fullText.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    // Build the dictionary
    private void buildDictionary(){
        // Build stream to collect the dictionary
        this.dictionary = this.fullText.chars().mapToObj(c -> (char) c).distinct().collect(Collectors.toList());
    }

    // Fill parent structure ; dataset
    private void buildData(){
        Sequence sequence = new Sequence();
        for (int i = 0; i < this.textAsList.size()-1; i++){
            double[] sampleArr = new double[this.dictionary.size()];
            sampleArr[this.dictionary.indexOf(this.textAsList.get(i))] = 1;
            INDArray sample = new BaseNDArray(sampleArr, this.dictionary.size());
            if ( i > 0 & i % (this.sequenceLength) == 0){
                this.dataset.add(null); // null <-> sequence
                sequence = new Sequence();
                sequence.add(sample);
            } else {
                sequence.add(sample);
            }
        }
        // Add last sequence if it has not been added in the loop
        if ((this.textAsList.size() - 1) % this.sequenceLength != 0) {
            this.dataset.add(null); // null <-> sequence
        }
    }

    // Fill parent structure : labels
    private void buildLabels(){
        Sequence sequence = new Sequence();
        List<Character> textForLabels = this.textAsList;
        textForLabels.remove(0);
        for (int i = 0; i < textForLabels.size(); i++){
            double[] sampleArr = new double[this.dictionary.size()];
            sampleArr[this.dictionary.indexOf(this.textAsList.get(i))] = 1;
            INDArray sample = new BaseNDArray(sampleArr, new int[]{this.dictionary.size()});
            if (i > 0 & i % (this.sequenceLength) == 0){
                this.labels.add(null); // null <-> sequence
                sequence = new Sequence();
                sequence.add(sample);
            } else {
                sequence.add(sample);
            }
        }
        // Add last sequence if it has not been added in the loop
        if ((this.textAsList.size() - 1) % this.sequenceLength != 0) {
            this.labels.add(null); // null <-> sequence
        }
    }

    // With
    public RawTextDataset withSequenceLength(int length){
        this.sequenceLength = length;
        return this;
    }
}
