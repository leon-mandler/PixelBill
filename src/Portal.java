import java.awt.*;

public class Portal extends Entity{

  private Portal next;
  private final Color innerColor;

  private final int COOLDOWN;
  private int ticksSince = 0;
  protected Portal(Vector2 position, Color color, Color innerColor, int cooldown){
    super(position, color);
    this.innerColor = innerColor;
    this.COOLDOWN = cooldown;
  }

  public Vector2 teleport(Vector2 pos){
    Vector2 nextPos = next.getPosition();
    if(pos.getX() > 14){
      if(pos.getY() > 14){
        //pos y > 14 && pos x > 14 --> bottom right
        return nextPos.addVector(GameBoard.LEFT_VECTOR);
      }
      //pos y < 14 && pos x > 14 --> top left
      return nextPos.addVector(GameBoard.DOWN_VECTOR);
    }
    if(pos.getY() > 14){
      //pos x < 14 && pos y > 14 --> bottom left
      return nextPos.addVector(GameBoard.UP_VECTOR);
    }
    //pos y < 14 && pos x < 14 --> top right
    return nextPos.addVector(GameBoard.RIGHT_VECTOR);
  }

  public void setNext(Portal next){
    this.next = next;
  }

  @Override
  public void render(Graphics g){
    g.setColor(getColor());
    g.fillRect(getPosition().getX() * Renderer.TILE_SIZE, getPosition().getY() * Renderer.TILE_SIZE, Renderer.TILE_SIZE, Renderer.TILE_SIZE);
    g.setColor(innerColor);
    g.fillRect(getPosition().getX() * Renderer.TILE_SIZE + 5, getPosition().getY() * Renderer.TILE_SIZE + 5, Renderer.TILE_SIZE - 10, Renderer.TILE_SIZE - 10);
  }


}
