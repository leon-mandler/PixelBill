import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Tile {

  private final Color color;
  private final boolean isWall;

  public Tile(Color color, boolean isWall){
    this.color = color;
    this.isWall = isWall;

  }

  public boolean isWall(){
    return isWall;
  }

  public Color getColor(){
    return color;
  }
}
