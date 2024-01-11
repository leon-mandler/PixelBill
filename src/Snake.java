import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.Vector;

public class Snake extends Entity{

  private int length;
  private Deque<Vector2> positionStack;

  private Vector2 direction;

  public Snake(Vector2 position, Color color, int length){
    super(position, color);
    this.length = length;
    this.direction = new Vector2(0, -1);
    positionStack = new ArrayDeque<>();
    positionStack.push(position);
  }

  public Vector2 getDirection(){
    return direction;
  }

  private int getLength(){
    return length;
  }

  private void setLength(int length){
    this.length = length;
  }

  public void move(){
    positionStack.push(getPosition().addVector(direction));
    if(positionStack.size() > length){
      positionStack.removeLast();
    }

  }

  @Override
  public void render(Graphics g){
    g.setColor(getColor());
    for(Vector2 position : positionStack){
      g.fillRect(position.getX() * Renderer.TILE_SIZE, position.getY() * Renderer.TILE_SIZE, Renderer.TILE_SIZE, Renderer.TILE_SIZE);
    }
  }
}
