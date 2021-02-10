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
package fr.pops.popsmath;

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
     * Getters
     *
     *****************************************/
    public double getValue(int i, int j){return this.value[i][j];}

    public int getNbRows(){return this.nbRows;}

    public int getNbColumns(){return this.nbColumns;}

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    private void setValue(double[][] dblArr){
        this.value = dblArr;
    }

    public void setValue(int i, int j, double val){
        this.value[i][j] = val;
    }

    /*****************************************
     *
     * Specific matrices
     *
     *****************************************/
    public static Matrix randomize(Matrix M){
        return PopsMath.apply(x -> x + PopsMath.rand(), M);
    }

    public static Matrix ones(int sizeI, int sizeJ){
        return PopsMath.apply(x -> x + 1, new Matrix(sizeI, sizeJ));
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
