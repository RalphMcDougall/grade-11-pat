/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 13/10/2017
 */
public class Tile extends Entity {
    
    private boolean walkable = false;
    private int tileID;
    
    public Tile(Map map, double worldX, double worldY, double worldSize, boolean walkable, int tileID, ArrayList<Integer> imageIndexes) {
        super(worldX, worldY, worldSize, imageIndexes);
        this.tileID = tileID;
        
        this.walkable = walkable;
        
        map.allTiles.add(this);
    }
    
    public boolean isWalkable()
    {
        return this.walkable;
    }
    
}
