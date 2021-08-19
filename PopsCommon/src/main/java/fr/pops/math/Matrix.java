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
 * Name: Matrix.java
 *
 * Description: Class defining the matrices.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.math;

import java.util.function.Function;

public class Matrix {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private int nbRows;
    private int nbColumns;
    private double[][] value;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     *
     * @param sizeI
     * @param sizeJ
     */
    public Matrix(int sizeI, int sizeJ){
        this.nbRows = sizeI;
        this.nbColumns = sizeJ;
        this.value = new double[sizeI][sizeJ];
        for (int i = 0; i < sizeI; i++){
            for (int j = 0; j < sizeJ; j++){
                this.value[i][j] = 0;
            }
        }
    }

    /*****************************************
     *
     * Math
     *
     *****************************************/
    /* *
     * Return a new Vector corresponding to -1 * M
     * */
    public static Matrix negate(Matrix M){ return Matrix.apply(x -> (-1) * x, M); }

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


    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * Value at indices i,j
     * @param i Row index
     * @param j Column index
     * @return The value at indices i,j
     */
    public double getValue(int i, int j){return this.value[i][j];}

    /**
     * @return The number of rows
     */
    public int getNbRows(){return this.nbRows;}

    /**
     * @return The number of columns
     */
    public int getNbColumns(){return this.nbColumns;}

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Set the value of the matrix
     * @param dblArr The new value of the matrix
     */
    private void setValue(double[][] dblArr){
        this.value = dblArr;
    }

    /**
     * The new value at indices i,j in the matrix
     * @param i The row index
     * @param j The column index
     * @param val The new value at indices i,j
     */
    public void setValue(int i, int j, double val){
        this.value[i][j] = val;
    }

    /*****************************************
     *
     * Specific matrices
     *
     *****************************************/
    /**
     * Build a matrix with every element is a random num number between 0 and 1
     * @param M The matrix to random
     * @return A matrix with every element is a random num number between 0 and 1
     */
    public static Matrix randomize(Matrix M){
        return Matrix.apply(x -> x + PopsMath.rand(), M);
    }

    /**
     * Build a matrix with every element is 1
     * @param sizeI The number of rows
     * @param sizeJ The number of columns
     * @return A matrix with every element is 1
     */
    public static Matrix ones(int sizeI, int sizeJ){
        return Matrix.apply(x -> x + 1, new Matrix(sizeI, sizeJ));
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Extract submatrix from A
     * @param A
     * @param startI
     * @param startJ
     * @param sizeI
     * @param sizeJ
     * @return
     */
    public static Matrix extract(Matrix A, int startI, int startJ, int sizeI, int sizeJ){
        double[][] res = new double[sizeI][sizeJ];
        int endI = startI + sizeI;
        int endJ =  startJ + sizeJ;
        if( endI <= A.getNbRows() && endJ <= A.getNbColumns() ){
            for (int i = startI; i < endI; i++){
                for (int j = startJ; j < endJ; j++){
                    res[i - startI][j - startJ] = A.getValue(i,j);
                }
            }
        } else {
            System.out.println("Cannot extract specified submatrix from A.");
        }
        return Matrix.toMatrix(res);
    }

    /**
     * Add padding all over matrix A
     * @param A
     * @param sizePadding
     * @return
     */
    public static Matrix addPadding(Matrix A, int sizePadding){

        double[][] res = new double[A.getNbRows() + 2 * sizePadding][A.getNbColumns() + 2 * sizePadding];

        for (int i = - sizePadding; i < A.getNbRows() + sizePadding; i++){
            for (int j = - sizePadding; j < A.getNbColumns() + sizePadding; j++){
                if (i < 0 || j < 0 || i >= A.getNbRows() || j >= A.getNbColumns()){
                    res[i + sizePadding][j + sizePadding] = 0;
                } else {
                    res[i + sizePadding][j + sizePadding] = A.getValue(i, j);
                }
            }
        }

        return Matrix.toMatrix(res);
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    public static Matrix toMatrix(double[][] fltArr){
        Matrix M = null;
        if (fltArr != null){
            M = new Matrix(fltArr.length, fltArr[0].length);
            M.setValue(fltArr);
        }
        return M;
    }

    @Override
    public String toString(){
        String msg = " ";
        for (int i = 0; i < this.getNbRows(); i++){
            for (int j = 0; j < this.getNbColumns(); j++){
                msg += String.format("%6.3f", this.getValue(i,j)) + "  ";
            }
            msg += " \n ";
        }
        return msg;
    }
}
