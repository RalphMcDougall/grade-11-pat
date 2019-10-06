/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Ralph McDougall 16/10/2017
 */
public class MapFileLoader {
    
    private String fileName;
    
    private Scanner file;
    
    private int mapWidth;
    private int mapHeight;
    
    private char grid[][];
    
    private boolean loaded = false;
    
    private final String PREFIX = "src/resources/";
    
    public MapFileLoader(String fileName)
    {
        this.fileName = fileName;
        
        loadFile();
        retrieveData();
        
        closeFile();
    }
    
    private void loadFile()
    {
        try
        {
            this.file = new Scanner(new File(PREFIX + this.fileName));
            this.loaded = true;
        }
        catch(FileNotFoundException ex)
        {
            GameLogger.logError("The file " + this.fileName + " could not be found.");
            GameLogger.logError(ex.toString());
        }
    }
    
    private void retrieveData()
    {
        if (!this.loaded)
        {
            return;
        }
        String[] mapSizeLine = this.file.nextLine().split(" ");
        
        this.mapWidth = Integer.parseInt(mapSizeLine[0]);
        this.mapHeight = Integer.parseInt(mapSizeLine[1]);
        
        this.grid = new char[this.mapHeight][this.mapWidth];
        
        for (int y = 0; y < this.mapHeight; ++y)
        {
            String line = this.file.nextLine();
            for (int x = 0; x < this.mapWidth; ++x)
            {
                this.grid[y][x] = line.charAt(x);
            }
        }
        
    }
    
    private void closeFile()
    {
        if (!this.loaded)
        {
            return;
        }
        this.file.close();
    }
    
    public int getMapWidth()
    {
        return this.mapWidth;
    }
    
    public int getMapHeight()
    {
        return this.mapHeight;
    }
    
    public char[][] getGrid()
    {
        return this.grid;
    }
    
}
