/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 23/10/2017
 */
public class Pellet extends Entity{
    
    private int attribute;
    private int value;
    
    public Pellet(double worldX, double worldY, double worldSize, ArrayList<Integer> imageIndexes, int attribute, int value)
    {
        super(worldX, worldY, worldSize, imageIndexes);
        this.attribute = attribute;
        this.value = value;
    }
    
    public int getAttribute()
    {
        return this.attribute;
    }
    
    public int getValue()
    {
        return this.value;
    }
}
