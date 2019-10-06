/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 14/10/2017
 */
public class GameCharacter extends Entity{
    
    private int currentHealth;
    private int maxHealth;
    
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    
    private long lastMove = 0;
    private long waitToMove;
    
    private double moveDist;
    
    private int facing = 0; // 0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEFT
    
    private long lastImageChange = 0;
    
    private long lastAttack = 0;
    private long attackWaitTime = 0;
    
    private boolean isPlayer = false;
    private boolean tracking = false;
    
    public GameCharacter(double worldX, double worldY, double worldSize, ArrayList<Integer> imageIndexes, int maxHealth, long waitToMove, double moveDist, long attackWaitTime)
    {
        super(worldX, worldY, worldSize, imageIndexes);
        
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
        this.waitToMove = waitToMove;
        this.moveDist = moveDist;
        
        this.attackWaitTime = attackWaitTime;
    }
    
    public int getHealth()
    {
        return this.currentHealth;
    }
    
    public void setHealth(int h)
    {
        this.currentHealth = h;
    }
    
    public long getAttackWaitTime()
    {
        return this.attackWaitTime;
    }
    
    public void addHealth(int h)
    {
        this.currentHealth += h;
    }
    
    public void setAttackWaitTime(int fr)
    {
        this.attackWaitTime = fr;
    }
    
    @Override
    protected void updateImage()
    {
        if (!this.movingDown && !this.movingLeft && !this.movingRight && !this.movingUp)
        {
            this.currentImageIndex = 0;
            this.lastImageChange = 0;
        }
        else
        {
            if (this.currentImageIndex == 0)
            {
                this.currentImageIndex = 1;
            }
            if (System.currentTimeMillis() - this.lastImageChange >= 150)
            {
                this.currentImageIndex += 1;
                this.currentImageIndex %= 8;
                this.lastImageChange = System.currentTimeMillis();
            }
        }
        
        this.rotation = this.facing * 90;
    }
    
    public boolean isTracking()
    {
        return this.tracking;
    }
    
    public void setTracking(boolean b)
    {
        this.tracking = b;
    }
    
    public boolean canAttack()
    {
        return System.currentTimeMillis() >= this.lastAttack + this.attackWaitTime;
    }
    
    public void attack()
    {
        this.lastAttack = System.currentTimeMillis();
    }
    
    public boolean moveX(double dist, Map map)
    {
        boolean result = false;
        this.worldX += dist;
        
        boolean obstructed = this.isObstructed(map);
        
        if (this.canMove() && !obstructed)
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
            this.movingLeft = false;
            this.movingRight = false;
        }
        
        return result;
    }
    
    public boolean moveY(double dist, Map map)
    {
        boolean result = false;
        this.worldY += dist;
        
        boolean obstructed = this.isObstructed(map);
        
        if (this.canMove() && !obstructed)
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
            this.movingUp = false;
            this.movingDown = false;
        }
        
        return result;
    }

    public boolean isMovingUp()
    {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp)
    {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown()
    {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown)
    {
        this.movingDown = movingDown;
    }

    public boolean isMovingLeft()
    {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft)
    {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight()
    {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight)
    {
        this.movingRight = movingRight;
    }
    
    public void setFacing(int facing)
    {
        this.facing = facing;
    }
    
    public int getFacing()
    {
        return this.facing;
    }
    
    public boolean canMove()
    {
        return System.currentTimeMillis() > this.lastMove + this.waitToMove;
    }
    
    public void updateLastMove()
    {
        this.lastMove = System.currentTimeMillis();
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
    
    public double getWorldX()
    {
        return this.worldX;
    }
    
    public double getWorldY()
    {
        return this.worldY;
    }
    
    public double getMoveDist()
    {
        return this.moveDist;
    }
    
    public void dealDamage(int damage)
    {
        this.currentHealth -= damage;
        this.spawnDamageParticles(10);
    }
    
    @Override
    protected void perform(Map map)
    {
        // Do stuff
        if (this.isPlayer)
        {
            return;
        }
        
        if (this.currentHealth <= 0)
        {
            EntityHandler.allEntities.remove(this);
        }
    }
    
    public void setIsPlayer(boolean b)
    {
        this.isPlayer = b;
    }
    
    private int random(int upper)
    {
        return (int) (Math.random() * upper);
    }
    
    public void randomMove(Map map)
    {
        boolean result = false;
        int dir = random(1000);
        switch (dir)
        {
            case 0:
                result = this.moveY(-1 * this.moveDist, map);
                break;
            case 1:
                result = this.moveX(1 * this.moveDist, map);
                break;
            case 2:
                result = this.moveY(1 * this.moveDist, map);
                break;
            case 3:
                result = this.moveX(-1 * this.moveDist, map);
                break;
            default:
                // don't move
                break;
        }
        if (result)
        {
            this.setFacing(dir);
        }
    }
    
    public void spawnDamageParticles(int count)
    {
        for (int i = 0; i < count; ++i)
        {
            this.spawnDamageParticle();
        }
    }
    
    public void spawnDamageParticle()
    {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.add(52);
        Particle p = new Particle(this.worldX + this.worldSize / 4, this.worldY + this.worldSize / 4, 0.25, indexes, 250, 0.2);
    }
    
}
