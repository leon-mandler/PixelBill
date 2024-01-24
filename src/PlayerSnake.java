import java.awt.*;

public class PlayerSnake extends Snake{
  private boolean canMove;
  public PlayerSnake(Vector2 position, Color color, int length, Vector2 direction){
    super(position, color, length, direction);
  }

  public void cannotMove(){
    canMove = false;
  }

  public void canMove(){
    canMove = true;
  }

  @Override
  public void move(){
    if(canMove){
      super.move();
    }
  }

}
