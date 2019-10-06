/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 20/10/2017
 */
public class Projectile extends Entity{
    
    private long waitToMove;
    private double moveDist;
    private int damage;
    private int direction;
    private boolean toDestroy = false;
    
    private long lastMove = 0;
    
    public Projectile(double worldX, double worldY, double worldSize, ArrayList<Integer> imageIndexes, long waitToMove, double moveDist, int damage, int direction)
    {
        super(worldX, worldY, worldSize, imageIndexes);
        this.waitToMove = waitToMove;
        this.moveDist = moveDist;
        this.damage = damage;
        this.direction = direction;
    }
    
    @Override
    protected void perform(Map map)
    {
        if (this.toDestroy)
        {
            EntityHandler.allEntities.remove(this);
        }
        boolean result = false;
        switch(this.direction)
        {
            case 0:
                result = this.moveY(-1 * moveDist, map);
                break;
            case 1:
                result = this.moveX(moveDist, map);
                break;
            case 2:
                result = this.moveY(moveDist, map);
                break;
            case 3:
                result = this.moveX(-1 * moveDist, map);
                break;
            default:
                GameLogger.logInfo("Invalid player move direction");
                break;
        }
        
        if (result)
        {
                this.spawnParticles(3);
        }
        
    }
    
    public void spawnParticles(int count)
    {
        for (int i = 0; i < count; ++i)
        {
            this.spawnParticle();
        }
    }
    
    public void spawnParticle()
    {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.add(50);
        Particle p = new Particle(this.getWorldX() + this.getWorldSize() / 4, this.getWorldY() + this.getWorldSize() / 4, 0.25, indexes, 100, 0.15);
    }
    
    public boolean moveX(double dist, Map map)
    {
        boolean result = false;
        this.worldX += dist;
        
        boolean obstructed = this.isObstructed(map);
        
        if (this.sufficientTimeEllapsed() && !obstructed)
        {
            this.updateLastMove();
            result = true;
        }
        else
        {
            this.worldX -= dist;
        }
        
        if (obstructed)
        {
            this.toDestroy = true;
        }
        
        return result;
    }
    
    public boolean moveY(double dist, Map map)
    {
        boolean result = false;
        this.worldY += dist;
        
        boolean obstructed = this.isObstructed(map);
        
        if (this.sufficientTimeEllapsed() && !obstructed)
        {
            this.updateLastMove();
            result = true;
        }
        else
        {
            this.worldY -= dist;
        }
        
        
        if (obstructed)
        {
            this.toDestroy = true;
        }
        
        return result;
    }
    
    public boolean isObstructed(Map map)
    {
        double epsilon = 0.01;
        
        int leftX = (int) this.worldX;
        int rightX = (int) (this.worldX + this.worldSize - epsilon);
        
        int topY = (int) this.worldY;
        int bottomY = (int) (this.worldY + this.worldSize - epsilon);
        
        boolean found = false;
        
        for (int y = topY; y <= bottomY; ++y)
        {
            for (int x = leftX; x <= rightX; ++x)
            {
                if (!map.grid[y][x].isWalkable())
                {
                    found = true;
                    break;
                }
            }
            if (found)
            {
                break;
            }
        }
        
        return found;
    }
    
    public boolean sufficientTimeEllapsed()
    {
        return System.currentTimeMillis() > this.lastMove + this.waitToMove;
    }
    
    public void updateLastMove()
    {
        this.lastMove = System.currentTimeMillis();
    }
    
    public int getDamage()
    {
        return this.damage;
    }
    
}
