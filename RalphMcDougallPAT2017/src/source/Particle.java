/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 25/10/2017
 */
public class Particle extends Entity {
    
    private long waitToMove = 5;
    private long lastMove = 0;
    
    private long lifeSpan = 50;
    private long birthTime = 0;
    
    private double dispersionFactor = 1.0;
    
    public Particle(double worldX, double worldY, double worldSize, ArrayList<Integer> imageIndexes, long lifeSpan, double dispersionFactor)
    {
        super(worldX, worldY, worldSize, imageIndexes);
        ParticleHandler.allParticles.add(this);
        
        this.lastMove = System.currentTimeMillis();
        this.birthTime = System.currentTimeMillis();
        this.lifeSpan = lifeSpan;
        this.dispersionFactor = dispersionFactor;
    }
    
    public long getBirthTime()
    {
        return this.birthTime;
    }
    
    public long getWaitToMove()
    {
        return this.waitToMove;
    }
    
    public void randomMove()
    {
        double diffX = (Math.random() - 0.5) * this.dispersionFactor;
        double diffY = (Math.random() - 0.5) * this.dispersionFactor;
        this.worldX += diffX;
        this.worldY += diffY;
        this.lastMove = System.currentTimeMillis();
    }
    
    public boolean canMove()
    {
        return System.currentTimeMillis() >= this.lastMove + this.waitToMove;
    }
    
    public boolean shouldBeRemoved()
    {
        return System.currentTimeMillis() >= this.birthTime + this.lifeSpan;
    }
}
