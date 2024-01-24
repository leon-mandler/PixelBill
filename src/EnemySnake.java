import java.awt.*;
import java.rmi.UnexpectedException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class EnemySnake extends Snake{

  public static final int STANDARD_MOVE_DELAY = 15;
  private int moveDelay;
  private int tickCounter = 0;
  public EnemySnake(Vector2 position, Color color, int length, Vector2 direction){
    super(position, color, length, direction);
    this.moveDelay = STANDARD_MOVE_DELAY;
  }

  @Override
  public void move(){
    if(tickCounter < moveDelay){
      tickCounter++;
      return;
    }
    tickCounter = 0;
    setDirection(chooseDirection());
    super.move();
  }

  protected void setMoveDelay(int moveDelay){
    this.moveDelay = moveDelay;
  }

  private Vector2 chooseDirection(){
    List<Tile> surroundingTiles = GameBoard.getSurroundingTiles(this.getPosition());
    Collections.shuffle(surroundingTiles);
    Vector2 bestChoice = null;

    for (Tile tile : surroundingTiles) {
      Vector2 potentialDirection = tile.getPosition().subtractVector(getPosition());
      if (!tile.isWall()) {
        if (!isInPositionStack(tile.getPosition())) {
          return potentialDirection; // Preferred choice
        } else if (bestChoice == null) {
          bestChoice = potentialDirection; // Possible choice
        }
      }
    }

    return bestChoice != null ? bestChoice : GameBoard.NULL_VECTOR; // Fallback to NULL_VECTOR if no choice found
  }

}
