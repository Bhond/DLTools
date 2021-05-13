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
 * Name: PopsMath.java
 *
 * Description: Class defining custom functions and
 *              operations between custom types.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.math;

import fr.pops.math.ndarray.INDArray;

import java.util.Random;
import java.util.function.Function;

@SuppressWarnings("unused")
public abstract class PopsMath<T, R> implements Function<T, R> {

    /**
     * Convert double to long
     * @param value The value to convert
     * @param base The base value which correspond to 1L
     */
    public static long convertDoubleToLong(double value, double base){
        if (value < base) return 0L;
        return (long) (value / base);
    }

    // Round the given number at the given decimal
    public static double round(double x, int dec){ return Math.round(x * Math.pow(10, dec)) / Math.pow(10, dec); }

    // log2
    public static double log2(double x) { return Math.log(x) / Math.log(2); }

    // Generate a random number
    // Classic randomized generation
    public static double rand(){ return 2 * Math.random() - 1; }

    // Uniform distribution generation between min and max
    public static double rand(double min, double max){ return ((max - min) * new Random().nextDouble() + min); }

    // Uniform distribution generation between -x and x
    public static double rand(double x){ return x * (2 * Math.random() - 1); }

    // Pseudo gaussian distribution generation with mean and variance
    public static double randGaussian(double mean, double variance){ return (new Random()).nextGaussian() * variance + mean; }

    // Activation functions
    // Identity
    public static Function<Double, Double> identity = (x) -> x;

    // Logistic
    public static Function<Double, Double> sigmoid = (x) -> 1 / (1 + Math.exp(-x));
    public static Function<Double, Double> dsigmoid = (x) -> Math.exp(-x) / Math.pow((1 + Math.exp(-x)), 2);

    // Softmax
    public static Function<INDArray, INDArray> softmax = (X) -> fr.pops.math.ArrayUtil.apply(x -> (Math.exp(x) / fr.pops.math.ArrayUtil.sum(ArrayUtil.apply(Math::exp, X))), X);
    public static Function<INDArray, INDArray> dsoftmax = (X) -> ArrayUtil.apply(x -> 0 * x + 1, X);

    // ReLu
    public static Function<Double, Double> ReLu = (x) -> Math.max(0d, x);
    public static Function<Double, Double> dRelu = (x) -> x <= 0 ? 0d : 1d; // The derivative of 0 is 0

    // Tanh
    public static Function<Double, Double> tanh = Math::tanh;
    public static Function<Double, Double> dtanh = (x) -> 1 - Math.pow(Math.tanh(x), 2);

    // Kronecker
    public static int kronecker(int i, int j) { return i == j ? 1 : 0; }

    // Clamp a value between min and max
    public static double clamp(double val, double min, double max){
        return Math.min(Math.max(min, val), max);
    }

    /**
     * Interface for two arguments functions
      */
    public interface Function2Args<T, U, V> {
        V apply(T t, U u);
    }

}
