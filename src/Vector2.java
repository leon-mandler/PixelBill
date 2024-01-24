import java.util.Vector;

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

  @Override
  public String toString(){
    return "Vector2(" + x + ", " + y + ")";
  }

  //equals method
  @Override
  public boolean equals(Object o){
    if(o == this){
      return true;
    }
    if(!(o instanceof Vector2)){
      return false;
    }
    Vector2 vector2 = (Vector2) o;
    return x == vector2.getX() && y == vector2.getY();
  }

  Vector2 subtractVector(Vector2 position){
    return new Vector2(x - position.getX(), y - position.getY());
  }
}
