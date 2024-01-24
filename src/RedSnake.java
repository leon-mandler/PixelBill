import java.awt.*;

public class RedSnake extends EnemySnake{

  private final int lengthDelay;
  private int lengthTickCounter = 0;
  public RedSnake(Vector2 position, int length, int lengthDelay, Vector2 direction){
    super(position, Color.RED, length, direction);
    this.lengthDelay = lengthDelay;
  }

  @Override
  public void move(){
    lengthTickCounter++;
    if(lengthTickCounter > lengthDelay){
      lengthTick();
      lengthTickCounter = 0;
    }
    super.move();
  }
  public void lengthTick(){
    addLength(1);
  }
}
