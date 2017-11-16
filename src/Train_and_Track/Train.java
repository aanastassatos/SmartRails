/**
 * Train class (implements Runnable interface)
 *
 *
 */
package Train_and_Track;

import SmartRails.Direction;
import Train_and_Track.Message.*;
import Train_and_Track.Track;
import GUI.TrainView;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Train implements Runnable
{
  private Track currentTrack;  //Track the train is currently on
  private Direction direction; //Direction of train
  private String name;  //Train name
  private TrainView trainView; //Draw/redraw train
  private String destination; //Station destination for train
  private LinkedList<String> schedule; //LinkedList containing the destinations for the train
  private String startStation;  //Station that train starts each trip at.
  private LinkedBlockingQueue<Message> incomingMessages;  //Linked blocking queue containing all the messages to be read.
  private LinkedBlockingQueue<Message> outgoingMessages;  //Linked blocking queue containing all the messages to be sent.

  /**
   * Train constructor:
   * @param name: Name to be associated with train
   * No output
   *
   *            Picks random color to be associated with train instance,
   *            sets name, trainImageName, and trainView
   *            instantiates messages Queue
   */
  public Train(String name)
  {
    this.name = name;
    incomingMessages = new LinkedBlockingQueue<>();
    outgoingMessages = new LinkedBlockingQueue<>();
    schedule = new LinkedList<>();
    new Thread(this).start();
  }
  
  /**
   * Sets the train's schedule
   * @param schedule
   */
  public void setSchedule(LinkedList<String> schedule)
  {
    this.schedule = schedule;
  }
  
  /**
   * getTrainView() method:
   * No parameters
   * @return TrainView set to train instance
   */
  public void setTrainView(TrainView trainView)
  {
    this.trainView = trainView;
  }
  
  /**
   * run() method from Runnable interface
   *
   * When thread is started, run begins reading messages queue for instruction and sending messages
   */
  @Override
  public void run()
  {
    while(true)
    {
      try
      {
        Message msg = incomingMessages.take();
        if(msg != null)
        {
          readMessage(msg);
        }
        
        msg = outgoingMessages.take();
        
        sendMessage(msg);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * recieveMessage() method:
   * @param msg: message to be recieved
   * No output
   *
   *          Adds parameter message to queue of messages to be read
   */
  public void receiveMessage(Message msg)
  {
    try
    {
      incomingMessages.put(msg);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * readMessage() method:
   * @param msg: Message to be read
   * No output
   *
   *           if message type is found, sends message to secure
   *           if message type is secured, sends message to move the train
   */
  private void readMessage(Message msg)
  {
    msg.print(-1,-1);
    
    if(msg.messageType == MessageType.SEARCH || msg.messageType == MessageType.SECURE || msg.messageType == MessageType.MOVE)
    {
      addToOutGoing(new Message(name, msg.messageType, msg.sender, direction, msg.correspondenceID));
    }
    
    else
    {
      switch (msg.messageType)
      {
        case FOUND:
          addToOutGoing(new Message(name, MessageType.SECURE, msg.sender, direction, msg.correspondenceID));
          break;
        
        case SECURED:
          currentTrack.setTrain(this);
          addToOutGoing(new Message(name, MessageType.MOVE, msg.sender, direction, msg.correspondenceID));
          break;
        
        case ARRIVED:
          addToOutGoing(new Message(name, MessageType.FREE, startStation, direction, msg.correspondenceID));
          break;
        
        case FREED:
          findRoute(schedule.poll());
          break;
        
        case START:
          findRoute(schedule.poll());
          break;
        
        default:
          break;
      }
    }
  }
  
  /**
   * sendMessage() method:
   * @param msg: Message to be sent
   *
   *           Prints trains current status and sends parameter message to
   *           current track (track the train is on)
   */
  private void sendMessage(Message msg)
  {
    currentTrack.receiveMessage(msg);
  }
  
  /**
   * Adds given message to outgoingMessages
   * @param msg
   */
  private void addToOutGoing(Message msg)
  {
    try
    {
      outgoingMessages.put(msg);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * sendOff() method:
   * @param destination: String name of destination station track
   * No output
   *
   *                   sends current track first search message
   */
  void findRoute(String destination)
  {
    if(destination != null)
    {
      startStation = ((StationTrack) currentTrack).getName();
      addToOutGoing(new Message(name, MessageType.SEARCH, destination, direction, Integer.parseInt(name)));
    }
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
   * getName() method:
   * No parameters
   * @return String of train name
   */
  String getName()
  {
    return name;
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
  private synchronized void relocate(double x, double y)
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
}
