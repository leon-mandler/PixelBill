import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake extends Entity{

  private int length;
  private final Deque<Vector2> positionStack;

  private Vector2 direction;
  private final List<Color> colors;
  private final Random random = new Random();

  public Snake(Vector2 position, Color color, int length, Vector2 direction){
    super(position, color);
    this.length = length;
    this.direction = direction;
    colors = new ArrayList<>();
    colors.add(color);
    for(int i = 0; i < 15; i++){
      addColor();
    }
    Collections.shuffle(colors);
    positionStack = new ArrayDeque<>();
    positionStack.add(position);
  }

  public void addColor(){
    if(getColor().getRed() > 0){
      colors.add(new Color(colors.get(colors.size() - 1).getRed() - 10, getColor().getGreen(), getColor().getBlue()));
      return;
    } else if(getColor().getGreen() > 0){
      colors.add(new Color(getColor().getRed(), colors.get(colors.size() - 1).getGreen() - 10, getColor().getBlue()));
      return;
    }
    colors.add(new Color(getColor().getRed(), getColor().getGreen(), colors.get(colors.size() - 1).getBlue() - 10));
  }

  public Vector2 getDirection(){
    return direction;
  }

  public List<Vector2> getPositionStack(){
    return new ArrayList<>(positionStack);
  }

  public void setDirection(Vector2 direction){
    this.direction = direction;
  }

  int getLength(){
    return length;
  }

  public boolean isInPositionStack(Vector2 position){
    for(Vector2 vector2 : positionStack){
      if(vector2.equals(position)){
        return true;
      }
    }
    return false;
  }

  public void addLength(int length){
    this.length += length;
  }

  public void move(){
    setPosition(getPosition().addVector(direction));
    positionStack.push(getPosition());
    if(positionStack.size() > length){
      positionStack.removeLast();
    }
  }

  @Override
  public void render(Graphics g){
    Vector2[] positionStack = this.positionStack.toArray(new Vector2[0]);
    for(int i = 0; i < positionStack.length; i++){
      Vector2 position = positionStack[i];
      g.setColor(colors.get(i % colors.size()));
      g.fillRect(position.getX() * Renderer.TILE_SIZE, position.getY() * Renderer.TILE_SIZE, Renderer.TILE_SIZE, Renderer.TILE_SIZE);
    }
  }

  public boolean checkSelfCollision(){
    for(Vector2 position : positionStack){
      if(position.equals(getPosition()) && position != getPosition()){
        return true;
      }
    }
    return false;
  }
}
