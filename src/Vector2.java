public class Vector2{

  private int x;
  private int y;

  public Vector2(int x, int y){
    this.x = x;
    this.y = y;
  }

  int getX(){
    return x;
  }

  int getY(){
    return y;
  }

  public Vector2 addVector(Vector2 vector2){
    return new Vector2(x + vector2.getX(), y + vector2.getY());
  }
}
