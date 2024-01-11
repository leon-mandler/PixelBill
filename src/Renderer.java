import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Renderer extends JPanel {
  public static final int TILE_SIZE = 32;
  private final Tile[][] maze;

  private Image mazeImage;
  public Renderer(Tile[][] maze) {
    this.maze = maze;
    JFrame frame = new JFrame("Pixel Bill");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setSize(maze[0].length * TILE_SIZE, maze.length * TILE_SIZE);
    frame.setResizable(false);
    frame.setVisible(true);
    initializeMazeImage();
  }

  private void initializeMazeImage() {
    mazeImage = createImage(maze[0].length * TILE_SIZE, maze.length * TILE_SIZE);
    Graphics g = mazeImage.getGraphics();
    for (int y = 0; y < maze.length; y++) {
      for (int x = 0; x < maze[y].length; x++) {
        if (maze[y][x].isWall()) {
          g.setColor(Color.BLACK);
        } else {
          g.setColor(Color.WHITE);
        }
        g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }
    }
    g.dispose();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(mazeImage, 0, 0, this);
    for(Entity entity : GameBoard.entityList){
      entity.render(g);
    }
  }
}

