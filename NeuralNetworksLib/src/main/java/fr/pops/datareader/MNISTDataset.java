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
 * Name: MNISTDataset.java
 *
 * Description: Read idx files and store MNIST hand written digits data.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.datareader;

import fr.pops.main.PopsMain;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.cst.StrCst;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MNISTDataset extends DataReader {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Cst
    private static final int SIZE_LABEL_VECTOR = 10;

    // Data structure
    private int nbRows = 1;
    private int nbColumns = 1;
    private int vectorizedImageSize = this.nbRows * this.nbColumns;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    public MNISTDataset(EnumCst.RunningMode mode) throws IOException {
        // Invoke parent
        super();

        // Read the file containing the labels and add them to their structure
        if (mode == EnumCst.RunningMode.TRAINING){
            this.readLabelFile(StrCst.PATH_MNIST_TRAINING_LABELS);
            this.readImageFile(StrCst.PATH_MNIST_TRAINING_IMAGES);
        } else if (mode == EnumCst.RunningMode.TEST){
            this.readLabelFile(StrCst.PATH_MNIST_TESTING_LABELS);
            this.readImageFile(StrCst.PATH_MNIST_TESTING_IMAGES);
        }

    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Read the labels
     * @param labelFile The name of the file
     * @throws IOException
     */
    private void readLabelFile(String labelFile) throws IOException {

        // Build data stream
        String fullPath = StrCst.PATH_MNIST + labelFile;
        InputStream file = PopsMain.class.getResourceAsStream("/resources" + fullPath);
        DataInputStream dis = new DataInputStream(file);

        // First read the magic number
        int magicNumber = dis.readInt();

        // Read the number of labels
        int nbofBytesToRead = dis.readInt();
        this.nbOfLabels = nbofBytesToRead;

        // Read the actual labels and vectorize them
        for (int i = 0; i < nbofBytesToRead; i++){
            int val = dis.readByte();
            double[] labelVectorArr = new double[SIZE_LABEL_VECTOR];
            labelVectorArr[val] = 1;
            INDArray labelVector = new BaseNDArray(labelVectorArr, SIZE_LABEL_VECTOR);
            this.labels.add(labelVector);
        }
        // Close stream
        dis.close();
    }

    /**
     * Read the image file
     * @param imageFile The name of the file
     * @throws IOException
     */
    private void readImageFile(String imageFile) throws IOException {

        // Build data stream
        String fullPath = StrCst.PATH_MNIST + imageFile;
        InputStream file = PopsMain.class.getResourceAsStream("/resources" + fullPath);
        DataInputStream dis = new DataInputStream(file);

        // First read the magic number
        int magicNumber = dis.readInt();

        this.nbOfTrainingSamples = dis.readInt();
        this.nbRows = dis.readInt();
        this.nbColumns = dis.readInt();
        this.vectorizedImageSize = this.nbRows * this.nbColumns;
        int nbofBytesToRead = this.nbOfTrainingSamples * this.nbRows * this.nbColumns;

        // Read the pixel values and vectorize each image
        int counter = 0;
        int i = 0;
        int arrSize = this.nbRows * this.nbColumns;
        int[] shape = new int[]{arrSize};
        double[] data = new double[arrSize];
        while (i < nbofBytesToRead){
            if (counter < this.vectorizedImageSize){
                double val = dis.readUnsignedByte();
                data[counter] = val / 255;
                counter++;
                i++;
            } else {
                super.dataset.add(new BaseNDArray(data, shape));
                counter = 0;
                data = new double[this.nbRows * this.nbColumns];
            }
        }
        // Add final image
        super.dataset.add(new BaseNDArray(data, shape));
        // Close stream
        dis.close();
    }
}
