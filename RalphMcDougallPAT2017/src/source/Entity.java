/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;
import org.newdawn.slick.Image;


/**
 *
 * @author Ralph McDougall 13/10/2017
 */
public class Entity {
    // Everything in the game is an Entity :O
    
    protected double worldX;
    protected double worldY;
    protected double worldSize; // The number of block units that this entity is in size
    
    private int screenX;
    private int screenY;
    private int screenSize;
    
    private boolean toDraw = false;

    private ArrayList<Integer> imageIndexes;
    protected int currentImageIndex = 0;
    protected int rotation = 0;
    
    public Entity(double worldX, double worldY, double worldSize, ArrayList<Integer> imageIndexes) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.worldSize = worldSize;
        this.imageIndexes = imageIndexes;
        
        EntityHandler.allEntities.add(this);
    }
    
    public void update(Camera camera, int tileSize, Map map)
    {
        this.perform(map);
        
        this.updateImage();
        this.toDraw = false;
        
        this.screenX = (int) (this.worldX * tileSize - camera.getLeftX());
        this.screenY = (int) (this.worldY * tileSize - camera.getTopY());
        this.screenSize = (int) (this.worldSize * tileSize);
        
        if (this.onScreen(camera))
        {
            this.toDraw = true;
        }
    }
    
    protected void updateImage()
    {
        // Change which image is being used
    }
    
    protected void perform(Map map)
    {
        // Do stuff
    }
    
    private boolean onScreen(Camera camera)
    {
        return (this.screenX + this.screenSize > 0 && 
                this.screenX < camera.getWidth()) &&
               (this.screenY + this.screenSize > 0 &&
                this.screenY < camera.getHeight());
    }
    
    public boolean toDraw()
    {
        return this.toDraw;
    }
    
    public Image getImage()
    {
        Image temp = ImageHandler.getImage( this.imageIndexes.get(this.currentImageIndex ) ).copy();
        temp.rotate(this.rotation);
        return temp;
    }
    
    public int getScreenX()
    {
        return this.screenX;
    }
    
    public int getScreenY()
    {
        return this.screenY;
    }
    
    public double getWorldX()
    {
        return this.worldX;
    }
    
    public double getWorldY()
    {
        return this.worldY;
    }
    
    public double getWorldSize()
    {
        return this.worldSize;
    }
    
    public boolean intersects(Entity en)
    {
        return (this.worldX < (en.getWorldX() + en.getWorldSize()) && this.worldY < (en.getWorldY() + en.getWorldSize()) ) && ((this.worldX + this.worldSize) > en.getWorldX() && (this.worldY + this.worldSize) > en.getWorldY());
    }
    
}
