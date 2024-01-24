import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Tile{
  private final Color color;
  private final boolean isWall;

  private boolean hasEntity;
  private Entity entity;

  private final Vector2 position;

  public Tile(boolean isWall, Vector2 position){
    this.color = isWall ? GameBoard.WALL_COLOR : GameBoard.FLOOR_COLOR;
    this.isWall = isWall;
    this.position = position;
    hasEntity = false;
  }

  public Vector2 getPosition(){
    return position;
  }

  public void setEntity(Entity entity){
    this.entity = entity;
    hasEntity = true;
  }

  public boolean hasPowerPill(){
    if(!hasEntity){
      return false;
    }
    return entity instanceof PowerPill;
  }

  public void removeEntity(){
    entity = null;
    hasEntity = false;
  }

  public boolean hasEntity(){
    return hasEntity;
  }

  public PowerPill getPowerPill(){
    if(!hasPowerPill()){
      throw new IllegalArgumentException("Tile"+ getPosition() + " does not have a PowerPill");
    }
    return (PowerPill) entity;
  }

  public Portal getPortal(){
    if(!hasPortal()){
      throw new IllegalArgumentException("Tile"+ getPosition() + " does not have a Portal");
    }
    return (Portal) entity;
  }


  public boolean isWall(){
    return isWall;
  }

  public Color getColor(){
    return color;
  }

  @Override
  public String toString(){
    return "Tile: " + position.toString() + " " + isWall;
  }

  boolean hasPortal(){
    if(!hasEntity){
      return false;
    }
    return entity instanceof Portal;
  }
}
