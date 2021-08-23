package fr.pops.graphics.meshes;

import fr.pops.graphics.Material;
import fr.pops.graphics.Vertex;
import fr.pops.math.Vector2f;
import fr.pops.math.Vector3f;

public class Disc extends Mesh {

    private float radius;
    private int nbPoints;
    private Vector3f centerPosition;

    /**
     * Ctor
     * @param radius Radius of the disc
     * @param nbPoints Number of points composing the rim
     */
    public Disc(float radius, int nbPoints){
        this.radius = radius;
        this.nbPoints = nbPoints;
        this.draw();
    }

    /**
     * Ctor
     * @param radius Radius of the disc
     * @param nbPoints Number of points composing the rim
     * @param centerPosition The position of the center point
     */
    public Disc(float radius, int nbPoints, Vector3f centerPosition){
        this.radius = radius;
        this.nbPoints = nbPoints;
        this.centerPosition = centerPosition;
        this.draw();
    }

    /**
     * Draw disc
     */
    private void draw(){
        // Build vertices
        // Rim
        float step = (float) (2 * Math.PI / this.nbPoints);
        Vertex[] vertices = new Vertex[this.nbPoints + 1];
        for (int i = 0; i < this.nbPoints; i++){
            float x = this.centerPosition.x() + this.radius * (float) Math.cos(i * step);
            float y = this.centerPosition.y() +this.radius * (float) Math.sin(i * step);
            vertices[i] = new Vertex(new Vector3f(x, y, this.centerPosition.z()), new Vector3f(), new Vector2f());
        }

        // Center
        vertices[this.nbPoints] = new Vertex(this.centerPosition, new Vector3f(), new Vector2f());

        // Store vertices
        this.vertices = vertices;

        // Build indices
        int nbTriangles = this.nbPoints;
        int[] indices = new int[3 * nbTriangles];
        for (int i = 0; i < nbTriangles; i++){
            indices[3 * i] = i;
            indices[3 * i + 1] = this.nbPoints;
            indices[3 * i + 2] = i == nbTriangles - 1 ? 0 : i + 1;
        }
        this.indices = indices;

        // Material
        this.material = new Material("/textures/image.png");
    }

    /**
     * @return Radius of the disc
     */
    public float getRadius() {
        return radius;
    }

    /**
     * @return The number of points composing
     *         the outside circle
     */
    public int getNbPoints() {
        return nbPoints;
    }

}
