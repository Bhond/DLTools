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
 * Name: Vector3f.java
 *
 * Description: Class defining the 3D vectors with floating precision.
 *
 * Author: Charles MERINO
 *
 * Date: 15/08/2021
 *
 ******************************************************************************/
package fr.pops.math;

import java.util.HashMap;
import java.util.function.Function;

public class Vector3f extends Vectorf {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    public final static Vector3f X_AXIS = new Vector3f(1.0f, 0.0f, 0.0f);
    public final static Vector3f Y_AXIS = new Vector3f(0.0f, 1.0f, 0.0f);
    public final static Vector3f Z_AXIS = new Vector3f(0.0f, 0.0f, 1.0f);

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
     /**
      * Standard ctor
     */
    public Vector3f() {
        super(3);
    }

    /**
     * Ctor
     * @param x The x axis value of the vector
     * @param y The y axis value of the vector
     * @param z The z axis value of the vector
     */
    public Vector3f(float x, float y, float z) {
        super(x, y, z);
    }

    /*****************************************
     *
     * Math
     *
     *****************************************/
    /**
     * @return A new Vector corresponding to -u
     */
    public static Vector3f negate(Vector3f u){
        return new Vector3f(-u.x(), -u.y(), -u.z());
    }

    /* *
     * Apply a function to every element of the vector u
     * */
    public static Vector3f apply(Function<Float, Float> f, Vector3f u){
        return new Vector3f(f.apply(u.x()), f.apply(u.y()), f.apply(u.z()));
    }

    /**
     * @return A new Vector corresponding to u + v
     */
    public static Vector3f add(Vector3f u, Vector3f v){
        Vectorf res = Vectorf.add(u, v);
        return new Vector3f(res.getValue(0), res.getValue(1), res.getValue(2));
    }

    /**
     * @return A new Vector corresponding to u - v
     */
    public static Vector3f sub(Vector3f u, Vector3f v){
        Vectorf res = Vectorf.add(u, Vector3f.negate(v));
        return new Vector3f(res.getValue(0), res.getValue(1), res.getValue(2));
    }

    /**
     * @return A new Vector corresponding to hadamard product of u, v
     *         Multiplication component wise
     */
    public static Vector3f hadamard(Vector3f u, Vector3f v){
        Vectorf res = Vectorf.Hadamard(u, v);
        return new Vector3f(res.getValue(0), res.getValue(1), res.getValue(2));
    }

    /**
     * @return Dot product between u and v
     */
    public static float dot(Vector3f u, Vector3f v){
        return Vectorf.dot(u, v);
    }

    /**
     * Normalize the input Vector3f
     * @param u The Vector3f to normalize
     * @return The normalized Vector3f
     */
    public static Vector3f normalize(Vector3f u){
        float length = u.length();
        return Vector3f.apply(ui -> ui / length, u);
    }

    /**
     * Safe Normalize the input Vector3f
     * @param u The Vector3f to normalize
     * @return The normalized Vector3f if length != 0.0f
     *         The input Vector3f otherwise
     */
    public static Vector3f safeNormalize(Vector3f u){
        Vectorf u1 = Vectorf.safeNormalize(u);
        return new Vector3f(u1.getValue(0), u1.getValue(1), u1.getValue(2));
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link Object#equals(Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(this.x());
        result = prime * result + Float.floatToIntBits(this.y());
        result = prime * result + Float.floatToIntBits(this.z());
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        Vector3f other = (Vector3f) obj;
        if (Float.floatToIntBits(this.x()) != Float.floatToIntBits(other.x())){
            return false;
        }
        if (Float.floatToIntBits(this.y()) != Float.floatToIntBits(other.y())){
            return false;
        }
        if (Float.floatToIntBits(this.z()) != Float.floatToIntBits(other.z())){
            return false;
        }
        return true;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return X axis
     */
    public float x(){
        return value[0];
    }

    /**
     * @return Y axis
     */
    public float y(){
        return value[1];
    }

    /**
     * @return Y axis
     */
    public float z(){
        return value[2];
    }
}
