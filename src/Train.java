
import java.util.ArrayList;

public class Train
{
  private Track currentTrack;
  private Direction direction;
  private String name;
  private TrainView trainView;
  
  public Train(String name)
  {
    this.name = name;
    String trainImageName = "Train_";
    double random = (Math.random()*5) + 1;
    
    switch((int)random)
    {
      case 1:
        trainImageName = trainImageName + "Blue";
        break;
  
      case 2:
        trainImageName = trainImageName + "Green";
        break;
  
      case 3:
        trainImageName = trainImageName + "Purple";
        break;
        
      case 4:
        trainImageName = trainImageName + "Red";
        break;
      
      case 5:
        trainImageName = trainImageName + "Yellow";
        break;
        
      default:
        trainImageName = "train";
        break;
    }
    
    trainImageName = trainImageName + ".png";
    
    trainView = new TrainView(trainImageName);
  }

  public Direction opDirection()
  {
    if(direction == Direction.LEFT) return Direction.RIGHT;
    else if(direction == Direction.RIGHT) return Direction.LEFT;
    else if(direction == Direction.DOWN) return Direction.UP;
    else if(direction == Direction.UP) return Direction.DOWN;
    else return null;
  }

  private boolean trackTypeIsSwitch(TrackType trackType)
  {
    if(trackType == TrackType.RIGHT_UP_SWITCH) return true;
    else if(trackType == TrackType.RIGHT_DOWN_SWITCH) return true;
    else if(trackType == TrackType.LEFT_UP_SWITCH) return true;
    else if(trackType == TrackType.LEFT_DOWN_SWITCH) return true;
    return false;
  }

  /**
   * Takes the destination name, and goes through the track, securing the route to the destination by flipping track
   * switches, and turning stop lights red.
   * @param destination
   *
   * will currently set the path to found for tracks with one switch
   */
  public void findRoute(String destination)
  {
    Track nextTrack = currentTrack.getNextTrack(direction);
    currentTrack.setMessage(MessageType.FOUND);
    boolean pathFound = false;
    while (!pathFound)
    {
      TrackType tT = nextTrack.getTrackType();
      if(tT == TrackType.STRAIGHT || tT == TrackType.LIGHT || trackTypeIsSwitch(tT))
      {
        if(nextTrack.getMessage() != MessageType.SECURED)
        {
          nextTrack.setMessage(MessageType.FOUND);
          currentTrack = nextTrack;
          nextTrack = currentTrack.getNextTrack(direction);
        }
      }
      else if(tT == TrackType.STATION)
      {
        if(((StationTrack)nextTrack).getName().equals(destination))
        {
          pathFound = true;
          nextTrack.setMessage(MessageType.FOUND);
        }
        else
        {
          currentTrack = nextTrack;
          nextTrack = currentTrack.getNextTrack(opDirection());
          TrackType nextTrackType = nextTrack.getTrackType();
          boolean toBreak = false;
          while(nextTrackType != TrackType.STATION && !toBreak)
          {
            if(trackTypeIsSwitch(nextTrackType))
            {
              if(!((SwitchTrack)nextTrack).getSwitchOn()) toBreak = true;
            }
            nextTrack.setMessage(MessageType.NOTFOUND);
            currentTrack = nextTrack;
            nextTrack = currentTrack.getNextTrack(opDirection());
            nextTrackType = nextTrack.getTrackType();
          }
          if(trackTypeIsSwitch(nextTrackType))
          {
            ((SwitchTrack)nextTrack).setSwitchOn(true);
            currentTrack = nextTrack;
            nextTrack = currentTrack.getNextTrack(direction);
          }
          else if(nextTrack.getTrackType() == TrackType.STATION)
          {
            System.out.println("NO PATH FOUND");
            //no path can be found
          }
        }
      }
    }

    //TODO
    // SPECIFICATIONS:
    // This method should be called by StationTrack at the beginning of a trip and by LightTrack after a route is freed by
    // another train.
    //
    // Should move through the tracks starting at "currentTrack," finding until it finds the station corresponding to
    // "destination."  As it is doing this, it should check to see if there are any red light tracks in its path, as this
    // would mean the path is already secured by another train.  If the route is free, flip the appropriate track switches,
    // and change the appropriate lights in order to get the train to its destination.  If the route is secured by another
    // train, secure the route up to the first red light rail so the train can move to that light track where it should
    // wait for track to be freed.
  }

  /**
   * Frees the route behind where a train has already moved.
   */
  public void freeRoute()
  {
    //TODO
    //SPECIFICATIONS:
    //This method should be called every time the train passes a switch.
    //
    //Should go through and reset switch and lights corresponding to that switch.
  }
  
  public void setDirection(Direction direction)
  {
    this.direction = direction;
  }
  
  public Direction getDirection()
  {
    return direction;
  }

  public void printDirection() {
    if(direction == Direction.RIGHT) System.out.println("Right");
    else if(direction == Direction.LEFT) System.out.println("Left");
  }
  
  public void setCurrentTrack(Track currentTrack)
  {
    this.currentTrack = currentTrack;
    relocate(currentTrack.getX(), currentTrack.getY());
  }
  
  public String getName()
  {
    return name;
  }
  
  public TrainView getTrainView()
  {
    return trainView;
  }
  
  public void relocate(double x, double y)
  {
    if(currentTrack instanceof StationTrack)
    {
      trainView.setVisible(false);
    }
    else
    {
      trainView.setVisible(true);
    }
    
    trainView.move(x, y);
  }
}
