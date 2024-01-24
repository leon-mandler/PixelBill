import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer extends JPanel{
  public static final int TILE_SIZE = 32;
  private Tile[][] maze;

  public JFrame frame;

  private Image mazeImage;

  public Renderer(){
    frame = new JFrame("Pixel Bill");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.requestFocusInWindow();
    setFocusable(true);
  }

  public void newLevel(Tile[][] maze){
    this.maze = maze;
    initializeMazeImage();
    frame.setSize(maze[0].length * TILE_SIZE, maze.length * TILE_SIZE);
    frame.repaint();
  }

  private void initializeMazeImage(){
    mazeImage = createImage(maze[0].length * TILE_SIZE, maze.length * TILE_SIZE);
    Graphics g = mazeImage.getGraphics();
    for(int y = 0; y < maze.length; y++){
      for(int x = 0; x < maze[y].length; x++){
        g.setColor(maze[x][y].getColor());
        g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }
    }
    g.dispose();
  }


  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(mazeImage, 0, 0, this);
    g.drawString("Score: " + GameBoard.SCORE, 15, 15);
    GameBoard.player.render(g);
    renderEntities(g, new ArrayList<>(GameBoard.entityList));
    renderSnakes(g);
  }

  private void renderEntities(Graphics g, List<Entity> entityList){
    for(int i = 0, entityListSize = entityList.size(); i < entityListSize; i++){
      Entity e = entityList.get(i);
      e.render(g);
    }
  }

  private void renderSnakes(Graphics g){
    List<Snake> snakes = new ArrayList<>(GameBoard.getSnakes());
    for(Snake snake : snakes){
      snake.render(g);
    }
  }
}

