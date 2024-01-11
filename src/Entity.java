import java.awt.*;

public abstract class Entity{
  private Vector2 position;

  private Color color;


  //default getter for position
  public Vector2 getPosition(){
    return position;
  }

  //default setter for position
  public void setPosition(Vector2 position){
    this.position = position;
  }

  //default getter for color
  public Color getColor(){
    return color;
  }

  //default setter for color
  public void setColor(Color color){
    this.color = color;
  }

  //default constructor
  public Entity(Vector2 position, Color color){
    this.position = position;
    this.color = color;
  }

  public abstract void render(Graphics g);

}
