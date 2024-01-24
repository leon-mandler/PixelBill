import java.awt.*;

public class Game{

  private static final MazeExtractor mazeExtractor = new MazeExtractor();

  public static void main(String[] args){
    GameBoard board;
    board = new GameBoard();
    board.start();
  }
}
