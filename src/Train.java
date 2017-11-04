/**
 * Train class (implements Runnable interface)
 *
 *
 */

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Train implements Runnable
{
  private Track currentTrack;  //Track the train is currently on
  private Direction direction; //Direction of train
  private String name;  //Train name
  private TrainView trainView; //Draw/redraw train
  private String destination; //Station destination for train
  private Queue<Message> messages; //All messages to be executed

  /**
   * Train constructor:
   * @param name: Name to be associated with train
   * No output
   *
   *            Picks random color to be associated with train instance,
   *            sets name, trainImageName, and trainView
   *            instanciates messages Queue
   */
  Train(String name)
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

  /**
   * sendOff() method:
   * @param destination: String name of destination station track
   * No output
   *
   *                   sends current track first search message
   */
  void sendOff(String destination)
  {
    currentTrack.receiveMessage(new Message(name, MessageType.SEARCH, destination, direction, true));
  }

//  private boolean trackTypeIsSwitch(TrackType trackType)
//  {
//    if(trackType == TrackType.RIGHT_UP_SWITCH) return true;
//    else if(trackType == TrackType.RIGHT_DOWN_SWITCH) return true;
//    else if(trackType == TrackType.LEFT_UP_SWITCH) return true;
//    else if(trackType == TrackType.LEFT_DOWN_SWITCH) return true;
//    return false;
//  }
  
  /**
   * Takes the destination name, and goes through the track, securing the route to the destination by flipping track
   * switches, and turning stop lights red.
   * @param destination
   *
   * will currently set the path to found for tracks with one switch
   */
  public void secureRoute(String destination)
  {
  
  }
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
//  public void findRoute(String destination)
//  {
//    Track nextTrack = currentTrack.getNextTrack(direction);
//    currentTrack.setMessage(MessageType.FOUND);
//    boolean pathFound = false;
//    while (!pathFound)
//    {
//      TrackType tT = nextTrack.getTrackType();
//      if(tT == TrackType.STRAIGHT || tT == TrackType.LIGHT || trackTypeIsSwitch(tT))
//      {
//        if(nextTrack.getMessage() != MessageType.SECURED)
//        {
//          nextTrack.setMessage(MessageType.FOUND);
//          currentTrack = nextTrack;
//          nextTrack = currentTrack.getNextTrack(direction);
//        }
//      }
//      else if(tT == TrackType.STATION)
//      {
//        if(((StationTrack)nextTrack).getName().equals(destination))
//        {
//          pathFound = true;
//          nextTrack.setMessage(MessageType.FOUND);
//        }
//        else
//        {
//          currentTrack = nextTrack;
//          nextTrack = currentTrack.getNextTrack(opDirection());
//          TrackType nextTrackType = nextTrack.getTrackType();
//          boolean toBreak = false;
//          while(nextTrackType != TrackType.STATION && !toBreak)
//          {
//            if(trackTypeIsSwitch(nextTrackType))
//            {
//              if(!((SwitchTrack)nextTrack).getSwitchOn()) toBreak = true;
//            }
//            nextTrack.setMessage(MessageType.NOTFOUND);
//            currentTrack = nextTrack;
//            nextTrack = currentTrack.getNextTrack(opDirection());
//            nextTrackType = nextTrack.getTrackType();
//          }
//          if(trackTypeIsSwitch(nextTrackType))
//          {
//            ((SwitchTrack)nextTrack).setSwitchOn(true);
//            currentTrack = nextTrack;
//            nextTrack = currentTrack.getNextTrack(direction);
//          }
//          else if(nextTrack.getTrackType() == TrackType.STATION)
//          {
//            System.out.println("NO PATH FOUND");
//            //no path can be found
//          }
//        }
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

  /**
   * readMessage() method:
   * @param msg: Message to be read
   * No output
   *
   *           if message type is found, sends message to secure
   *           if message type is secured, sends message to move the train
   */
  private synchronized void readMessage(Message msg)
  {
    switch (msg.messageType)
    {
      case FOUND:
        sendMessage(new Message(name, MessageType.SECURE, msg.sender, direction, true));
        break;
    
      case SECURED:
        sendMessage(new Message(name, MessageType.MOVE, msg.sender, direction, true));
        break;
    }
  }

  /**
   * recieveMessage() method:
   * @param msg: message to be recieved
   * No output
   *
   *          Adds parameter message to queue of messages to be read
   */
  synchronized void receiveMessage(Message msg)
  {
    messages.add(msg);
  }

  /**
   * sendMessage() method:
   * @param msg: Message to be sent
   *
   *           Prints trains current status and sends parameter message to
   *           current track (track the train is on)
   */
  private synchronized void sendMessage(Message msg)
  {
    msg.print(-1,-1);
    currentTrack.receiveMessage(msg);
  }

  /**
   * setDirection() method:
   * @param direction: direction to be set
   * No output
   *
   *                 Sets instance direction to parameter direction
   */
  void setDirection(Direction direction)
  {
    this.direction = direction;
  }

  /**
   * getDirection() method:
   * No parameters
   * @return Current direction train is travelling
   */
  Direction getDirection()
  {
    return direction;
  }

  /**
   * USED IN UNIT TESTING
   * printDirection() method:
   * No parameters
   * No output
   *
   *                Prints current train direction to console
   */
  public void printDirection()
  {
    if(direction == Direction.RIGHT) System.out.println("Right");
    else if(direction == Direction.LEFT) System.out.println("Left");
  }

  /**
   * setCurrentTrack() method:
   * @param currentTrack: track piece
   * No output
   *
   *                    Used to set current track train is on
   *                    and move train to current track
   */
  void setCurrentTrack(Track currentTrack)
  {
    this.currentTrack = currentTrack;
    relocate(currentTrack.getX(), currentTrack.getY());
  }

  /**
   * getName() method:
   * No parameters
   * @return String of train name
   */
  String getName()
  {
    return name;
  }

  /**
   * getTrainView() method:
   * No parameters
   * @return TrainView set to train instance
   */
  TrainView getTrainView()
  {
    return trainView;
  }

  /**
   * relocate() method:
   * @param x: x coordinate to move train to
   * @param y: y coordinate to move train to
   * No output
   *                    If train is at a station, does not
   *                    show train.  If not, sets trainView to visible
   *                    so train can be seen on interface at current location
   */
  private void relocate(double x, double y)
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

  /**
   * readNextMessage() method:
   * No parameters
   * No output
   *
   *                    Pulls message from top of messages
   *                    queue, removes message from queue, and reads
   *                    message
   */
  private synchronized void readNextMessage()
  {
    Message msg = messages.poll();
    messages.remove(msg);
    if(msg != null)
    {
      readMessage(msg);
    }
  }

  /**
   * run() method from Runnable interface
   *
   * When thread is started, run begins reading messages queue for instruction
   */
  @Override
  public void run()
  {
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
