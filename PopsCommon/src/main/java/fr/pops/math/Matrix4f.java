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
 * Name: Matrix4f.java
 *
 * Description: Class defining 4 by 4 matrices
 *              with float precision.
 *
 * Author: Charles MERINO
 *
 * Date: 18/08/2021
 *
 ******************************************************************************/
package fr.pops.math;

import java.util.function.Function;

public class Matrix4f extends Matrixf {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    public final static int SIZE = 4;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Matrix4f(){
        super(SIZE, SIZE);
    }

    /*****************************************
     *
     * Math
     *
     *****************************************/
    /* *
     * Apply a function to every element of the matrix 4f M
     * */
    public static Matrix4f apply(Function<Float, Float> f, Matrix4f M){
        float[] buffer = new float[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++){
            buffer[i] = f.apply(M.value[i]);
        }
        return Matrix4f.toMatrix4f(buffer);
    }

    /* *
     * Return a new Vector corresponding to -1 * M
     * */
    public static Matrix4f negate(Matrix4f M){ return Matrix4f.apply(x -> (-1) * x, M); }

    /**
     * Translation matrix with the shape:
     *             1 0 0 x
     *             0 1 0 y
     *             0 0 1 z
     *             0 0 0 1
     *
     * @param translate The amount of translation
     * @return The translation matrix
     */
    public static Matrix4f translate(Vector3f translate){
        Matrix4f res = Matrix4f.identity();
        res.set(0, 3, translate.x());
        res.set(1, 3, translate.y());
        res.set(2, 3, translate.z());
        return res;
    }

    /**
     * Rotate the axis
     * @param angle The angle of rotation in rad
     * @param axis The axis to rotate
     * @return The Matrix4f representing the rotation matrix around the input axis
     */
    public static Matrix4f rotate(float angle, Vector3f axis){
        Matrix4f res = Matrix4f.identity();
        Vector3f a1 = Vector3f.safeNormalize(axis); // axis normalized
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        // 1st row
        res.set(0, 0, cos + a1.x() * a1.x() * (1 - cos));
        res.set(0, 1, a1.x() * a1.y() * ( 1 - cos) - a1.z() * sin);
        res.set(0, 2, a1.x() * a1.z() * ( 1 - cos) + a1.y() * sin);

        // 2nd row
        res.set(1, 0, a1.y() * a1.x() * (1 - cos) + a1.z() * sin);
        res.set(1, 1, cos + a1.y() * a1.y() * (1 - cos));
        res.set(1, 2, a1.y() * a1.z() * (1 - cos) - a1.x() * sin);

        // 3rd row
        res.set(2, 0, a1.z() * a1.x() * (1 - cos) - a1.y() * sin);
        res.set(2, 1, a1.z() * a1.y() * (1 - cos) + a1.x() * sin);
        res.set(2, 2, cos + a1.z() * a1.z() * (1 - cos));

        return res;
    }

    /**
     * Scale the matrix
     * @param scale The amount of scaling
     * @return The scaling matrix
     */
    public static Matrix4f scale(Vector3f scale){
        Matrix4f res = Matrix4f.identity();
        res.set(0, 0, scale.x());
        res.set(1, 1, scale.y());
        res.set(2, 2, scale.z());
        return res;
    }

    /**
     * Matrix multiplication
     * @param A First matrix
     * @param B Second matrix
     * @return A matrix representing A * B
     */
    public static Matrix4f times(Matrix4f A, Matrix4f B){
        Matrix4f res = Matrix4f.identity();
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                res.set(i, j, A.get(i, 0) * B.get(0, j) +
                                   A.get(i, 1) * B.get(1, j) +
                                   A.get(i, 2) * B.get(2, j) +
                                   A.get(i, 3) * B.get(3, j)
                        );
            }
        }
        return res;
    }

    /**
     * Transform a matrix
     * @param position The new position of the matrix
     * @param rotation A vector containing the angles of rotation to apply
     *                 according each axis
     * @param scale The scale of the matrix
     * @return The transform of the matrix
     */
    public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale){
        // Build transformation matrices
        Matrix4f translationMatrix = Matrix4f.translate(position);
        Matrix4f rotXMatrix = Matrix4f.rotate(rotation.x(), Vector3f.X_AXIS);
        Matrix4f rotYMatrix = Matrix4f.rotate(rotation.y(), Vector3f.Y_AXIS);
        Matrix4f rotZMatrix = Matrix4f.rotate(rotation.z(), Vector3f.Z_AXIS);
        Matrix4f scaleMatrix = Matrix4f.scale(scale);
        Matrix4f rotationMatrix = Matrix4f.times(rotXMatrix, Matrix4f.times(rotYMatrix, rotZMatrix));
        // Return transformation
        return Matrix4f.times(translationMatrix, Matrix4f.times(rotationMatrix, scaleMatrix));
    }

    /**
     * Projection matrix
     * @param fov Field of view in radian
     * @param aspect Window aspect ration
     * @param near Near distance
     * @param far Far distance
     * @return The projection matrix4f
     */
    public static Matrix4f projection(float fov, float aspect, float near, float far){
        Matrix4f res = Matrix4f.identity();
        float tan =  (float) Math.tan(fov / 2);
        res.set(0, 0, 1.0f / (aspect * tan));
        res.set(1, 1, 1.0f / tan);
        res.set(2, 2, -((far + near) / (far - near)));
        res.set(2, 3, -((2 * far * near) / (far - near)));
        res.set(3, 2, -1);
        res.set(3, 3, 0);
        return res;
    }

    /**
     * Transform a matrix
     * @param position The new position of the matrix
     * @param rotation A vector containing the angles of rotation to apply
     *                 according to each axis
     * @return The transform of the matrix
     */
    public static Matrix4f view(Vector3f position, Vector3f rotation){
        // Build transformation matrices
        Vector3f negMatrix = Vector3f.negate(position);
        Matrix4f translationMatrix = Matrix4f.translate(negMatrix);
        Matrix4f rotXMatrix = Matrix4f.rotate(rotation.x(), Vector3f.X_AXIS);
        Matrix4f rotYMatrix = Matrix4f.rotate(rotation.y(), Vector3f.Y_AXIS);
        Matrix4f rotZMatrix = Matrix4f.rotate(rotation.z(), Vector3f.Z_AXIS);
        Matrix4f rotationMatrix = Matrix4f.times(rotZMatrix, Matrix4f.times(rotYMatrix, rotXMatrix));
        // Return view
        return Matrix4f.times(translationMatrix, rotationMatrix);
    }

    /*****************************************
     *
     * Standard matrices
     *
     *****************************************/
    /**
     * Identity matrix
     * @return The identity matrix
     */
    public static Matrix4f identity(){
        return Matrix4f.toMatrix4f(new float[]{
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        });
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Cast the given float array into a Matrix4f
     * @param arr The array to cast
     * @return Null if array size is not 16
     *         A Matrix4f otherwise
     */
    public static Matrix4f toMatrix4f(float[] arr){
        if(arr.length != SIZE * SIZE) return null;
        Matrix4f cast = new Matrix4f();
        cast.setValue(arr);
        return cast;
    }
}
