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
 * Name: WaveReader.java
 *
 * Description: Class used to read Wave files.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 * TODO: Move this class to fr.pops.datareader.
 *
 ******************************************************************************/
package fr.pops.datareader;

import fr.pops.popsmath.Complex;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WaveReader {

    /*
     * TODO: Generalize this class for more WAVE formats depending on the header of the file
     *       Move it to the data reader package
     */

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String m_filePath;
    private InputStream m_fileStream;
    private DataInputStream m_dataStream;

    // Csts
    private static final int BYTES_INT32 = 4;
    private static final int BYTES_INT16 = 2;
    private static final int INT16_MAX = 32767;
    private static final int INT16_MIN = -32768;
    private static final int STANDARD_FMT_CHUNCK = 16;

    // First chunck
    private String m_riffString;
    private int m_fileSize;
    private String m_waveString;
    // Fmt chunck
    private String m_formatString;
    private int m_formatChunckSize;
    private int m_audioFormat;
    private int m_nbChannels;
    private int m_sampleRate;
    private int m_bytesRate;
    private int m_nbBlockAligned;
    private int m_bitsPerSample;
    // Data chunck
    private String m_dataString;
    private int m_nbSamples;
    private double[] m_normalizedData;
    private double m_duration;

    // Specter
    private Complex[][] m_specter;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    public WaveReader(String filePath) {
        this.m_filePath = filePath;
        this.m_fileStream = getClass().getResourceAsStream(filePath);
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
    public void readFile() throws Exception {
        this.m_dataStream = new DataInputStream(this.m_fileStream);
        this.readRIFFChunck();
        this.readFMTChunck();
        this.readDATAChunck();
        this.m_duration = this.m_nbSamples / this.m_sampleRate;
        this.m_dataStream.close();
    }

    /**
     * Riff Chunck
     */
    private void readRIFFChunck() {
        // Header
        this.m_riffString = this.readHexToASCII(BYTES_INT32);
        this.m_fileSize = this.readLittleEndianInt(BYTES_INT32);
        this.m_waveString = this.readHexToASCII(BYTES_INT32);
    }

    /**
     * FMT chunck
     */
    private void readFMTChunck() {
        this.m_formatString = this.readHexToASCII(BYTES_INT32);
        this.m_formatChunckSize = this.readLittleEndianInt(BYTES_INT32); // Need to differentiate the case depending on this value
        if (m_formatChunckSize == STANDARD_FMT_CHUNCK) {
            try {
                this.m_audioFormat = this.m_dataStream.readByte();
                this.skipLine();
                this.m_nbChannels = this.m_dataStream.readByte();
                this.skipLine();
                this.m_sampleRate = this.readLittleEndianInt(BYTES_INT32);
                this.m_bytesRate = this.readLittleEndianInt(BYTES_INT32);
                this.m_nbBlockAligned = this.m_dataStream.readByte();
                this.skipLine();
                this.m_bitsPerSample = this.m_dataStream.readByte();
                this.skipLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Size of the format chunck is not yet recognised.");
        }
    }

    /**
     * DATA chunck
     * @throws IOException
     */
    private void readDATAChunck() throws IOException {
        this.m_dataString = this.readHexToASCII(BYTES_INT32);
        this.m_nbSamples = this.readLittleEndianInt(BYTES_INT32) / 2;
        this.m_normalizedData= new double[this.m_nbSamples];
        try {
            this.readRawSound();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the raw data
     * @throws IOException
     */
    private void readRawSound() throws IOException {
        int data;
        for(int i = 0; i < this.m_nbSamples; i++) {
            data = this.readLittleEndianInt(BYTES_INT16);
            this.m_normalizedData[i] = (double) data / INT16_MAX;
        }
    }

    /**
     * Convert hexadecimal values to ASCCI
     * @param size
     * @return
     */
    private String readHexToASCII(int size) {
        byte[] a = new byte[size];
        for (int i=0;i<size;i++) {
            try {
                a[i] = this.m_dataStream.readByte();
            } catch (RuntimeException | IOException e) {
                e.printStackTrace();
            }
        }
        return new String(a);
    }

    /**
     * Read little endian byte
     * @param size
     * @return
     */
    private int readLittleEndianInt(int size) {
        int res = -1;
        byte[] buffer = new byte[size];
        int bytesRead;
        try {
            bytesRead = this.m_dataStream.read(buffer);
            if (bytesRead != size) {
                System.out.println("Error when reading little-endian byte in file : " + this.m_filePath + ", bytesRead = " + bytesRead);
            }
            switch (size){
                case BYTES_INT16:
                    res = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getShort();
                    break;
                case BYTES_INT32:
                    res = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Skip a line
     */
    private void skipLine() {
        try {
            this.m_dataStream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public int getSampleRate() { return this.m_sampleRate; }

    public int getNbData() { return this.m_nbSamples; }

    public int getBitsPerSample() { return this.m_bitsPerSample; }

    public double[] getData(){ return this.m_normalizedData; }

    public double getDuration(){return this.m_duration; }

    public String getInfo() {
        String message;
        message = "RIFF chunck : \n";
        message += "   - Id          : " + this.m_riffString + " \n";
        message += "   - File size   : " + this.m_fileSize   + " \n";
        message += "   - Wave String : " + this.m_waveString + " \n";
        message += " \n";
        message += "Format chunck : \n";
        message += "   - Id                : " + this.m_formatString   + " \n";
        message += "   - Audio format      : " + this.m_audioFormat    + " \n";
        message += "   - Nb channels       : " + this.m_nbChannels     + " \n";
        message += "   - Sample Rate       : " + this.m_sampleRate     + " \n";
        message += "   - Nb blocks aligned : " + this.m_nbBlockAligned + " \n";
        message += "   - Bits per Sample   : " + this.m_bitsPerSample  + " \n";
        message += " \n";
        message += "Data chunck : \n";
        message += "   - Id            : " + this.m_dataString + " \n";
        message += "   - Nb samples    : " + this.m_nbSamples  + " \n";
        message += " \n";
        message += " Misc : \n";
        message += "   - Duration : " + this.m_duration + " s \n";
        return message;
    }
}
