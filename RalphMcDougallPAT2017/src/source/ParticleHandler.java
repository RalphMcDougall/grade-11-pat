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
public class ParticleHandler {
    public static ArrayList<Particle> allParticles = new ArrayList<Particle>();
    
    public static void updateParticles()
    {
        for (int i = 0; i < ParticleHandler.allParticles.size(); ++i)
        {
            Particle p = ParticleHandler.allParticles.get(i);
            if (p.canMove())
            {
                p.randomMove();
            }
            
            if (p.shouldBeRemoved())
            {
                ParticleHandler.allParticles.remove(p);
                EntityHandler.allEntities.remove(p);
                --i;
            }
        }
    }
    
    public static void clearAllParticles()
    {
        GameLogger.logInfo("Clearing all particles");
        while (allParticles.size() != 0)
        {
            allParticles.remove(0);
        }
        GameLogger.logInfo("All particles cleared");
    }
}
