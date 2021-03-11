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
 * Name: Complex.java
 *
 * Description: Class defing the complex numbers z = a + i * b.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.popsmath;

public class Complex {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private double Re;
    private double Im;
    private double r;
    private double arg;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    public Complex(double re, double im) {
        this.Re = re;
        this.Im = im;
        this.r = Math.sqrt(re*re + im*im);
        this.computeTheta();
    }

    /*****************************************
     *
     * Algebraic ops
     *
     *****************************************/
    public static Complex add(Complex z1, Complex z2) {
        return new Complex(z1.getRe() + z2.getRe(), z1.getIm() + z2.getIm());
    }

    public static Complex minus(Complex z1, Complex z2) {
        return new Complex(z1.getRe() - z2.getRe(), z1.getIm() - z2.getIm());
    }

    public static Complex times(Complex z1, Complex z2) {
        return new Complex(z1.getRe()*z2.getRe() - z1.getIm()*z2.getIm(),
                z1.getRe()*z2.getIm() + z1.getIm()*z2.getRe());
    }

    public static Complex shift(Complex z, double a) {
        return new Complex(a * z.getRe(), a * z.getIm());
    }

    public void conjugate(Complex z) {
        z.Im*=-1;
        this.computeTheta();
    }

    private void computeTheta(){
        double re = this.Re;
        double im = this.Im;
        if (re != 0) {
            this.arg = Math.atan(im / re);
        } else if (im > 0 ){
            this.arg = Math.PI / 2;
        } else if (im < 0 ){
            this.arg = - Math.PI / 2;
        } else {
            this.arg = 0;
        }
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public double getRadius() {
        return this.r;
    }

    public double getRe() {
        return this.Re;
    }

    public double getIm() {
        return this.Im;
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    @Override
    public String toString() {
        return this.Re + " + " + this.Im + " i";
    }

}
