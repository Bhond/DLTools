package fr.pops.graphics.meshes;

import fr.pops.graphics.Material;
import fr.pops.graphics.Vertex;
import fr.pops.math.Vector2f;
import fr.pops.math.Vector3f;

public class Grid extends Mesh {

    private int resX;
    private int resY;
    private float width;
    private float height;
    /**
     * Ctor
     * @param resX Nb of vertices on the local x axis
     * @param resY Nb of vertices on the local y axis
     * @param width Length of the grid along the x axis
     * @param height Length of the grid along the y axis
     */
    public Grid(int resX, int resY, float width, float height) {
        this.resX = resX;
        this.resY = resY;
        this.width = width;
        this.height = height;
        renderMode = RenderMode.LINES;
        this.draw();
    }

    private void draw(){
        // Build vertices
        float stepX = width / (resX - 1);
        float stepY = height / (resY - 1);
        float x = - width / 2;
        float y = - height / 2;
        int counter = 0;
        Vertex[] lines = new Vertex[this.resX * this.resY];
        for (int i = 0; i < resY; i++){
            for (int j = 0; j < resX; j++){
                lines[counter] = new Vertex(new Vector3f(x, y, 0), new Vector3f(), new Vector2f());
                x += stepX;
                counter++;
            }
            x = - width / 2;
            y += stepY;
        }

        // Build indices
        int[] indices;
        if (renderMode == RenderMode.TRIANGLES){
            int trianglesPerRow = 6 * (resX - 1);
            indices = new int[6 * (this.resX - 1) * (this.resY - 1)];
            int idx = 0;
            int bufIdx = 0;
            for (int i = 0; i < resX - 1; i++){
                for (int j = 0; j < resY - 1; j++){
                    bufIdx = i * resY + j;
                    indices[idx]     = bufIdx;
                    indices[idx + 1] = bufIdx + resY + 1;
                    indices[idx + 2] = bufIdx + resY;
                    indices[idx + 3] = bufIdx;
                    indices[idx + 4] = bufIdx + 1;
                    indices[idx + 5] = bufIdx + resY + 1;
                    idx += 6;
                }
            }
        } else {
            indices = new int[2 * (this.resY * (this.resX - 1) + this.resX * (this.resY - 1))];
            int idx = 0;
            // Horizontal lines
            for (int i = 0; i < resX; i++){
                for (int j = 0; j < resY - 1; j++){
                    indices[idx] = i * resY + j;
                    indices[idx + 1] = i * resY + j + 1;
                    idx+=2;
                }
            }
            // Vertical lines
            for (int i = 0; i < resX - 1; i++){
                for (int j = 0; j < resY; j++){
                    indices[idx] = i * resY + j;
                    indices[idx + 1] = i * resY + j + resY;
                    idx += 2;
                }
            }
        }

        // Fill in fields
        this.vertices = lines;
        this.indices = indices;
        this.material = new Material("/textures/image.png");
    }
}
