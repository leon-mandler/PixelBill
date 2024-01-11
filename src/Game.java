import java.awt.*;

public class Game{
  public static void main(String[] args){
    GameBoard board = new GameBoard();
    board.addEntity(new Snake(new Vector2(15, 15), Color.RED, 5));
    while(true){
      board.moveSnakes();
      board.render();
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }
}
