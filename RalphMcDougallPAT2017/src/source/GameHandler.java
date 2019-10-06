
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;

/**
 *
 * @author Ralph McDougall 13/10/2017
 */
public class GameHandler extends BasicGame {

    // The main class that handles all updates, input and rendering
    
    private GUIPlayerSetup parentGUI;
    
    private Camera camera;
    private Map map;
    private GameCharacter player;
    
    public static final int TILE_SIZE = 32;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    
    private ArrayList<GameCharacter> allEnemies;
    private ArrayList<Projectile> allProjectiles;
    
    private long lastSpawn = 0;
    private int enemyDamage = 25;
    
    private long lastReport = 0;
    private long waitPerReport = 5000;
    
    private long lastScoreIncrease = 0;
    private long waitPerScoreIncrease = 500;
    private int score = 0;
    
    private int enemyKillBonus = 50;
    
    private int MAX_PATH_FIND_DEPTH = 60;
    private boolean GAME_RUNNING = true;

    private String playerName = "";
    private int difficulty = 0;

    private int[] STARTING_PLAYER_HEALTH = {100, 100, 50, 10};
    private int[] STARTING_ENEMY_HEALTH = {50, 50, 100, 100};
    
    private double[] RAMP_FACTORS = {0.95, 0.9, 0.85, 0.8};
    private double rampFactor = 1;
    private long lastRamp = 0;
    private long waitPerRamp = 5000;
    private long waitPerEnemySpawn = 1000;
    
    private String enemyString = null;
    
    private Pellet pellet;
    
    private int projectileDamage = 25;
    
    private String boostText = "";
    private long startBoostText = 0;
    private long waitBoostText = 2000;
    
    private int numPelletsPickedUp = 0;
    
    public GameHandler(String title, GUIPlayerSetup parentGUI, String playerName, int diff)
    {
        super(title);
        this.parentGUI = parentGUI;
        this.playerName = playerName;
        this.difficulty = diff;
    }

    @Override
    public void init(GameContainer gc) throws SlickException
    {
        // Make sure the handlers are clear
        ParticleHandler.clearAllParticles();
        EntityHandler.clearAllEntities();
        
        // Initiliase all of the game objects and variables
        ImageHandler.setupImageHandler(this.TILE_SIZE);
        
        this.rampFactor = this.RAMP_FACTORS[this.difficulty];
        
        this.allEnemies = new ArrayList<GameCharacter>();
        this.allProjectiles = new ArrayList<Projectile>();
        
        try
        {   
            this.map = new Map("map1.txt");
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"The map could not be loaded!\n" + ex.toString());
            GameLogger.logError("The map could not be loaded.");
            GameLogger.logError(ex.toString());
        }
        
        try
        {
            this.setupPlayer();
            this.player.setIsPlayer(true);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Unable to load player!\n" + ex.toString());
            GameLogger.logError("Unable to load player");
            GameLogger.logError(ex.toString());
        }
        this.camera = new Camera(-1 * ((SCREEN_WIDTH - TILE_SIZE) / 2 - (int) this.player.getWorldX() * TILE_SIZE) , -1 * ((SCREEN_HEIGHT - TILE_SIZE) / 2 - (int) this.player.getWorldY() * TILE_SIZE), SCREEN_WIDTH, SCREEN_HEIGHT);
        
        this.addNewPellet();
        
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        // This runs every update tick and handles inputs and object updates
        this.checkPlayerAlive();
        
        this.checkNumPelletsPickedUp();
        
        if (!GAME_RUNNING)
        {
            // The player character has died and we need to wait for the ESCAPE key to be pressed to continue
            this.waitForEscape(gc);
            return;
        }
        
        this.handleKeyboardInput(gc);
        this.handlePlayerMovement();
        
        this.increaseRamp();
        this.increaseScore();
        
        this.spawnIfPossible();
        this.moveEnemies();
        
        EntityHandler.updateEntities(this.camera, this.map);
        ParticleHandler.updateParticles();
        this.updateProjectiles();
        this.updateEnemies();
        
        this.checkPlayerPelletCollision();
        
        this.entityCountLog();
    }
    
    public void checkPlayerPelletCollision()
    {
        // Check if the player has picked up the pellet
        if (this.player.intersects(this.pellet))
        {
            this.numPelletsPickedUp += 1;
            GameLogger.logInfo("Pellet used");
            EntityHandler.allEntities.remove(this.pellet);
            this.updateStats(this.pellet.getAttribute(), this.pellet.getValue());
            this.addNewPellet();
            this.clearAllEnemies();
            this.spawnPowerUpParticles(100);
        }
    }
    
    public void checkPlayerAlive()
    {
        // Check if the player is still alive
        if (GAME_RUNNING && this.player.getHealth() <= 0)
        {
            GameLogger.logInfo("Player died");
            GAME_RUNNING = false;
        }
        
        if (this.player.getHealth() < 0)
        {
            this.player.setHealth(0);
        }
    }
    
    public void waitForEscape(GameContainer gc)
    {
        // Wait until the ESCAPE key is pressed. When it is, close the game
        Input input = gc.getInput();
        
        if (input.isKeyDown(Input.KEY_ESCAPE))
        {
            this.closeGame();
        }
    }
    
    public void increaseRamp()
    {
        // If sufficient time has ellapsed, decrease the amount of time between enemy spawns
        if (System.currentTimeMillis() >= this.lastRamp + this.waitPerRamp)
        {
            GameLogger.logInfo("Increasing spawn rate");
            this.lastRamp = System.currentTimeMillis();
            this.waitPerEnemySpawn = (long) (this.waitPerEnemySpawn * this.rampFactor);
            GameLogger.logInfo("New time between spawns: " + this.waitPerEnemySpawn);
        }
    }
    
    public void increaseScore()
    {
        // Give the player a score increase for how long they've survived
        if (System.currentTimeMillis() >= this.lastScoreIncrease + this.waitPerScoreIncrease)
        {
            this.lastScoreIncrease = System.currentTimeMillis();
            this.score += 1;
        }
    }
    
    public void entityCountLog()
    {
        // Log the number of entities and number of each type of entity for performance monitoring
        if (System.currentTimeMillis() >= this.lastReport + this.waitPerReport)
        {
            // I should include the FPS in there somehow...
            GameLogger.logInfo("------------------------");
            GameLogger.logInfo("Number of entities: " + EntityHandler.allEntities.size());
            GameLogger.logInfo("Number of projectiles: " + this.allProjectiles.size());
            GameLogger.logInfo("Number of enemies: " + this.allEnemies.size());
            GameLogger.logInfo("Number of particles: " + ParticleHandler.allParticles.size());
            this.lastReport = System.currentTimeMillis();
        }
    }
    
    public void spawnIfPossible()
    {
        // Spawn a new enemy if enough time has passed
        if (System.currentTimeMillis() - this.lastSpawn >= this.waitPerEnemySpawn)
        {
            try
            {
                this.spawnEnemy();
                this.lastSpawn = System.currentTimeMillis();
            }
            catch(SQLException ex)
            {
                GameLogger.logError("Unable to spawn enemy");
                GameLogger.logError(ex.toString());
            }
            
        }
    }
    
    public void spawnPowerUpParticles(int count)
    {
        // Spawn a number of particles
        for (int i = 0; i < count; ++i)
        {
            this.spawnPowerUpParticle();
        }
    }
    
    public void spawnPowerUpParticle()
    {
        // Spawn a new green particle
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.add(51);
        Particle p = new Particle(this.player.getWorldX() + this.player.getWorldSize() / 4, this.player.getWorldY() + this.player.getWorldSize() / 4, 0.25, indexes, 1000, 1);
    }
    
    public void handleKeyboardInput(GameContainer gc)
    {
        // Handle all of the keyboard inputs
        Input input = gc.getInput();
        input.disableKeyRepeat();
        
        if (input.isKeyDown(Input.KEY_ESCAPE))
        {
            input.clearControlPressedRecord();
            input.clearKeyPressedRecord();
            input.clearMousePressedRecord();
            closeGame();
        }
        
        if (input.isKeyDown(Input.KEY_W))
        {
            player.setMovingUp(true);
        }
        else
        {
            player.setMovingUp(false);
        }
        if (input.isKeyDown(Input.KEY_S))
        {
            player.setMovingDown(true);
        }
        else
        {
            player.setMovingDown(false);
        }
        if (input.isKeyDown(Input.KEY_A))
        {
            player.setMovingLeft(true);
        }
        else
        {
            player.setMovingLeft(false);
        }
        if (input.isKeyDown(Input.KEY_D))
        {
            player.setMovingRight(true);
        }
        else
        {
            player.setMovingRight(false);
        }
        
        
        if (input.isKeyDown(Input.KEY_I))
        {
            player.setFacing(0);
        }
        if (input.isKeyDown(Input.KEY_L))
        {
            player.setFacing(1);
        }
        if (input.isKeyDown(Input.KEY_K))
        {
            player.setFacing(2);
        }
        if (input.isKeyDown(Input.KEY_J))
        {
            player.setFacing(3);
        }
        
        if (input.isKeyDown(Input.KEY_SPACE))
        {
            if (this.player.canAttack())
            {
                this.player.attack();
                this.spawnProjectile(this.player.getWorldX() + 0.25, this.player.getWorldY() + 0.25, 0.5, this.player.getFacing());
            }
        }
    }
    
    public void handlePlayerMovement()
    {
        // Move the player if they need to be moved
        if (player.isMovingDown())
        {
            if (player.moveY(player.getMoveDist(), map))
            {
                camera.panY((int) (TILE_SIZE * player.getMoveDist()) );
            }
        }
        if (player.isMovingUp())
        {
            if(player.moveY(-1 * player.getMoveDist(), map))
            {
                camera.panY((int) (-1 * TILE_SIZE * player.getMoveDist()) ) ;
            }
        }
        if (player.isMovingLeft())
        {
            if (player.moveX(-1 * player.getMoveDist(), map))
            {
                camera.panX((int) (-1 * TILE_SIZE * player.getMoveDist()) );
            }
        }
        if (player.isMovingRight())
        {
            if (player.moveX(player.getMoveDist(), map))
            {
                camera.panX((int) (TILE_SIZE * player.getMoveDist()) );
            }
        }
    }
    
    public void clearAllEnemies()
    {
        // Remove all of the enemies
        for (int i = 0; i < this.allEnemies.size(); ++i)
        {
            EntityHandler.allEntities.remove(this.allEnemies.get(i));
            this.allEnemies.remove(i);
            --i;
        }
    }
    
    public void updateStats(int attr, int val)
    {
        // Increase the stats of the player because of the pellet they picked up
        switch (attr)
        {
            case 0:
                // Health boost
                this.player.addHealth(val);
                this.boostText = "HEALTH BOOST!";
                this.startBoostText = System.currentTimeMillis();
                break;
            case 1:
                // Fire rate boost
                this.player.setAttackWaitTime((int) (this.player.getAttackWaitTime() / (1 + val / 100.0) ) );
                this.boostText = "FIRE RATE BOOST!";
                this.startBoostText = System.currentTimeMillis();
                break;
            case 2:
                // Damage boost
                this.projectileDamage *= (1 + val / 100.0);
                this.projectileDamage += 1;
                this.boostText = "DAMAGE BOOST!";
                this.startBoostText = System.currentTimeMillis();
            default:
                break;
        }
    }
    
    private void moveEnemies()
    {
        // Perform a BFS starting at the player's position and make all enemies start to move towards the player
        
        GhostPlayer start = new GhostPlayer(this.player.getWorldX(), this.player.getWorldY(), this.player.getWorldSize(), -1, 0);
        
        Queue<GhostPlayer> q = new LinkedList<GhostPlayer>();
        
        q.add(start);
        
        double dist = this.player.getMoveDist();
        
        Integer[][] neighbourOffsets = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        
        boolean[][] visited = new boolean[MAX_PATH_FIND_DEPTH * 2 + 1][MAX_PATH_FIND_DEPTH * 2 + 1];
        
        while (!q.isEmpty())
        {
            GhostPlayer current = q.poll();
            
            if (current.depth > MAX_PATH_FIND_DEPTH)
            {
                continue;
            }
            
            boolean existed = visited[(int) ( (start.y - current.y) / dist + MAX_PATH_FIND_DEPTH) ][(int) ( (start.x - current.x) / dist + MAX_PATH_FIND_DEPTH) ];
            
            if (existed)
            {
                continue;
            }
            
            visited[(int) ( (start.y - current.y) / dist + MAX_PATH_FIND_DEPTH) ][(int) ( (start.x - current.x) / dist + MAX_PATH_FIND_DEPTH) ] = true;

            
            for (int i = 0; i < this.allEnemies.size(); ++i)
            {
                GameCharacter e = this.allEnemies.get(i);
                if (this.allEnemies.get(i).getWorldX() == current.x && this.allEnemies.get(i).getWorldY() == current.y)
                {
                    e.setTracking(true);
                    switch (current.direction)
                    {
                        case 0:
                            e.moveY(e.getMoveDist(), this.map);
                            e.setFacing(2);
                            break;
                        case 1:
                            e.moveX(-1 * e.getMoveDist(), this.map);
                            e.setFacing(3);
                            break;
                        case 2:
                            e.moveY(-1 * e.getMoveDist(), this.map);
                            e.setFacing(0);
                            break;
                        case 3:
                            e.moveX(e.getMoveDist(), this.map);
                            e.setFacing(1);
                            break;
                        default:
                            break;
                    }
                }
            }
            
            for (int i = 0; i < 4; ++i)
            {
                GhostPlayer neighbour = new GhostPlayer(current.x + dist * neighbourOffsets[i][0], current.y + dist * neighbourOffsets[i][1], current.size, i, current.depth + 1);
                
                if (neighbour.isObstructed(this.map))
                {
                    continue;
                }
                
                q.add(neighbour);
            }
            
        }
        
    }
    
    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException
    {
        // Render all of the graphics
        drawEntities(grphcs);
        drawHUD(grphcs);
    }
    
    public void closeGame()
    {
        // Close the game and save the score in the database
        this.recordScore();
        
        ParticleHandler.clearAllParticles();
        EntityHandler.clearAllEntities();
        
        GameLogger.logInfo("Closing game");
        //InternalTextureLoader.get().clear();
        //SoundStore.get().clear();
        GameLogger.flush();
        
        this.parentGUI.closeGUI();
    }
    
    private void drawEntities(Graphics grphcs)
    {
        // Draw all of the entities to the screen
        int counter = 0;
        for (int i = 0; i < EntityHandler.allEntities.size(); ++i)
        {
            // Go through all of the entities and draw them if they need to be drawn
            
            Entity currentEntity = EntityHandler.allEntities.get(i);
            
            if ( currentEntity.toDraw() )
            {
                ++counter;
                Image image = currentEntity.getImage();
                
                int x = currentEntity.getScreenX();
                int y = currentEntity.getScreenY();
                
                grphcs.drawImage(image, x, y);
            }
        }
    }
    
    private void drawHUD(Graphics grphcs)
    {
        // Draw the Head's Up Display for the player to see
        grphcs.setColor(Color.white);
        grphcs.drawString("Score: " + this.score, 10.f, 30.f);
        
        switch ((int) ((this.player.getHealth() - 1) / this.enemyDamage) )
        {
            case 0:
                grphcs.setColor(Color.red);
                break;
            case 1:
                grphcs.setColor(Color.orange);
                break;
            case 2:
                grphcs.setColor(Color.yellow);
                break;
            default:
                grphcs.setColor(Color.green);
        }
        grphcs.drawString("Health: " + this.player.getHealth(), 150.f, 30.f);
        grphcs.setColor(Color.white);
        
        grphcs.drawString("Fire Rate: " + (int) (1000.0 / this.player.getAttackWaitTime()), 270.f, 30.f);
        grphcs.drawString("Damage: " + this.projectileDamage, 420.f, 30.f);
        
        grphcs.drawString("Number of Pellets: " + this.numPelletsPickedUp + "/10", 550.f, 30.f);
        
        if (System.currentTimeMillis() <= this.startBoostText + this.waitBoostText)
        {   
            grphcs.setColor(Color.yellow);
            grphcs.drawString(this.boostText, 150.f, 0.f);
        }
        if (!GAME_RUNNING)
        {
            grphcs.setColor(Color.red);
            String msg = "YOU DIED!";
            if (this.numPelletsPickedUp == 10)
            {
                msg = "YOU WIN!";
                grphcs.setColor(Color.green);
            }
            grphcs.drawString(msg, SCREEN_WIDTH / 2 - 30, SCREEN_HEIGHT / 2 - 50);
            grphcs.drawString("Press ESCAPE to exit.", SCREEN_WIDTH / 2 - 70, SCREEN_HEIGHT / 2 + 50);
        }
    }
    
    private void setupPlayer() throws SQLException
    {
        // Setup the player character
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        ResultSet rs = DBBridge.query("SELECT * FROM tblCharacters WHERE CharacterName = 'Player'");
        
        String[] obj = DBBridge.processResultSet(rs, " ").replace('\n', ' ').split(" ");
        
        int texX = Integer.parseInt(obj[1]);
        int texY = Integer.parseInt(obj[2]);
        int startIndex = texX + 10 * texY;
        
        for (int i = startIndex; i < startIndex + 8; ++i)
        {
            indexes.add(i);
        }
        
        int health = this.STARTING_PLAYER_HEALTH[this.difficulty];
        
        double moveDist = Double.parseDouble(obj[5]);
        int moveWaitTime = Integer.parseInt(obj[4]);
        
        int x = random(this.map.width - 2) + 1;
        int y = random(this.map.height - 2) + 1;
        
        while (!this.map.grid[y][x].isWalkable())
        {
            x = random(this.map.width - 2) + 1;
            y = random(this.map.height - 2) + 1;
        }
        
        this.player = new GameCharacter(x, y, 1, indexes, health, moveWaitTime, moveDist, 250);
    }
    
    private void spawnEnemy() throws SQLException
    {
        // Spawn a new player somewhere on the map
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        
        if (this.enemyString == null)
        {
            ResultSet rs = DBBridge.query("SELECT * FROM tblCharacters WHERE CharacterName = 'Enemy'");
            this.enemyString = DBBridge.processResultSet(rs, " ");
        }
        
        String[] obj = this.enemyString.replace('\n', ' ').split(" ");
        
        int texX = Integer.parseInt(obj[1]);
        int texY = Integer.parseInt(obj[2]);
        int startIndex = texX + 10 * texY;
        
        for (int i = startIndex; i < startIndex + 8; ++i)
        {
            indexes.add(i);
        }
        
        int health = this.STARTING_ENEMY_HEALTH[this.difficulty];
        double moveDist = Double.parseDouble(obj[5]);
        int moveWaitTime = Integer.parseInt(obj[4]);
        
        int x = random(this.map.width);
        int y = random(this.map.height);
        
        while (!this.map.grid[y][x].isWalkable() || coordOnScreen(x, y))
        {
            // We don't want the enemy to spawn right infront of us or on a tile where they cannot walk
            x = random(this.map.width);
            y = random(this.map.height);
        }
        
        GameCharacter enemy = new GameCharacter(x, y, 1, indexes, health, moveWaitTime, moveDist, 1000);
        this.allEnemies.add(enemy);
    }
    
    private void addNewPellet()
    {
        // Add a new pellet somewhere in the world
        int x = random(this.map.width);
        int y = random(this.map.height);
        
        while (!this.map.grid[y][x].isWalkable() || coordOnScreen(x, y))
        {
            // We don't want the pellet to spawn right infront of us or on a tile where they cannot walk
            x = random(this.map.width);
            y = random(this.map.height);
        }
        
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.add(40);
        int attribute = random(3);
        int value = random(100) + 1;
        
        GameLogger.logInfo("Adding pellet at (" + x + ", " + y + ") with attribute = " + attribute + " and value = " + value);
        
        this.pellet = new Pellet(x + 0.25, y + 0.25, 0.5, indexes, attribute, value);
    }
    
    private boolean coordOnScreen(double x, double y)
    {
        // Return true if (x, y) should be on the screen
        return ((x * TILE_SIZE) > (this.camera.getLeftX())) && 
                ((x * TILE_SIZE) < (this.camera.getLeftX() + this.camera.getWidth())) &&
                ((y * TILE_SIZE) > (this.camera.getTopY())) && 
                ((y * TILE_SIZE) < (this.camera.getTopY() + this.camera.getHeight()));
    }
    
    private void spawnProjectile(double worldX, double worldY, double worldSize, int direction)
    {
        // Spawn a new projectile
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.add(30);
        Projectile projectile = new Projectile(worldX, worldY, worldSize, indexes, 10, 0.5, this.projectileDamage, direction);
        this.allProjectiles.add(projectile);
    }
    
    private int random(int upper)
    {
        // Generate a new random integer n such that 0 <= n < upper
        return (int) (Math.random() * upper);
    }
    
    private void recordScore()
    {
        // Add the player's score to the database
        GameLogger.logInfo("Adding score for player '" + this.playerName + "' to tblHighScores with score " + this.score + " and difficulty " + this.difficulty);
        try
        {
            DBBridge.update("INSERT INTO tblHighScores (PlayerName, Score, Difficulty) VALUES ('" + this.playerName + "', " + this.score + ", " + this.difficulty + ")");
            GameLogger.logInfo("Score successfully recorded");
        }
        catch (SQLException ex)
        {
            GameLogger.logError("The score could not be recorded!");
            GameLogger.logError(ex.toString());
        }
        
    }

    private void updateProjectiles() 
    {
        // Update all of the projectiles
        for (int i = 0; i < this.allProjectiles.size(); ++i)
        {
            Projectile p = this.allProjectiles.get(i);
            
            if (!EntityHandler.allEntities.contains(p))
            {
                this.allProjectiles.remove(p);
                --i;
                continue;
            }
            
            for (int j = 0; j < this.allEnemies.size(); ++j)
            {
                
                if (p.intersects(this.allEnemies.get(j)))
                {
                    this.allEnemies.get(j).dealDamage(p.getDamage());
                    EntityHandler.allEntities.remove(p);
                    this.allProjectiles.remove(p);
                    --i;
                    break;
                }
            }
        }
    }

    private void updateEnemies()
    {
        // Update all of the enemies
        for (int i = 0; i < this.allEnemies.size(); ++i)
        {
            if (!EntityHandler.allEntities.contains(this.allEnemies.get(i)))
                {
                    // This enemy was killed
                    this.score += this.enemyKillBonus;
                    this.enemyKillBonus += 10;
                    
                    this.allEnemies.remove(i);
                    --i;
                    continue;
                }
            
            if (this.player.intersects(this.allEnemies.get(i)))
            {
                // Deal damage to the player
                if (this.allEnemies.get(i).canAttack())
                {
                    this.allEnemies.get(i).attack();
                    this.player.dealDamage(enemyDamage);
                }
            }
            if (!this.allEnemies.get(i).isTracking() && this.allEnemies.get(i).canMove())
            {
                // Map sure the enemies don't stand still
                this.allEnemies.get(i).randomMove(map);
            }
                
        }
    }

    private void checkNumPelletsPickedUp() {
        if (this.GAME_RUNNING && this.numPelletsPickedUp == 10)
        {
            this.GAME_RUNNING = false;
            this.score *= 10;
        }
    }
    
}
