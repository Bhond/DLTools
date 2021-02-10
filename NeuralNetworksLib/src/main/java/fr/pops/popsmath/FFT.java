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
 * Name: FFT.java
 *
 * Description: Class allowing computation of the Fast Fourier Transform.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.popsmath;

public abstract class FFT {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Misc
    private final static double factorRadius = 1;

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * Process data
     * @param data
     * @return
     */
    public static Complex[] initiateComputation(double[] data){
        // TODO : find a way to deal with a size which is not a pwr of 2 rather than padding the data with trailing zeros...
        double[] completedData;
        int newSize;
        boolean zeroPadding = true;
        boolean flagPow2 = controlSize(data);
        // Control if the input array's size is a power of 2. If not, it is completed with zeros
        if (! flagPow2){
            if (zeroPadding){
                newSize = (int) Math.floor(PopsMath.log2(data.length)) + 1;
                completedData = completeData(data, newSize);
            } else{
                newSize = (int) Math.floor(PopsMath.log2(data.length)) + 1;
                completedData = completeData(data, newSize);
            }
        } else {
            completedData = data;
        }

        Complex[] res = new Complex[completedData.length];
        for (int i = 0; i < res.length; i++){
            res[i] = new Complex(completedData[i], 0);
        }

        return res;
    }

    /**
     * Control size
     * @param data
     * @return
     */
    public static boolean controlSize(double[] data){
        return Math.floor(PopsMath.log2(data.length)) == PopsMath.log2(data.length);
    }

    /**
     * Complete data
     * @param data
     * @param size
     * @return
     */
    private static double[] completeData(double[] data, int size){
        double[] res = new double[(int) Math.pow(2, size)];
        for (int i = 0; i < data.length; i++){
            res[i] = data[i];
        }
        return res;
    }

    /**
     *
     * @param x
     * @return
     */
    public static Complex[] fft(Complex[] x) {
        int N = x.length;
        // If there is only one element in the input array
        if (N == 1) { return x; }

        // Control the size N, needs to be a power of 2
        if (N % 2 != 0) {
            System.out.println("N ain't a power of 2.");
        }

        // Separate the array into odd and even numbers and compute their respective FFT
        Complex[] even = new Complex[N/2];
        for (int i = 0; i < N/2; i++) {
            even[i] = x[2 * i];
        }
        Complex[] q = fft(even);

        Complex[] odd = new Complex[N/2];
        for (int i = 0; i < N/2; i++) {
            odd[i] = x[2 * i + 1];
        }
        Complex[] r = fft(odd);

        // Combine both with the formula
        Complex[] res = new Complex[N];
        for (int k = 0; k < N / 2; k++) {
            Complex w = factor(N, k);
            res[k] = Complex.add(q[k], Complex.times(w, r[k]));
            res[k + N / 2] = Complex.minus(q[k], Complex.times(w, r[k]));
        }

        return res;
    }

    /**
     * Factor w_N^k
     * @param N
     * @param k
     * @return
     */
    // Factor w_N^k
    private static Complex factor(int N, int k) {
        double theta = 0;
        try {
            theta = - 2 * Math.PI * k / N;
        }catch (ArithmeticException e){
            System.out.println("ArithmeticException when computing the factor : division by zero.");
        }
        return new Complex(factorRadius * Math.cos(theta), factorRadius * Math.sin(theta));
    }

}
