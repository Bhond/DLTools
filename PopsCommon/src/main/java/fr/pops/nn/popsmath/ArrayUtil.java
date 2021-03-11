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
 * Name: ArrayUtil.java
 *
 * Description: Class defining the basic N-Dimensional array transforms.
 *
 * Author: Charles MERINO
 *
 * Date: 15/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.popsmath;

import fr.pops.nn.ndarray.BaseNDArray;
import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.ndarray.Shape;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public abstract class ArrayUtil {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    public enum ordering {ROW_WISE, COLUMN_WISE}

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Turn the 2D input array into a flat one
     * row wise
     * @param arr The input array to flatten
     * @return A flatten representation of the array
     */
    public static double[] flatten(double[][] arr){
        if (arr.length == 0 || arr[0].length == 0){
            System.out.println("Null Array in ArrayUtil.flatten");
            return new double[0];
        }
        int count = 0;
        double[] res = new double[arr.length * arr[0].length];
        for (double[] doubles : arr) {
            System.arraycopy(doubles, 0, res, count, doubles.length);
            count += doubles.length;
        }
        return res;
        }

    /**
     * Turn the 2D input array into a flat one
     * column wise
     * @param arr The input array to flatten
     * @return A flatten representation of the array
     */
    @Deprecated
    public static double[] flattenFUnsafe(double[][] arr){
        return new double[]{};
    }

    /**
     * @param f Function to apply to each array components
     * @param arr The input array
     * @return An array representing the input array with a function applied to each of its elements
     */
    public static INDArray apply(Function<Double, Double> f, INDArray arr){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.apply");
            return new BaseNDArray(new double[0], 1);
        }
        double[] res = new double[arr.getShape().getSize()];
        for (int i = 0; i < arr.getShape().getSize(); i++){
            res[i] = f.apply(arr.getData()[i]);
        }
        return new BaseNDArray.BaseNDArrayBuilder().withData(res)
                                                   .withShape(arr.getShape())
                                                   .build();
    }

    /**
     * @param f Function to apply to each array components
     * @param arr The input array
     * @return An array representing the input array with a function applied to each of its elements
     */
    public static double[] apply(Function<Double, Double> f, double[] arr){
        if (arr.length == 0){
            return new double[0];
        }
        double[] res = new double[arr.length];
        for (int i = 0; i < arr.length; i++){
            res[i] = f.apply(arr[i]);
        }
        return res;
    }

    /**
     * Transpose array arr
     * @param arr The input array to transpose
     * @return The input array transposed
     */
    public static INDArray transpose(INDArray arr){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.transpose");
            return new BaseNDArray.BaseNDArrayBuilder().zeros(arr.getShape()).build();
        }
        int m = arr.getShape().getXAxisLength();
        int n = arr.getShape().getYAxisLength();
        double[] res = new double[arr.getShape().getSize()];

        int offset = 0;
        int count = 0;
        for (int i = 0; i < arr.getShape().getSize(); i++){
            res[i] = arr.getData()[count];
            if (count + n >= arr.getShape().getSize()){
                offset++;
                count = offset;
            } else {
                count += n;
            }
        }
        return new BaseNDArray(res, n, m);
    }

    /**
     * @param arr The input array
     * @return An array representing the input array with a function applied to each of its elements
     */
    public static INDArray negate(INDArray arr){ return ArrayUtil.apply(x -> (-1) * x, arr); }

    /**
     * TODO: complete this method for CNN and 3D arrays like double[][][]
     * @param arr1 First INDArray
     * @param arr2 Second INDArray
     * @return A column array representing the dot product of arr1 and arr2
     */
    public static INDArray dot(INDArray arr1, INDArray arr2) {
        if (arr1.getShape().getYAxisLength() != arr2.getShape().getXAxisLength()){
            System.out.println("In ArrayUtil.dot: the first array must have the same number of columns than the number of rows of the second array.");
        }

        int m = arr1.getShape().getXAxisLength();
        int n = arr1.getShape().getYAxisLength();
        int p = arr2.getShape().getYAxisLength();

        double[] res = new double[m * p];

        // Transpose the 2nd array
        INDArray tArr2 = ArrayUtil.transpose(arr2);

        // Build the lists of rows
        List<double[]> arr1Rows = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < m; i++) {
            double[] buffer = new double[n];
            System.arraycopy(arr1.getData(), count, buffer, 0, n);
            arr1Rows.add(buffer);
            count += n;
        }
        List<double[]> tArr2Rows = new ArrayList<>();
        count = 0;
        for (int j = 0; j < p; j++) {
            double[] buffer = new double[n];
            System.arraycopy(tArr2.getData(), count, buffer, 0, n);
            tArr2Rows.add(buffer);
            count += n;
        }

        // Loop over the rows
        count = 0;
        for (double[] rowArr1 : arr1Rows){
            for (double[] rowArr2 : tArr2Rows){
                double[] buffer = ArrayUtil.hadamard(rowArr1, rowArr2);
                res[count] = ArrayUtil.sum(buffer);
                count++;
            }
        }
        return new BaseNDArray(res,  m == 1 && n > 1 ? p : m, m == 1 && n > 1 ? m : p);
    }

    /**
     * Both arrays must have the same size and shape
     * @param arr1 First INDArray
     * @param arr2 Second INDArray
     * @return An array representing the addition of the elements in arr1 and arr2 component-wise
     */
    public static INDArray add(INDArray arr1, INDArray arr2){
        if (arr1.getShape().getSize() == 0 || arr2.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.add");
            return new BaseNDArray(new double[0],1);
        } else if (arr1.getShape().getSize() != arr2.getShape().getSize() && arr1.getShape() != arr2.getShape()){
            System.out.println("Arrays cannot be added. Must have the same size and shape.");
            return new BaseNDArray(new double[0], 1);
        }

        double[] res = new double[arr1.getShape().getSize()];
        for (int i = 0; i < arr1.getShape().getSize(); i++){
            res[i] = arr1.getData()[i] + arr2.getData()[i];
        }
        return new BaseNDArray.BaseNDArrayBuilder().withData(res)
                                                   .withShape(arr1.getShape())
                                                   .build();
    }

    /**
     * Both arrays must have the same size and shape
     * @param arr1 First INDArray
     * @param arr2 Second INDArray
     * @return A INDArray representing the Hadamard product of the elements in arr1 and arr2
     *         It is a multiplication component-wise
     */
    public static INDArray hadamard(INDArray arr1, INDArray arr2){
        if (arr1.getShape().getSize() == 0 || arr2.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.hadamard");
            return new BaseNDArray(new double[0],1);
        } else if (arr1.getShape().getSize() != arr2.getShape().getSize() && arr1.getShape() != arr2.getShape()){
            System.out.println("Hadamard cannot be computed. Must have the same size and shape.");
            return new BaseNDArray(new double[0], 1);
        }

        double[] res = new double[arr1.getShape().getSize()];
        for (int i = 0; i < arr1.getShape().getSize(); i++){
            res[i] = arr1.getData()[i] * arr2.getData()[i];
        }
        return new BaseNDArray.BaseNDArrayBuilder().withData(res)
                                                   .withShape(arr1.getShape())
                                                   .build();
    }

    /**
     * Both arrays must have the same size and shape
     * @param arr1 First double array
     * @param arr2 Second double array
     * @return An double array representing the Hadamard product of the elements in arr1 and arr2
     *         It is a multiplication component-wise
     */
    public static double[] hadamard(double[] arr1, double[] arr2){
        if (arr1.length == 0 || arr2.length == 0){
            System.out.println("Null Array in ArrayUtil.hadamard");
            return new double[0];
        }

        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] * arr2[i];
        }
        return res;
    }

    /**
     * TODO: Deal with odd numbers fo padding
     * Add padding all around the input array in the X and Y axis directions
     * @param arr The array to add padding to
     * @param paddingSize The size of the padding to add
     * @return The padded array
     */
    public static INDArray addPadding(INDArray arr, int paddingSize){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.addPadding");
            return new BaseNDArray(new double[0],1);
        }
        // Build final shape
        int xAxisLength = arr.getShape().getXAxisLength() + 2 * paddingSize;
        int yAxisLength = arr.getShape().getYAxisLength() + 2 * paddingSize;
        // Initialize data
        double[] buffer = new double[xAxisLength * yAxisLength * arr.getShape().getZAxisLength()];

        int counter = paddingSize * yAxisLength + paddingSize;
        int arrStep = 0;
        for (int i = 0; i < arr.getShape().getXAxisLength(); i++){
            System.arraycopy(arr.getData(), arrStep, buffer, counter, arr.getShape().getYAxisLength());
            counter += arr.getShape().getYAxisLength() + 2 * paddingSize;
            arrStep += arr.getShape().getYAxisLength();
        }
        return new BaseNDArray(buffer, xAxisLength, yAxisLength, arr.getShape().getZAxisLength());
    }

    /**
     * Add padding around the input array in the X and Y axis directions
     * The input array is not necessarily centered in the final array
     * @param arr The array to add padding to
     * @param paddingSize The size of the padding to add
     * @param offset The index offset place the array in the padded array
     *               It's the top left hand starting index in a matrix analogy
     * @return The padded array
     */
    public static INDArray addPadding(INDArray arr, int paddingSize, int offset){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.addPadding");
            return new BaseNDArray(new double[0],1);
        }
        // Build final shape
        int xAxisLength = arr.getShape().getXAxisLength() + 2 * paddingSize;
        int yAxisLength = arr.getShape().getYAxisLength() + 2 * paddingSize;

        // Initialize data
        double[] buffer = new double[xAxisLength * yAxisLength * arr.getShape().getZAxisLength()];

        // Loop over every lines
        int counter = offset;
        int arrStep = 0;
        for (int i = 0; i < arr.getShape().getXAxisLength(); i++){
            System.arraycopy(arr.getData(), arrStep, buffer, counter, arr.getShape().getYAxisLength());
            counter += arr.getShape().getYAxisLength() + 2 * paddingSize;
            arrStep += arr.getShape().getYAxisLength();
        }
        return new BaseNDArray(buffer, xAxisLength, yAxisLength, arr.getShape().getZAxisLength());
    }

    /**
     * Add padding around the input flatten matrix
     * The input array is not necessarily centered in the final array depending on the given offset
     * @param dblArr The array to add padding to
     * @param nbRows The number of rows in the input array
     * @param nbCols The number of columns in the input array
     * @param paddingSize The size of the padding to add
     * @param offset The index of the place of the dblArray in the padded array
     *               It's the top left hand starting index in a matrix analogy
     * @return The padded array
     */
    public static double[] addPaddingFlattened2DArray(double[] dblArr,  int nbRows, int nbCols, int paddingSize, int offset){
        if (nbRows * nbCols == 0){
            System.out.println("Null Array in ArrayUtil.addPaddingFlatten2DArray");
            return new double[0];
        }
        // Build final shape
        int xAxisLength = nbRows + 2 * paddingSize;
        int yAxisLength = nbCols + 2 * paddingSize;
        // Initialize data
        double[] res = new double[xAxisLength * yAxisLength];

        int counter = offset;
        int arrStep = 0;
        for (int i = 0; i < nbRows; i++){
            System.arraycopy(dblArr, arrStep, res, counter, nbCols);
            counter += nbCols + 2 * paddingSize - 1;
            arrStep += nbCols;
        }
        return res;
    }

    /**
     * Convolution of arr by filter
     * Assuming, for now,  arr.shape.xAxisLength == arr.shape.yAxisLength && filter.shape.xAxisLength == filter.shape.yAxisLength
     * @param arr The array to convolve
     * @param filter The filter to make the convolution
     * @param stride The stride used to slide the filter
     * @return The convoluted array
     */
    public static INDArray convolve(INDArray arr, INDArray filter, int stride){
        if (arr.getShape().getSize() == 0 || filter.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.convolve");
            return new BaseNDArray(new double[0],1);
        } else if (arr.getShape().getXAxisLength() != arr.getShape().getYAxisLength()
                || filter.getShape().getXAxisLength() != filter.getShape().getYAxisLength()) {
            System.out.println("Each Arr and filter channels must be squared in ArrayUtil.convolve");
            return new BaseNDArray(new double[0],1);
        } else if (arr.getShape().getZAxisLength() != filter.getShape().getZAxisLength()){
            System.out.println("Arr and filter must have the same depth in ArrayUtil.convolve");
            return new BaseNDArray(new double[0],1);
        }
        
        // Initialize the result
        int nbSlides = ((arr.getShape().getXAxisLength() - filter.getShape().getXAxisLength()) / stride) + 1;
        int xAxisLength = nbSlides;
        int yAxisLength = nbSlides;
        int zAxisLength = 1;
        double[] res = new double[xAxisLength * yAxisLength];

        // Split the arr into the channels
        List<double[]> arrChannels = new ArrayList<>();
        int arrCounter = 0;
        for (int i = 0; i < arr.getShape().getZAxisLength(); i++){
            double[] buffer = new double[arr.getShape().getXAxisLength() * arr.getShape().getYAxisLength()];
            System.arraycopy(arr.getData(), arrCounter, buffer, 0, buffer.length);
            arrChannels.add(buffer);
            arrCounter += buffer.length;
        }

        // Split the filter into the channels
        List<double[]> filterChannels = new ArrayList<>();
        int filterCounter = 0;
        for (int i = 0; i < arr.getShape().getZAxisLength(); i++){
            double[] buffer = new double[filter.getShape().getXAxisLength() * filter.getShape().getYAxisLength()];
            System.arraycopy(filter.getData(), filterCounter, buffer, 0, buffer.length);
            filterChannels.add(buffer);
            filterCounter += buffer.length;
        }

        // Loop over the channels of the array
        int paddingSize = (arr.getShape().getXAxisLength() - filter.getShape().getXAxisLength());
        paddingSize = paddingSize % 2 == 0 ? paddingSize / 2 : (int) (Math.floor((double) paddingSize / 2) + 1);
        int index = 0;
        for (int i = 0; i < arrChannels.size(); i++) {
            int offset = 0;
            for (int s = 0; s < nbSlides * nbSlides; s++){
                double[] buffer = ArrayUtil.addPaddingFlattened2DArray(filterChannels.get(i),
                        filter.getShape().getXAxisLength(),
                        filter.getShape().getYAxisLength(),
                        paddingSize,
                        offset);
                double[] hadamardArrFilter = ArrayUtil.hadamard(arrChannels.get(i), buffer);
                res[index] += ArrayUtil.sum(hadamardArrFilter);
                offset += offset != 0 && (index + 1) % nbSlides == 0 ? filter.getShape().getXAxisLength() : stride;
                index++;
            }
            index = 0;
        }
        return new BaseNDArray(res, xAxisLength, yAxisLength, zAxisLength);
    }

    /**
     * Convolution of a specific channel of the arr by filter
     * Assuming, for now,  arr.shape.xAxisLength == arr.shape.yAxisLength && filter.shape.xAxisLength == filter.shape.yAxisLength
     * @param arr The array to convolve
     * @param filter The filter to make the convolution
     * @param stride The stride used to slide the filter
     * @return The convoluted array
     */
    public static INDArray convolve(INDArray arr, int channel, INDArray filter, int stride){
        if (arr.getShape().getSize() == 0 || filter.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.convolve");
            return new BaseNDArray(new double[0],1);
        } else if (arr.getShape().getXAxisLength() != arr.getShape().getYAxisLength()
                || filter.getShape().getXAxisLength() != filter.getShape().getYAxisLength()) {
            System.out.println("Each Arr and filter channels must be squared in ArrayUtil.convolve");
            return new BaseNDArray(new double[0],1);
        }

        // Initialize the result
        int nbSlides = ((arr.getShape().getXAxisLength() - filter.getShape().getXAxisLength()) / stride) + 1;
        int xAxisLength = nbSlides;
        int yAxisLength = nbSlides;
        int zAxisLength = 1;
        double[] res = new double[xAxisLength * yAxisLength];

        // Split the arr into the channels
        List<double[]> arrChannels = new ArrayList<>();
        int arrCounter = 0;
        for (int i = 0; i < arr.getShape().getZAxisLength(); i++){
            double[] buffer = new double[arr.getShape().getXAxisLength() * arr.getShape().getYAxisLength()];
            System.arraycopy(arr.getData(), arrCounter, buffer, 0, buffer.length);
            arrChannels.add(buffer);
            arrCounter += buffer.length;
        }

        // Loop over the channels of the array
        int paddingSize = (arr.getShape().getXAxisLength() - filter.getShape().getXAxisLength());
        paddingSize = paddingSize % 2 == 0 ? paddingSize / 2 : (int) (Math.floor((double) paddingSize / 2) + 1);
        int index = 0;
        int offset = 0;
        for (int s = 0; s < nbSlides * nbSlides; s++){
            double[] buffer = ArrayUtil.addPaddingFlattened2DArray(arr.getData(),
                    filter.getShape().getXAxisLength(),
                    filter.getShape().getYAxisLength(),
                    paddingSize,
                    offset);
            double[] hadamardArrFilter = ArrayUtil.hadamard(arrChannels.get(channel), buffer);
            res[index] += ArrayUtil.sum(hadamardArrFilter);
            offset += offset != 0 && (index + 1) % nbSlides == 0 ? filter.getShape().getXAxisLength() : stride;
            index++;
        }
        return new BaseNDArray(res, xAxisLength, yAxisLength, zAxisLength);
    }

    /**
     * Max pooling function to reduce the number of elements in the input array
     * @param arr The array to reduce the size of
     * @param filterShape The shape of the identity filter to apply
     * @param stride The stride used to slide the filter
     * @return The pooled array
     */
    public static INDArray maxPool(INDArray arr, Shape filterShape, int stride){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.maxPool");
            return new BaseNDArray(new double[0],1);
        }  else if (filterShape.getXAxisLength() != filterShape.getYAxisLength()){
            System.out.println("ArrayUtil.maxPool doesn't know how to deal with non squared filters");
            return new BaseNDArray(new double[0],1);
        }

        // Initialize the result
        int filterSize = filterShape.getXAxisLength(); // || FilterShape.getYAxisLength(), doesn't matter, sizes have been previously checked
        int nbSlides = ((arr.getShape().getXAxisLength() - filterSize) / stride) + 1;
        int xAxisLength = nbSlides;
        int yAxisLength = nbSlides;
        int zAxisLength = arr.getShape().getZAxisLength();
        double[] res = new double[xAxisLength * yAxisLength * zAxisLength];

        // Split the arr into the channels
        List<double[]> channels = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < arr.getShape().getZAxisLength(); i++){
            double[] buffer = new double[arr.getShape().getXAxisLength() * arr.getShape().getYAxisLength()];
            System.arraycopy(arr.getData(), counter, buffer, 0, buffer.length);
            channels.add(buffer);
            counter += buffer.length;
        }

        // Loop over the channels
        INDArray filter = new BaseNDArray.BaseNDArrayBuilder().ones(filterSize, filterSize).build();
        int paddingSize = (arr.getShape().getXAxisLength() - filter.getShape().getXAxisLength());
        paddingSize = paddingSize % 2 == 0 ? paddingSize / 2 : (int) (Math.floor((float)paddingSize / 2) + 1);
        int index = 0;
        for (double[] channel : channels){
            int offset = 0;
            for (int i = 0; i < nbSlides * nbSlides; i++){
                double[] buffer = ArrayUtil.addPaddingFlattened2DArray(filter.getData(),
                        filter.getShape().getXAxisLength(),
                        filter.getShape().getYAxisLength(),
                        paddingSize,
                        offset);
                double[] hadamardArrFilter = ArrayUtil.hadamard(channel, buffer);
                res[index] = ArrayUtil.max(hadamardArrFilter);
                offset += offset != 0 && (index + 1) % nbSlides == 0 ? filter.getShape().getXAxisLength() : stride;
                index++;
            }
        }

        return new BaseNDArray(res, xAxisLength, yAxisLength, zAxisLength);
    }

    /**
     * Max pooling function to reduce the number of elements in the input array
     * @param arr The array to reduce the size of
     * @param filterShape The shape of the identity filter to apply
     * @param stride The stride used to slide the filter
     * @return The pooled array
     */
    public static AbstractMap.SimpleEntry<Integer[], INDArray> maskedMaxPool(INDArray arr, Shape filterShape, int stride){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.maxPool");
            return new AbstractMap.SimpleEntry<>(new Integer[0], new BaseNDArray(new double[0],1));
        }  else if (filterShape.getXAxisLength() != filterShape.getYAxisLength()){
            System.out.println("ArrayUtil.maxPool doesn't know how to deal with non squared filters");
            return new AbstractMap.SimpleEntry<>(new Integer[0], new BaseNDArray(new double[0],1));
        }

        // Initialize the result
        int filterSize = filterShape.getXAxisLength(); // || FilterShape.getYAxisLength(), doesn't matter, sizes have been previously checked
        int nbSlides = ((arr.getShape().getXAxisLength() - filterSize) / stride) + 1;
        int xAxisLength = nbSlides;
        int yAxisLength = nbSlides;
        int zAxisLength = arr.getShape().getZAxisLength();
        double[] res = new double[xAxisLength * yAxisLength * zAxisLength];
        int maskChannelIndex = 0;
        Integer[] mask = new Integer[xAxisLength * yAxisLength * zAxisLength];

        // Split the arr into the channels
        List<double[]> channels = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < arr.getShape().getZAxisLength(); i++){
            double[] buffer = new double[arr.getShape().getXAxisLength() * arr.getShape().getYAxisLength()];
            System.arraycopy(arr.getData(), counter, buffer, 0, buffer.length);
            channels.add(buffer);
            counter += buffer.length;
        }

        // Loop over the channels
        INDArray filter = new BaseNDArray.BaseNDArrayBuilder().ones(filterSize, filterSize).build();
        int paddingSize = (arr.getShape().getXAxisLength() - filter.getShape().getXAxisLength());
        paddingSize = paddingSize % 2 == 0 ? paddingSize / 2 : (int) (Math.floor((float)paddingSize / 2) + 1);
        int index = 0;
        for (double[] channel : channels){
            int offset = 0;
            for (int i = 0; i < nbSlides * nbSlides; i++){
                double[] buffer = ArrayUtil.addPaddingFlattened2DArray(filter.getData(),
                        filter.getShape().getXAxisLength(),
                        filter.getShape().getYAxisLength(),
                        paddingSize,
                        offset);
                double[] hadamardArrFilter = ArrayUtil.hadamard(channel, buffer);
                AbstractMap.SimpleEntry<Integer, Double> maskedMaxPair = ArrayUtil.maskedMax(hadamardArrFilter);
                res[index] = maskedMaxPair.getKey();
                mask[index] = maskedMaxPair.getKey() + maskChannelIndex * channel.length;
                offset += offset != 0 && (index + 1) % nbSlides == 0 ? filter.getShape().getXAxisLength() : stride;
                index++;
            }
            maskChannelIndex++;
        }

        return new AbstractMap.SimpleEntry<>(mask,
                          new BaseNDArray.BaseNDArrayBuilder().withData(res).withShape(xAxisLength, yAxisLength, zAxisLength).build());
    }

    /**
     * Pass the array through the mask
     * @param arr The array to filter
     * @param mask The mask to pass through
     * @return The arr passed through the mask
     */
    public static INDArray invPool(INDArray arr, Integer[] mask, Shape outShape){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.invPool");
            return new BaseNDArray(new double[0],1);
        } else if (arr.getShape().getXAxisLength() != arr.getShape().getYAxisLength()){
            System.out.println("ArrayUtil.invPool doesn't know how to deal with non squared arrays");
            return new BaseNDArray(new double[0],1);
        } else if (arr.getShape().getSize() != mask.length) {
            System.out.println("Arr and mask must have the same size in ArrayUtil.invPool.");
            return new BaseNDArray(new double[0],1);
        }

        // Initialisation
        double[] res = new double[outShape.getSize()];

        // Loop over the elements of the output INDArray
        for (int i = 0; i < mask.length; i++){
            res[mask[i]] = arr.getData()[i];
        }

        return new BaseNDArray.BaseNDArrayBuilder().withData(res).withShape(outShape).build();
    }


    /**
     * @param arr The input array
     * @return A double representing the product of the array elements
     */
    public static int prod(int[] arr){
        int res = 1;
        for (int value : arr) {
            res *= value;
        }
        return res;
    }

    /**
     * @param arr The input array
     * @return A double representing the product of the array elements
     */
    public static double prod(INDArray arr){
        double res = 1;
        for (double value : arr.getData()){
            res *= value;
        }
        return res;
    }

    /**
     * @param arr The input array
     * @return A double representing the sum of the array elements
     */
    public static double sum(INDArray arr){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.sum");
            return 0;
        }
        double res = 0;
        for (int i = 0; i < arr.getShape().getSize(); i++){
            res += arr.getData()[i];
        }
        return res;
    }

    /**
     * @param arr The input array
     * @return A double representing the sum of the array elements
     */
    public static double sum(double[] arr){
        if (arr.length== 0){
            System.out.println("Null Array in ArrayUtil.sum");
            return 0;
        }
        double res = 0;
        for (double v : arr) {
            res += v;
        }
        return res;
    }

    /**
     * @param arr Array to diagonalize
     * @return A INDArray representing a matrix of size arr.length * arr.length
     *         with the input array elements in the diagonal
     */
    public static INDArray diag(INDArray arr){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.diag");
            return new BaseNDArray(new double[0], 1);
        }
        int count = 0;
        double[] res = new double[arr.getShape().getSize() * arr.getShape().getSize()];
        for (int i = 0; i < arr.getShape().getSize() - 1; i++){
            res[count] = arr.getData()[i];
            count += arr.getShape().getSize() + 1;
        }
        res[res.length - 1] = arr.getData()[arr.getShape().getSize() - 1];
        return new BaseNDArray(res, arr.getShape().getSize() * arr.getShape().getSize());
    }

    /**
     * @param arr The input array
     * @return A double representing the maximal value of the array elements
     */
    public static double max(INDArray arr){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.max");
            return 0;
        }
        double res = arr.getData()[0];
        for(int i = 1; i < arr.getShape().getSize(); i++){
            res = Math.max(res, arr.getData()[i]);
        }
        return res;
    }

    /**
     * @param arr The input array
     * @return A double representing the maximal value of the array elements
     */
    public static double max(double[] arr){
        if (arr.length == 0){
            System.out.println("Null Array in ArrayUtil.max");
            return 0;
        }
        double res = arr[0];
        for(int i = 1; i < arr.length; i++){
            res = Math.max(res, arr[i]);
        }
        return res;
    }

    /**
     * This method allows to retrieve the max value of an array with its index
     * Consistent with the Math.max method
     * @param arr The input array
     * @return A pair of double representing the maximal value of the array elements
     *         and the index of this element
     */
    public static AbstractMap.SimpleEntry<Integer, Double> maskedMax(double[] arr){
        if (arr.length == 0){
            System.out.println("Null Array in ArrayUtil.max");
            return new AbstractMap.SimpleEntry<>(0, 0.0);
        }
        int index = 0;
        double res = arr[index];
        for(int i = 1; i < arr.length; i++){
            if (res <= arr[i]){
                index = i;
                res = arr[i];
            }
        }
        return new AbstractMap.SimpleEntry<>(index, res);
    }

    /**
     * @param arr The input array
     * @return A double representing the minimal value of the array elements
     */
    public static double min(INDArray arr){
        if (arr.getShape().getSize() == 0){
            System.out.println("Null Array in ArrayUtil.min");
            return 0;
        }
        double res = arr.getData()[0];
        for(int i = 1; i < arr.getShape().getSize(); i++){
            res = Math.min(res, arr.getData()[i]);
        }
        return res;
    }

    /**
     * @param arr The input array
     * @return A double representing the average value of the array elements
     */
    public static double average(INDArray arr){
        return sum(arr) / (arr.getShape().getSize() != 0 ? arr.getShape().getSize() : 1); // Avoid dividing by 0. sum already returns 0
    }

}
