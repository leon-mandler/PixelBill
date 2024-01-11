import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoard{
  private final boolean[][] board = {{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
  {false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false },
  {false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false },
  {false, true, false, true, true, true, true, true, true, false, true, false, true, true, false, true, false, true, true, true, true, false, true, true, true, true, false, true, false },
  {false, true, false, true, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, false, false, true, false, true, false },
  {false, true, false, true, false, true, true, true, true, false, true, true, false, true, false, true, true, true, true, false, true, false, true, true, false, false, false, true, false },
  {false, true, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, false, true, false },
  {false, true, false, true, false, true, true, true, true, true, false, true, true, true, false, true, true, true, true, true, true, true, true, true, false, true, false, true, false },
  {false, true, false, true, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, true, false },
  {false, true, false, true, true, false, true, true, true, true, true, true, false, true, false, false, false, true, true, true, true, false, true, false, true, true, false, true, false },
  {false, true, false, false, false, false, true, false, false, false, false, true, false, true, true, true, false, false, false, false, false, false, true, false, false, false, false, true, false },
  {false, true, true, true, true, false, true, false, true, true, false, true, false, false, false, false, false, true, true, false, true, false, true, false, true, true, true, true, false },
  {false, true, false, false, false, false, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, false, true, false, false, false, false, true, false },
  {false, true, false, true, true, true, true, true, true, false, true, false, true, false, false, false, true, false, true, true, true, false, true, true, true, true, false, true, false },
  {false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false },
  {false, true, false, true, true, true, true, false, true, true, true, false, true, false, false, false, true, false, true, false, true, true, true, true, true, true, false, true, false },
  {false, true, false, false, false, false, true, false, true, false, false, false, true, true, false, true, true, false, false, false, false, false, true, false, false, false, false, true, false },
  {false, true, true, true, true, false, true, false, true, false, true, true, false, false, false, false, false, true, false, true, true, false, true, false, true, true, true, true, false },
  {false, true, false, false, false, false, true, false, false, false, false, false, false, true, true, true, false, true, false, false, false, false, true, false, false, false, false, true, false },
  {false, true, false, true, true, false, true, false, true, true, true, true, false, false, false, true, false, true, true, true, true, true, true, false, true, true, false, true, false },
  {false, true, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, true, false, true, false },
  {false, true, false, true, false, true, true, true, true, true, true, true, true, true, false, true, true, true, false, true, true, true, true, true, false, true, false, true, false },
  {false, true, false, true, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, true, false },
  {false, true, false, false, false, true, true, false, true, false, true, true, true, true, false, true, false, true, true, false, true, true, true, true, false, true, false, true, false },
  {false, true, false, true, false, false, false, false, true, false, false, false, false, true, false, true, false, false, true, false, false, false, false, false, false, true, false, true, false },
  {false, true, false, true, true, true, true, false, true, true, true, true, false, true, false, true, true, false, true, false, true, true, true, true, true, true, false, true, false },
  {false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false },
  {false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false },
  {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false }};

  private static final Color WALL_COLOR = new Color(26, 26, 24);
  private static final Color FLOOR_COLOR = Color.WHITE;

  public static final List<Entity> entityList = new ArrayList<>();
  private Renderer renderer;
  public GameBoard(){
    Tile[][] tiles = new Tile[29][29];
    for(int y = 0; y < tiles.length; y++){
      for(int x = 0; x < tiles[y].length; x++){
        tiles[x][y] = new Tile((board[x][y] ? WALL_COLOR : FLOOR_COLOR), board[x][y]);
      }
    }
    renderer = new Renderer(tiles);
  }

  public void addEntity(Entity entity){
    entityList.add(entity);
  }
  public void moveSnakes(){
    for(Entity entity : entityList){
      if(entity instanceof Snake snake){
          snake.move();
      }
    }
  }

  public void render(){
    renderer.repaint();
  }
  public boolean getBoardCoordinate(int x, int y){
    return board[x][y];
  }
}
