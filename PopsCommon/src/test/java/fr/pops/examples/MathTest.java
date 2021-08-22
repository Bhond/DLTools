package fr.pops.examples;

import fr.pops.math.Matrix4f;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {

    @Test
    public void testMatrix4fMultiplication(){
        Matrix4f A = Matrix4f.toMatrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Matrix4f B = Matrix4f.toMatrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        float[] expectedArray = new float[]{
                90.0f, 100.0f, 110.0f, 120.0f,
                202.0f, 228.0f, 254.0f, 280.0f,
                314.0f, 356.0f, 398.0f, 440.0f,
                426.0f, 484.0f, 542.0f, 600.0f
        };
        assertEquals(Arrays.toString(expectedArray), Arrays.toString(Matrix4f.times(A, B).getValue()));
    }
}
