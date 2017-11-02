import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Train implements Runnable
{
  private Track currentTrack;
  private Direction direction;
  private String name;
  private TrainView trainView;
  private String destination;
  private Queue<Message> messages;
  
  public Train(String name)
  {
    this.name = name;
    String trainImageName = "Train_";
    messages = new ConcurrentLinkedQueue<>();
    
    double random = SmartRailsWindow.rand.nextInt((5-1)+1) + 1;
    
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

  public void sendOff(String destination)
  {
    currentTrack.receiveMessage(new Message(name, MessageType.SEARCH, destination, direction));
  }
  
  /**
   * Takes the destination name, and goes through the track, securing the route to the destination by flipping track
   * switches, and turning stop lights red.
   * @param destination
   */
  public void secureRoute(String destination)
  {
    ArrayList<Track> pathway = new ArrayList<>();
    Track nextTrack = currentTrack.getNextTrack(direction);
//    while (nextTrack.getName() != destination)
//    {
//      if(nextTrack.getTrackType() == TrackType.STRAIGHT)
//      {
//        pathway.add(currentTrack);
//        currentTrack.setLocked(true);
//        currentTrack = nextTrack;
//        nextTrack = currentTrack.getNextTrack(direction);
//      }
//      else if(nextTrack.getTrackType() == TrackType.STATION)
//      {
//        nextTrack = destination;
//      }
//      else
//      {
//        pathway.add(currentTrack);
//        currentTrack.setLocked(true);
//        currentTrack = nextTrack;
//        nextTrack = currentTrack.getNextTrack(direction);
//      }
//    }

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
  
  public synchronized void readMessage(Message msg)
  {
    switch (msg.messageType)
    {
      case FOUND:
        sendMessage(new Message(name, MessageType.SECURE, msg.sender, direction));
        break;
    
      case SECURED:
        sendMessage(new Message(name, MessageType.MOVE, msg.sender, direction));
        break;
    }
  }
  
  public synchronized void receiveMessage(Message msg)
  {
    messages.add(msg);
  }
  
  public synchronized void sendMessage(Message msg)
  {
    msg.print(-1,-1);
    currentTrack.receiveMessage(msg);
  }
  
  public void setDirection(Direction direction)
  {
    this.direction = direction;
  }
  
  public Direction getDirection()
  {
    return direction;
  }

  public void printDirection()
  {
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
  
  public synchronized void readNextMessage()
  {
    Message msg = messages.poll();
    messages.remove(msg);
    if(msg != null)
    {
      readMessage(msg);
    }
  }
  
  @Override
  public void run()
  {
    Message msg;
    while(true)
    {
      readNextMessage();
      //      try{
      //        Thread.sleep(50);
      //      } catch (InterruptedException e)
      //      {
      //        System.out.println(Thread.currentThread().getName()+" died.");
      //        break;
      //      }
    }
  }
}
