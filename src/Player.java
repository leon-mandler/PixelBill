import java.awt.*;

public class Player extends Entity{

  Vector2 getDirectionX(){
    return directionX;
  }

  void setDirectionX(Vector2 directionX){
    this.directionX = directionX;
  }

  Vector2 getDirectionY(){
    return directionY;
  }

  void setDirectionY(Vector2 directionY){
    this.directionY = directionY;
  }

  private Vector2 directionX;
  private Vector2 directionY;

  public Player(Vector2 position, Color color){
    super(position, color);
    this.directionX = GameBoard.NULL_VECTOR;
    this.directionY = GameBoard.NULL_VECTOR;
  }

  public void move(){
    if(!wallInFront(directionX)){
      setPosition(getPosition().addVector(directionX));
    }
    if(!wallInFront(directionY)){
      setPosition(getPosition().addVector(directionY));
    }
  }

  public void move(Vector2 direction){
    if(wallInFront(direction)){
      return;
    }
    setPosition(getPosition().addVector(direction));
  }

  private boolean wallInFront(Vector2 direction){
    return GameBoard.getTileAtCoordinate(getPosition().addVector(direction)).isWall();
  }

  @Override
  public void render(Graphics g){
    g.setColor(getColor());
    g.fillRect(getPosition().getX() * Renderer.TILE_SIZE, getPosition().getY() * Renderer.TILE_SIZE, Renderer.TILE_SIZE, Renderer.TILE_SIZE);
  }
}