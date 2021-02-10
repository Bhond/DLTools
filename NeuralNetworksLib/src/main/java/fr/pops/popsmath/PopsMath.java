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
package fr.pops.popsmath;

import fr.pops.ndarray.INDArray;

import java.util.Random;
import java.util.function.Function;

@SuppressWarnings("unused")
public abstract class PopsMath<T, R> implements Function<T, R> {

    // Misc
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
    public static Function<INDArray, INDArray> softmax = (X) -> ArrayUtil.apply(x -> (Math.exp(x) / ArrayUtil.sum(ArrayUtil.apply(Math::exp, X))), X);
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

    // Matrix and vectors operations
    /* *
     * Apply a function to every element of the matrix M
     * */
    public static Matrix apply(Function<Double, Double> f, Matrix M){
        double[][] buffer = new double[M.getNbRows()][M.getNbColumns()];
        for (int i = 0; i < M.getNbRows(); i++){
            for (int j = 0; j < M.getNbColumns(); j++){
                buffer[i][j] = f.apply(M.getValue(i,j));
            }
        }
        return Matrix.toMatrix(buffer);
    }

    /* *
     * Apply a function to every element of the vector X
     * */
    public static Vector apply(Function<Double, Double> f, Vector X){
        double[] buffer = new double[X.getSize()];
        for (int i = 0; i < X.getSize(); i++){
            buffer[i] = f.apply(X.getValue(i));
        }
        return Vector.toVector(buffer);
    }

    /* *
     * Return a new Matrix corresponding to A * B != B * A
     * */
    public static Matrix times(Matrix A, Matrix B){
        double[][] res = new double[A.getNbRows()][B.getNbColumns()];
        int maxK = A.getNbColumns();
        for (int i = 0; i < A.getNbRows(); i++){
            for (int j = 0; j < B.getNbColumns(); j++){
                double buffer = 0;
                for (int k = 0; k < maxK; k++){
                    buffer += A.getValue(i,k) * B.getValue(k,j);
                }
                res[i][j] = buffer;
            }
        }

        return Matrix.toMatrix(res);
    }

    /* *
     * Return a new Vector corresponding to Ax != xA
     * */
    // TODO : Check correspondence between sizes
    public static Vector times(Matrix A, Vector X){
        double[] res = new double[A.getNbRows()];
        for (int i = 0; i < A.getNbRows(); i++){
            double buffer = 0;
            for (int k = 0; k < X.getSize(); k++){
                buffer += A.getValue(i,k) * X.getValue(k);
            }
            res[i] = buffer;
        }
        return Vector.toVector(res);
    }

    /* *
     * Return a new Matrix corresponding to A + B
     * */
    public static Matrix add(Matrix A, Matrix B){
        // TODO: add rerror when sizes don't match

        double[][] res = new double[A.getNbRows()][A.getNbColumns()];
        for (int i = 0; i < A.getNbRows(); i++){
            for (int j = 0; j < B.getNbColumns(); j++){
                res[i][j] = A.getValue(i,j) + B.getValue(i,j);
            }
        }
        return Matrix.toMatrix(res);
    }

    /* *
     * Return a new Vector corresponding to X + Y
     * */
    public static Vector add(Vector X, Vector Y){
        double[] res = new double[X.getSize()];
        if (X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res[i] = X.getValue(i) + Y.getValue(i);
            }
        } else {
            for (int i = 0; i < X.getSize(); i++){
                res[i] = 0;
            }
        }
        return Vector.toVector(res);
    }

    /* *
     * Return a new Vector corresponding to -1 * M
     * */
    public static Matrix negate(Matrix M){ return PopsMath.apply(x -> (-1) * x, M); }

    /* *
     * Return a new Vector corresponding to -1 * X
     * */
    public static Vector negate(Vector X){
        return PopsMath.apply(x -> (-1) * x, X);
    }

    /* *
     * Transpose Matrix A
     * */
    public static Matrix transpose(Matrix A){
        double[][] res = new double[A.getNbColumns()][A.getNbRows()];
        for (int i = 0; i < A.getNbRows(); i++){
            for (int j = 0; j < A.getNbColumns(); j++){
                res[j][i] = A.getValue(i,j);
            }
        }
        return Matrix.toMatrix(res);
    }

    /* *
     * Compute Hadamard product of  Matrix A and Matrix B (multiplication component wise)
     * */
    public static Matrix Hadamard(Matrix A, Matrix B){
        // TODO: add an error when sizes don't match
        double[][] res = new double[A.getNbRows()][A.getNbColumns()];
        if( A.getNbColumns() == B.getNbColumns() && A.getNbRows() == B.getNbRows()){
            for (int i = 0; i < A.getNbRows(); i++){
                for (int j = 0; j < A.getNbColumns(); j++){
                    res[i][j] = A.getValue(i,j) * B.getValue(i,j);
                }
            }
        }
        return Matrix.toMatrix(res);
    }

    /* *
     * Compute Convolution between Matrix A and Matrix B
     * */
    public static Matrix convolve(Matrix A, Matrix B, int step){

        boolean testRowWise = (A.getNbRows() - B.getNbRows()) % step == 0;
        boolean testColumnWise = (A.getNbColumns() - B.getNbColumns()) % step == 0;

        int nbSlidesI;
        int nbSlidesJ;

        if (testColumnWise && testRowWise){
            nbSlidesI = ((A.getNbRows() - B.getNbRows()) / step) + 1;
            nbSlidesJ = ((A.getNbColumns() - B.getNbColumns()) / step) + 1;
        } else {
            nbSlidesI = 0;
            nbSlidesJ = 0;
            System.out.print("Cannot compute convolution between matrices A and B. Add padding.");
            }

        double[][] res = new double[nbSlidesI][nbSlidesJ];
        for (int i = 0; i < nbSlidesI; i += step){
            for (int j = 0; j < nbSlidesJ; j += step){
                Matrix C = Matrix.extract(A, i, j, B.getNbRows(), B.getNbColumns());
                Matrix D = Hadamard(C, B);
                res[i][j] = reduceSum(D);
            }
        }

        return Matrix.toMatrix(res);
    }

    /* *
     * Max Pooling of A to reduce its size
     * Take the max value of a grid sliding over A
     * of specified size resulting in a matrix
     * */
    public static Matrix maxPool(Matrix A, int sizeI, int sizeJ, int step){

        boolean testRowWise = (A.getNbRows() - sizeI) % step == 0;
        boolean testColumnWise = (A.getNbColumns() - sizeJ) % step == 0;

        int nbSlidesI;
        int nbSlidesJ;

        if (testColumnWise && testRowWise){
            nbSlidesI = ((A.getNbRows() - sizeI) / step) + 1;
            nbSlidesJ = ((A.getNbColumns() - sizeJ) / step) + 1;
        } else {
            nbSlidesI = 0;
            nbSlidesJ = 0;
            System.out.print("Cannot compute pooling of matrix A. Add padding.");
        }

        double[][] res = new double[nbSlidesI][nbSlidesJ];
        for (int i = 0; i < nbSlidesI; i += step){
            for (int j = 0; j < nbSlidesJ; j += step){
                Matrix C = Matrix.extract(A, i, j, sizeI, sizeJ);
                res[i][j] = max(C);
            }
        }

        return Matrix.toMatrix(res);
    }

    /* *
     * Average Pooling of A to reduce its size
     * Take the average value of a grid sliding over A
     * of specified size resulting in a matrix
     * */
    public static Matrix averagePool(Matrix A, int sizeI, int sizeJ, int step){

        boolean testRowWise = (A.getNbRows() - sizeI) % step == 0;
        boolean testColumnWise = (A.getNbColumns() - sizeJ) % step == 0;

        int nbSlidesI;
        int nbSlidesJ;

        if (testColumnWise && testRowWise){
            nbSlidesI = ((A.getNbRows() - sizeI) / step) + 1;
            nbSlidesJ = ((A.getNbColumns() - sizeJ) / step) + 1;
        } else {
            nbSlidesI = 0;
            nbSlidesJ = 0;
            System.out.print("Cannot compute pooling of matrix A. Add padding.");
        }

        double[][] res = new double[nbSlidesI][nbSlidesJ];
        for (int i = 0; i < nbSlidesI; i++){
            for (int j = 0; j < nbSlidesJ; j++){
                Matrix C = Matrix.extract(A, i, j, sizeI, sizeJ);
                res[i][j] = average(C);
            }
        }

        return Matrix.toMatrix(res);
    }

    /* *
     * Compute Hadamard product of  Vector X and Vector Y (multiplication component wise)
     * */
    public static Vector Hadamard(Vector X, Vector Y){
        // TODO: add an error when sizes don't match
        double[] res = new double[X.getSize()];
        if( X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res[i] = X.getValue(i) * Y.getValue(i);
            }
        }
        return Vector.toVector(res);
    }


    /* *
     * Create diagonal Matrix A from a vector X
     * */
    public static Matrix diag(Vector X) {
        double[][] res = new double[X.getSize()][X.getSize()];
        for (int i = 0; i < X.getSize(); i++) {
            res[i][i] = X.getValue(i);
        }
        return Matrix.toMatrix(res);
    }

    /* *
     * Add every element to each other on line i of the matrix M
     * */
    public static double sumRow(int line, Matrix M){
        double res = 0;
        for(int j = 0; j < M.getNbColumns(); j++){
            res += M.getValue(line, j);
        }
        return res;
    }

    /* *
     * Add every element to each other on column j of the matrix M
     * */
    public static double sumColumn(int column, Matrix M){
        double res = 0;
        for(int i = 0; i < M.getNbRows(); i++){
            res += M.getValue(i, column);
        }
        return res;
    }

    /* *
     * Sum every element of matrix M to each other
     * */
    public static double reduceSum(Matrix M){
        double res = 0;
        for(int i = 0; i < M.getNbRows(); i++){
            for(int j = 0; j < M.getNbColumns(); j++){
                res += M.getValue(i,j);
            }
        }
        return res;
    }

    /* *
     * Sum every element of vector X to each other
     * */
    public static double reduceSum(Vector X){
        double res = 0;
        for(int i = 0; i < X.getSize(); i++){
            res += X.getValue(i);
        }
        return res;
    }

    /* *
    * Return the usual scalar product of vectors X and Y
    * */
    public static double dot(Vector X, Vector Y){
        double res = 0;
        if (X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res += X.getValue(i) * Y.getValue(i);
            }
        }else{
            System.out.println("Size of vector X and vector Y don't match.");
        }
        return res;
    }

    /* *
     * Return the maximal value in the matrix A
     * */
    public static double max(Matrix A){
        double res = A.getValue(0, 0);
        for(int i = 0; i < A.getNbRows(); i++){
            for(int j = 0; j < A.getNbColumns(); j++){
                res = Math.max(res, A.getValue(i, j));
            }
        }
        return res;
    }

    /* *
     * Return the maximal value in the vector X
     * */
    public static double max(Vector X){
        double res = X.getValue(0);
        for(int i = 1; i < X.getSize(); i++){
            res = Math.max(res, X.getValue(i));
        }
        return res;
    }

    /* *
     * Return the minimal value in the vector X
     * */
    public static double min(Vector X){
        double res = X.getValue(0);
        for(int i = 1; i < X.getSize(); i++){
            res = Math.min(res, X.getValue(i));
        }
        return res;
    }

    /* *
     * Return the average value in the matrix A
     * */
    public static double average(Matrix A){
        double res = 0;
        for(int i = 0; i < A.getNbRows(); i++){
            for(int j = 0; j < A.getNbColumns(); j++){
                res += A.getValue(i, j);
            }
        }
        return res / (A.getNbColumns() * A.getNbRows());
    }

    /* *
     * Return the index of either the first min or the first max value of the vector X
     * */
    public static int indexOfMinMaxValue(Vector X, String minOrMax){
        int res = 0;
        double buff = X.getValue(0);
        if (minOrMax.toLowerCase().equals("min")){
            for(int i = 1; i < X.getSize(); i++){
                if (buff > X.getValue(i)){
                    buff = X.getValue(i);
                    res = i;
                }
            }
        } else if (minOrMax.toLowerCase().equals("max")){
            for(int i = 1; i < X.getSize(); i++){
                if (buff < X.getValue(i)){
                    buff = X.getValue(i);
                    res = i;
                }
            }
        } else {
            System.out.println("Unknown string input : specify either min or max.");
        }

        return res;
    }

    /**
     * Interface for two arguments functions
      */
    public interface Function2Args<T, U, V> {
        V apply(T t, U u);
    }

}
