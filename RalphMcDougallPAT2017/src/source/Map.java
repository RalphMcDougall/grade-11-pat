/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ralph McDougall 13/10/2017
 */
public class Map {
    
    public ArrayList<Tile> allTiles;
    
    public int width;
    public int height;
    public Tile[][] grid;
    
    public String mapName;
    
    public Map(String mapName) throws SQLException
    {
        GameLogger.logInfo("Starting to load map");
        this.allTiles = new ArrayList<Tile>();
        
        this.mapName = mapName;
        
        loadMap();
        
        GameLogger.logInfo("Map loading completed");
    }
    
    private void loadMap() throws SQLException
    {
        GameLogger.logInfo("Starting to load map file");
        MapFileLoader loader = new MapFileLoader(this.mapName);
        
        this.width = loader.getMapWidth();
        this.height = loader.getMapHeight();
        
        char mapLoaderGrid[][] = loader.getGrid();
        GameLogger.logInfo("Map file loading completed");
        
        this.grid = new Tile[this.height][this.width];
        
        // This helps to increase the efficiency of the map loading by insane amounts because we don't have to keep retrieving the same information thousands of times
        HashMap<Character, String> existingTiles = new HashMap<Character, String>();
        
        for (int y = 0; y < this.height; ++y)
        {
            GameLogger.logInfo("Starting to load map row: " + y);
            for (int x = 0; x < this.width; ++x)
            {
                String details = existingTiles.get(mapLoaderGrid[y][x]);
                
                if (details == null)
                {
                    // The details could not be found and need to be loaded
                    GameLogger.logInfo("Querying database for tile described by character '" + mapLoaderGrid[y][x] + "'");
                    ResultSet rs = DBBridge.query("SELECT * FROM tblTiles WHERE MapSymbol = '" + mapLoaderGrid[y][x] + "'");
                    GameLogger.logInfo("Query retrieved");
                    String newDetails = DBBridge.processResultSet(rs, " ");
                    details = newDetails;
                    existingTiles.put(mapLoaderGrid[y][x], newDetails);
                }
                
                ArrayList<Integer> indexes = new ArrayList<Integer>();
                boolean walkable = false;
                
                String[] obj = details.replace('\n', ' ').split(" ");
                
                walkable = Boolean.parseBoolean(obj[3]);
                
                indexes.add( Integer.parseInt(obj[4]) + 10 * Integer.parseInt(obj[5]) );
                
                Tile t = new Tile(this, x, y, 1, walkable, 0, indexes);
                this.grid[(int) y][(int) x] = t;
            }
        }
    }
    
}
