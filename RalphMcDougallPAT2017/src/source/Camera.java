/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

/**
 *
 * @author Ralph McDougall 13/10/2017
 */
public class Camera {
    private int leftX = 0; // Where in the map the leftX is ..... Take the worldX of objects and multiply by the size of each tile
    private int topY = 0; // Where in the map the topY is
    private int screenWidth;
    private int screenHeight;
    
    public Camera(int leftX, int topY, int screenWidth, int screenHeight)
    {
        this.leftX = leftX;
        this.topY = topY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public int getTopY()
    {
        return this.topY;
    }
    
    public int getLeftX()
    {
        return this.leftX;
    }
    
    public int getWidth()
    {
        return this.screenWidth;
    }
    
    public int getHeight()
    {
        return this.screenHeight;
    }
    
    public void panX(int dist)
    {
        this.leftX += dist;
    }
    public void panY(int dist)
    {
        this.topY += dist;
    }
    
}
