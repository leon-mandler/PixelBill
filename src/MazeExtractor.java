import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MazeExtractor {

  private static final int LEVEL_SIZE = 29;
  private static final String LEVEL_PATH = "/levels/level";

  public Tile[][] getTileArray(int level, int size) {
    if (level < 1 || level > 26) {
      throw new IllegalArgumentException("invalid level number");
    }
    Tile[][] maze = new Tile[size][size];
    try {
      // Load the image as a resource
      InputStream is = getClass().getResourceAsStream(LEVEL_PATH + level + ".png");
      if (is == null) {
        throw new IOException("Resource not found: " + LEVEL_PATH + level + ".png");
      }
      BufferedImage image = ImageIO.read(is);
      for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
          int color = image.getRGB(x, y);
          boolean isWall = (color < 0);
          maze[x][y] = new Tile(isWall, new Vector2(x, y));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return maze;
  }

  public Tile[][] getTileArray(int level) {
    return getTileArray(level, LEVEL_SIZE);
  }
}
