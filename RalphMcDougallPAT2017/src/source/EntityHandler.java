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
public class EntityHandler {
    
    public static ArrayList<Entity> allEntities = new ArrayList<Entity>();
    
    public static void updateEntities(Camera camera, Map map) {
        for(int i = 0; i < EntityHandler.allEntities.size(); ++i)
        {
            // Go through all of the entities and update them as required
            
            Entity currentEntity = EntityHandler.allEntities.get(i);
            currentEntity.update(camera, GameHandler.TILE_SIZE, map);
        }
    }
    
    public static void clearAllEntities()
    {
        GameLogger.logInfo("Clearing all entities");
        while (allEntities.size() != 0)
        {
            allEntities.remove(0);
        }
        GameLogger.logInfo("All entities cleared");
    }

}


