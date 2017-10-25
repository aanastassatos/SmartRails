import javafx.scene.canvas.GraphicsContext;

public class Track
{
  private TrackType trackType;
  private Track left;
  private Track right;
  private Train train;
  private double x;
  private double y;
  
  public Track(TrackType trackType, double x, double y)
  {
    this.trackType = trackType;
    this.x = x;
    this.y = y;
  }
  
  public Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    else return left;
    
  }
  
  public void setLeft(Track left)
  {
    this.left = left;
  }
  
  public void setRight(Track right)
  {
    this.right = right;
  }
  
  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }
  
  //  public boolean findStation(Direction direction, String stationName)
//  {
//    System.out.println(x + " " + y);
//    if(getNextTrack(direction) instanceof Station)
//    {
//      if(((Station) getNextTrack(direction)).getName() == stationName)
//      {
//        System.out.println("SUCCESS");
//        return true;
//      }
//      else return false;
//    }
//
//    else if(getNextTrack(direction) instanceof Switch)
//    {
//      if((((Switch) getNextTrack(direction)).getConnection()).findStation(direction, stationName))
//      {
//        System.out.println(trackType);
//        return true;
//      }
//      else return getNextTrack(direction).findStation(direction, stationName);
//    }
//
//    else
//    {
//      if(getNextTrack(direction).findStation(direction, stationName))
//      {
//        System.out.println(trackType);
//        return true;
//      }
//    }
//
//    return false;
//  }
  
  public TrackType getTrackType()
  {
    return trackType;
  }
  
  
  
  //  public boolean isOccupied()
//  {
//    return occupied;
//  }
//
//  public void setOccupied(boolean occupied)
//  {
//    this.occupied = occupied;
//  }
}
