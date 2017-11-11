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
  private Queue<String> schedule;

  /**
   * Train constructor:
   * @param name: Name to be associated with train
   * No output
   *
   *            Picks random color to be associated with train instance,
   *            sets name, trainImageName, and trainView
   *            instantiates messages Queue
   */
  Train(String name)
  {
    this.name = name;
    messages = new ConcurrentLinkedQueue<>();
    schedule = new ConcurrentLinkedQueue<>();
    new Thread(this).start();
  }
  
  public void setSchedule(Queue<String> schedule)
  {
    this.schedule = schedule;
  }

  /**
   * sendOff() method:
   * @param destination: String name of destination station track
   * No output
   *
   *                   sends current track first search message
   */
  synchronized void findRoute(String destination)
  {
    while(currentTrack.isOccupied())
    {
    }
    currentTrack.receiveMessage(new Message(name, MessageType.SEARCH, destination, direction, Integer.parseInt(name)));
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
    msg.print(-1,-1);
    switch (msg.messageType)
    {
      case FOUND:
        sendMessage(new Message(name, MessageType.SECURE, msg.sender, direction, msg.correspondecnceID));
        break;
    
      case SECURED:
        currentTrack.setTrain(this);
        sendMessage(new Message(name, MessageType.MOVE, msg.sender, direction, msg.correspondecnceID));
        break;
        
      case ARRIVED:
        sendMessage(new Message(name, MessageType.FREE, msg.sender, direction.getOpposite(), msg.correspondecnceID));
        findRoute(schedule.poll());
        break;
        
      case START:
        findRoute(schedule.poll());
        break;
      
      default:
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
  public void setTrainView(TrainView trainView)
  {
    this.trainView = trainView;
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
