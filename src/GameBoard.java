import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard implements Runnable, KeyListener{

  //COLOR CONSTANTS
  public static final Color WALL_COLOR = new Color(43, 45, 48);
  public static final Color FLOOR_COLOR = Color.WHITE;
  public static final Color PLAYER_COLOR = Color.YELLOW;
  public static final Color PORTAL_COLOR = Color.CYAN;
  public static final Color INNER_PORTAL_COLOR = Color.WHITE;


  //VECTOR CONSTANTS
  private static final int UP_KEY = KeyEvent.VK_W;
  private static final int DOWN_KEY = KeyEvent.VK_S;
  private static final int LEFT_KEY = KeyEvent.VK_A;
  private static final int RIGHT_KEY = KeyEvent.VK_D;
  public static final Vector2 UP_VECTOR = new Vector2(0, -1);
  public static final Vector2 DOWN_VECTOR = new Vector2(0, 1);
  public static final Vector2 LEFT_VECTOR = new Vector2(-1, 0);
  public static final Vector2 RIGHT_VECTOR = new Vector2(1, 0);
  public static final Vector2 NULL_VECTOR = new Vector2(0, 0);



  //GAME VARIABLES
  public static int SCORE;
  public static int LEVEL;
  public int LEVEL_SCORE;


  //GAME LOOP CONSTANTS
  private static final int TICKS_PER_SECOND = 20;
  private static final int MOVEMENT_TICKS_PER_SECOND = 10;
  private static final long SKIP_TICKS = 1000 / TICKS_PER_SECOND;
  private static final long MOVEMENT_SKIP_TICKS = 1000 / MOVEMENT_TICKS_PER_SECOND;
  private long nextGameTick = System.currentTimeMillis();
  private long nextMovementTick = System.currentTimeMillis();

  //CONSTANTS
  private static final int RED_SNAKE_START_LENGTH = 3;
  public static final int RED_SNAKE_LENGTH_INCREASE_DELAY = MOVEMENT_TICKS_PER_SECOND * 5;
  private static final int GREEN_SNAKE_LENGTH = 8;
  private static final int GREEN_BOOST_DURATION = MOVEMENT_TICKS_PER_SECOND * 5;
  private static final int GREEN_BOOSTED_SPEED_MULTIPLIER = 4;
  private static final int GREEN_BOOSTED_MOVE_DELAY = (int) Math.ceil((double) EnemySnake.STANDARD_MOVE_DELAY / GREEN_BOOSTED_SPEED_MULTIPLIER);
  private static final int BLUE_SNAKE_START_LENGTH = 6;
  private static final int BLUE_TELEPORT_DELAY = MOVEMENT_TICKS_PER_SECOND * 20;
  private static final int PORTAL_COOLDOWN = MOVEMENT_TICKS_PER_SECOND * 5;
  //GLOBAL VARIABLES ENTITIES
  public static Player player;
  public static final List<Entity> entityList = new ArrayList<>();
  private static RedSnake redSnake;
  private static GreenSnake greenSnake;
  private static BlueSnake blueSnake;
  private final Renderer renderer;

  //GLOBAL BOARD CONSTANTS
  private static Tile[][] tiles;
  private final List<Vector2> powerPillPositions;
  private boolean running;
  //HELP CLASSES
  private static final MazeExtractor mazeExtractor = new MazeExtractor();
  private final Random random = new Random();

  public GameBoard(){
    SCORE = 0;
    LEVEL = 1;
    tiles = mazeExtractor.getTileArray(LEVEL);
    powerPillPositions = new ArrayList<>();
    renderer = new Renderer();
    renderer.frame.addKeyListener(this);
    initLevel();
  }

  private void initLevel(){
    LEVEL_SCORE = 0;
    player = new Player(new Vector2(14, 14), PLAYER_COLOR);
    entityList.clear();
    tiles = mazeExtractor.getTileArray(LEVEL);
    powerPillPositions.clear();
    initPowerPillPositions(powerPillPositions, tiles);
    initPowerPills();
    initPortals();
    redSnake = null;
    greenSnake = null;
    blueSnake = null;
    renderer.newLevel(tiles);
    renderer.repaint();
  }


  private void nextLevel(){
    LEVEL++;
    if(LEVEL > 26){
      System.exit(0);
    }
    initLevel();
  }

  public void start(){
    running = true;
    new Thread(this).start();
  }

  @Override
  public void run(){
    while(running){
      if(System.currentTimeMillis() - nextGameTick >= SKIP_TICKS){
        updateGameLogic();
        nextGameTick = System.currentTimeMillis();
      }

      if(System.currentTimeMillis() - nextMovementTick >= MOVEMENT_SKIP_TICKS){
        updateMovement();
        nextMovementTick = System.currentTimeMillis();
      }
      renderGame();
    }
  }

  private void updateGameLogic(){
    // Game logic that needs to be updated at GAME_TICKS_PER_SECOND
    checkEntityCollisions();
    switch(LEVEL_SCORE){
      case 1:
        if(redSnake == null){
          spawnRedSnake();
        }
        break;
      case 3:
        if(greenSnake == null){
          spawnGreenSnake();
        }
        break;
      case 9:
        if(blueSnake == null){
          spawnBlueSnake();
        }
        break;
      case 30:
        nextLevel();
        break;
    }
  }

  private void updateMovement(){
    player.move();
    moveSnakes();
  }


  private void spawnBlueSnake(){
    blueSnake = new BlueSnake(new Vector2(14, 14), BLUE_SNAKE_START_LENGTH, UP_VECTOR, BLUE_TELEPORT_DELAY, powerPillPositions);
  }

  private void spawnGreenSnake(){
    greenSnake = new GreenSnake(new Vector2(14, 14), GREEN_SNAKE_LENGTH, UP_VECTOR, GREEN_BOOST_DURATION, GREEN_BOOSTED_MOVE_DELAY);
  }

  private void spawnRedSnake(){
    redSnake = new RedSnake(new Vector2(14, 14), RED_SNAKE_START_LENGTH, RED_SNAKE_LENGTH_INCREASE_DELAY, UP_VECTOR);
  }

  private void initPowerPillPositions(List<Vector2> list, Tile[][] tiles){
    for(int y = 2; y < tiles.length - 2; y++){
      for(int x = 2; x < tiles[y].length - 2; x++){
        if(!tiles[x][y].isWall()){
          list.add(new Vector2(x, y));
        }
      }
    }
  }

  private void initPowerPills(){
    for(int i = 0; i < 16; i++){
      addPowerPill();
    }
  }

  private void initPortals(){
    Portal p1 = new Portal(new Vector2(2, 2), PORTAL_COLOR, INNER_PORTAL_COLOR, PORTAL_COOLDOWN);
    Portal p2 = new Portal(new Vector2(2, 26), PORTAL_COLOR, INNER_PORTAL_COLOR, PORTAL_COOLDOWN);
    Portal p3 = new Portal(new Vector2(26, 26), PORTAL_COLOR, INNER_PORTAL_COLOR, PORTAL_COOLDOWN);
    Portal p4 = new Portal(new Vector2(26, 2), PORTAL_COLOR, INNER_PORTAL_COLOR, PORTAL_COOLDOWN);
    p1.setNext(p2);
    p2.setNext(p3);
    p3.setNext(p4);
    p4.setNext(p1);
    entityList.add(p1);
    entityList.add(p2);
    entityList.add(p3);
    entityList.add(p4);
    getTileAtCoordinate(p1.getPosition()).setEntity(p1);
    getTileAtCoordinate(p2.getPosition()).setEntity(p2);
    getTileAtCoordinate(p3.getPosition()).setEntity(p3);
    getTileAtCoordinate(p4.getPosition()).setEntity(p4);
  }

  public static List<Tile> getSurroundingTiles(Vector2 position){
    List<Tile> surroundingTiles = new ArrayList<>();
    surroundingTiles.add(getTileAtCoordinate(position.addVector(UP_VECTOR)));
    surroundingTiles.add(getTileAtCoordinate(position.addVector(DOWN_VECTOR)));
    surroundingTiles.add(getTileAtCoordinate(position.addVector(LEFT_VECTOR)));
    surroundingTiles.add(getTileAtCoordinate(position.addVector(RIGHT_VECTOR)));
    return surroundingTiles;
  }

  private void addPowerPill(){
    Vector2 position;
    do{
      position = powerPillPositions.get(random.nextInt(powerPillPositions.size()));
    } while(getTileAtCoordinate(position).hasEntity() || hasSnake(position));
    PowerPill pill = new PowerPill(position);
    entityList.add(pill);
    getTileAtCoordinate(position).setEntity(pill);
  }

  private boolean hasSnake(Vector2 position){
    for(Entity entity : entityList){
      if(entity instanceof Snake){
        Snake snake = (Snake) entity;
        return snake.getPosition().equals(position);
      }
    }
    return false;
  }

  private void consumePowerPill(PowerPill pill){
    getTileAtCoordinate(pill.getPosition()).removeEntity();
    entityList.remove(pill);
    for(Entity entity : entityList){
      if(entity instanceof GreenSnake){
        GreenSnake snake = (GreenSnake) entity;
        snake.boost();
      }
    }
  }

  public void moveSnakes(){
    if(redSnake != null){
      redSnake.move();
    }
    if(greenSnake != null){
      greenSnake.move();
    }
    if(blueSnake != null){
      blueSnake.move();
    }
  }

  public static Tile getTileAtCoordinate(Vector2 v){
    return tiles[v.getX()][v.getY()];
  }

  private void checkEntityCollisions(){
    checkPowerPillCollisions();
    checkPortalCollisions();
    checkPlayerSnakeCollisions();
  }

  private void checkPlayerSnakeCollisions(){
    for(Snake snake : getSnakes()){
      if(snake.getPosition().equals(player.getPosition())){
        initLevel();
      }
    }
  }

  private void checkPortalCollisions(){
    Vector2 pos = player.getPosition();
    if(getTileAtCoordinate(pos).hasPortal()){
      player.setPosition(getTileAtCoordinate(pos).getPortal().teleport(pos));
    }
  }

  private void checkPowerPillCollisions(){
    Vector2 playerPosition = player.getPosition();
    if(getTileAtCoordinate(playerPosition).hasPowerPill()){
      consumePowerPill(getTileAtCoordinate(playerPosition).getPowerPill());
      LEVEL_SCORE++;
      SCORE++;
      addPowerPill();
    }
  }

  public boolean wallInDirectionPlayer(Vector2 direction){
    return getTileAtCoordinate(player.getPosition().addVector(direction)).isWall();
  }

  private boolean wallInDirectionSnake(Snake snake, Vector2 direction){
    return getTileAtCoordinate(snake.getPosition().addVector(direction)).isWall();
  }

  private void renderGame(){
    renderer.repaint();
  }

  public static List<Snake> getSnakes(){
    List<Snake> snakes = new ArrayList<>();
    if(redSnake != null){
      snakes.add(redSnake);
    }
    if(greenSnake != null){
      snakes.add(greenSnake);
    }
    if(blueSnake != null){
      snakes.add(blueSnake);
    }
    return snakes;
  }

  private boolean movedByKeyEvent = false;


  @Override
  public void keyPressed(KeyEvent e){
    switch(e.getKeyCode()){
      case UP_KEY:
        if(wallInDirectionPlayer(UP_VECTOR)){
          return;
        }
        player.setDirectionY(UP_VECTOR);
        break;
      case DOWN_KEY:
        if(wallInDirectionPlayer(DOWN_VECTOR)){
          return;
        }
        player.setDirectionY(DOWN_VECTOR);
        break;
      case LEFT_KEY:
        if(wallInDirectionPlayer(LEFT_VECTOR)){
          return;
        }
        player.setDirectionX(LEFT_VECTOR);
        break;
      case RIGHT_KEY:
        if(wallInDirectionPlayer(RIGHT_VECTOR)){
          return;
        }
        player.setDirectionX(RIGHT_VECTOR);
        break;
      case KeyEvent.VK_ESCAPE:
        System.exit(0);
    }
  }

  @Override
  public void keyReleased(KeyEvent e){
    switch(e.getKeyCode()){
      case UP_KEY:
      case DOWN_KEY:
        player.setDirectionY(NULL_VECTOR);
        break;
      case LEFT_KEY:
      case RIGHT_KEY:
        player.setDirectionX(NULL_VECTOR);
        break;
      case KeyEvent.VK_ESCAPE:
        System.exit(0);
    }
  }

  @Override
  public void keyTyped(KeyEvent e){

  }
}
