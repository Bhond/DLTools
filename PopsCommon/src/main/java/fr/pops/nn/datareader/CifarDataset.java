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
 * Name: CifarDataset.java
 *
 * Description: Read cifar-10 bin files
 *
 * Author: Charles MERINO
 *
 * Date: 15/11/2020
 *
 ******************************************************************************/
package fr.pops.nn.datareader;

import fr.pops.nn.ndarray.BaseNDArray;
import fr.pops.nn.popscst.cst.EnumCst;
import fr.pops.nn.popscst.cst.StrCst;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CifarDataset extends DataReader {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Cst
    private static final int NB_IMAGE_PER_TRAINING_FILE = 10000;
    private static final int NB_BYTE_PER_IMAGE = 3072; // 1 byte / pixel
    private static final int NB_BYTE_PER_LABEL = 1;
    private static final int NB_BYTE_PER_FILE = NB_IMAGE_PER_TRAINING_FILE * (NB_BYTE_PER_IMAGE + NB_BYTE_PER_LABEL); // Just in case ....
    private static final int NB_ROWS = 32;
    private static final int NB_COLUMNS = 32;
    private static final int NB_CHANNELS = 3; // rgb image

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    public CifarDataset(EnumCst.RunningMode mode) throws IOException {
        // Invoke parent
        super();

        // TODO: To complete by allowing use of the different files

        // Read the file containing the labels and add them to their structure
        if (mode == EnumCst.RunningMode.TRAINING){
            this.readImageFile(StrCst.PATH_CIFAR_TRAINING_IMAGES);
            this.nbOfTrainingSamples = this.dataset.size();
            this.nbOfLabels = this.labels.size();
        } else if (mode == EnumCst.RunningMode.TEST){
            this.readImageFile(StrCst.PATH_CIFAR_TRAINING_IMAGES);
            this.nbOfTrainingSamples = this.dataset.size();
            this.nbOfLabels = this.labels.size();
        }
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Read the image file
     * @param imageFile The name of the file
     * @throws IOException
     */
    private void readImageFile(String imageFile) throws IOException {

        // Build data stream
        String fullPath = StrCst.PATH_CIFAR + imageFile;
        InputStream file = this.getClass().getResourceAsStream(fullPath);
        DataInputStream dis = new DataInputStream(file);

        // Read the pixel values and vectorize each image
        int imageCount = 0;
        while (imageCount < NB_IMAGE_PER_TRAINING_FILE){ //  TODO: change this for testing
            // First byte for the label
            int lblIndex = dis.readByte();
            double[] labelArr = new double[EnumCst.CifarLabels.values().length];
            labelArr[lblIndex] = 1;
            this.labels.add(new BaseNDArray(labelArr, EnumCst.CifarLabels.values().length));

            // Read the actual image channel by channel
            double[] imageArr = new double[NB_BYTE_PER_IMAGE];
            for (int i = 0; i < NB_BYTE_PER_IMAGE; i++){
                double val = dis.readUnsignedByte();
                imageArr[i] = val / 255;
            }
            this.dataset.add(new BaseNDArray(imageArr, NB_ROWS, NB_COLUMNS, NB_CHANNELS));
            imageCount++;
        }

        // Close stream
        dis.close();
    }
}
