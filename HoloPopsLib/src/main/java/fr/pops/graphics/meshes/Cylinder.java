package fr.pops.graphics.meshes;

import fr.pops.graphics.Material;
import fr.pops.math.Vector3f;

public class Cylinder extends Mesh {

    private int nbPoints;
    private boolean closed;
    private float height;
    private float radius;

    private Mesh d1;
    private Mesh d2;
    Vector3f position;

    /**
     * Ctor
     * @param nbPoints The number of point composing the rim
     * @param closed True if discs should close the cylinder at its base
     * @param height The height of the cylinder
     * @param radius The radius of the base
     */
    public Cylinder(int nbPoints, boolean closed, float height, float radius) {
        this.nbPoints = nbPoints;
        this.closed = closed;
        this.height = height;
        this.radius = radius;
        this.draw();
    }

    /**
     * Ctor
     * @param nbPoints The number of point composing the rim
     * @param closed True if discs should close the cylinder at its base
     * @param height The height of the cylinder
     * @param radius The radius of the base
     * @param position Position of the center of the cylinder
     */
    public Cylinder(int nbPoints, boolean closed, float height, float radius, Vector3f position) {
        this.nbPoints = nbPoints;
        this.closed = closed;
        this.height = height;
        this.radius = radius;
        this.position = position;
    }

    /**
     * Draw cylinder
     */
    private void draw(){

        // Build bases
        this.d1 = new Disc(this.radius, this.nbPoints, new Vector3f(0, 0, position.z() - this.height / 2));
        this.d2 = new Disc(this.radius, this.nbPoints, new Vector3f(0, 0, position.z() + this.height / 2));

        // Build sides
        int[] sidesIndices = new int[ 6 * this.nbPoints];
        for (int i = 0; i < this.nbPoints; i++){
            sidesIndices[i * 6]     = d1.getIndices()[i * 3];
            sidesIndices[i * 6 + 1] = d2.getIndices()[i * 3] + d1.getVertices().length;
            sidesIndices[i * 6 + 2] = d2.getIndices()[i * 3 + 2] + d1.getVertices().length;
            sidesIndices[i * 6 + 3] = d1.getIndices()[i * 3];
            sidesIndices[i * 6 + 4] = d2.getIndices()[i * 3 + 2] + d1.getVertices().length;
            sidesIndices[i * 6 + 5] = d1.getIndices()[i * 3 + 2];
        }

        // Merge bases into 1 mesh
        Mesh bases = Mesh.merge(this.d1, this.d2);
        int[] completeArray = new int[bases.getIndices().length + sidesIndices.length];
        // Add bases into the final mesh
        if (this.closed){
            for(int i = 0; i < bases.getIndices().length; i++){
                completeArray[i] = bases.getIndices()[i];
            }
        }

        // Fill in fields
        int indexOffset = this.closed ? bases.getIndices().length : 0;
        this.vertices = bases.getVertices();
        for(int i = 0; i < sidesIndices.length; i++){
            completeArray[i + indexOffset] = sidesIndices[i];
        }
        this.indices = completeArray;
        this.material = new Material("/textures/image.png");
    }

    /**
     * @return True if the circle is close
     */
    public boolean isClosed() {
        return closed;
    }
}
