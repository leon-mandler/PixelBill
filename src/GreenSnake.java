import java.awt.*;

public class GreenSnake extends EnemySnake{

  private boolean boosted = false;
  private int boostedTickCounter = 0;

  private final int boostedTicks;
  private final int boostedDelay;
  public GreenSnake(Vector2 position, int length, Vector2 direction, int boostedTicks, int boostedDelay){
    super(position, Color.GREEN, length, direction);
    this.boostedDelay = boostedDelay;
    this.boostedTicks = boostedTicks;
  }

  public void boost(){
    boosted = true;
    boostedTickCounter = 0;
  }
  @Override
  public void move(){
    if(boosted){
      if(boostedTickCounter == 0){
        setMoveDelay(boostedDelay);
      }
      boostedTickCounter++;
      if(boostedTickCounter > boostedTicks){
        boosted = false;
        setMoveDelay(STANDARD_MOVE_DELAY);
      }
    }
    super.move();
  }
}
