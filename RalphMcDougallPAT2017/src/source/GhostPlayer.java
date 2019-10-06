/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

/**
 *
 * @author Ralph McDougall 20/10/2017
 */
public class GhostPlayer {
    
    public double x;
    public double y;
    public double size;
    public int direction = ' ';
    public int depth;
    
    public GhostPlayer(double x, double y, double size, int direction, int depth)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.direction = direction;
        this.depth = depth;
    }
    
    public boolean isObstructed(Map map)
    {
        double epsilon = 0.01;
        
        int leftX = (int) this.x;
        int rightX = (int) (this.x + this.size - epsilon);
        
        int topY = (int) this.y;
        int bottomY = (int) (this.y + this.size - epsilon);
        
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
    
}
