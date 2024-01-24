import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class BlueSnake extends EnemySnake{
  Random random = new Random();

  private final int teleportDelay;
  private final List<Vector2> positions;
  private int tickCounter = 0;
  public BlueSnake(Vector2 position, int length, Vector2 direction, int teleportDelay, List<Vector2> positions){
    super(position, Color.BLUE, length, direction);
    this.teleportDelay = teleportDelay;
    this.positions = positions;
  }

  @Override
  public void move(){
    tickCounter++;
    if(tickCounter > teleportDelay){
      teleport();
    }
    tickCounter = 0;
    super.move();
  }

  public void teleport(){
    setPosition(positions.get(random.nextInt(positions.size())));
  }
}
