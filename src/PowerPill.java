import java.awt.*;

public class PowerPill extends Entity{

  private static final Color POWER_PILL_COLOR = Color.PINK;
  protected PowerPill(Vector2 position, Color color){
    super(position, color);
  }

  public PowerPill(Vector2 position){
    this(position, POWER_PILL_COLOR);
  }


  @Override
  public void render(Graphics g){
    g.setColor(getColor());
    g.fillOval(getPosition().getX() * Renderer.TILE_SIZE, getPosition().getY() * Renderer.TILE_SIZE, Renderer.TILE_SIZE, Renderer.TILE_SIZE);
  }
}
