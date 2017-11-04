/**
 * Track class (implements Runnable interface)
 *
 *
 */

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Track implements Runnable
{
  MessageType message;
  private TrackType trackType; //Enum denoting the type of track this is.
  private Train train;  //Train that is currently on the track. Null if none.
  private Track left; //Reference to track piece to the left.
  private Track right;  //Reference to track piece to the left.

  private Queue <Message> messages;
  private boolean locked = false;
  private double x;
  private double y;

  /**
   * Track constructor:
   * @param trackType: Type of instanciated track
   * @param x: x coordinate of track on interface
   * @param y: y coordinate of track on interface
   *         Messages queue instanciated
   */
  Track(TrackType trackType, double x, double y)
  {
    this.trackType = trackType;
    this.x = x;
    this.y = y;
    messages = new ConcurrentLinkedQueue<>();
  }
  
  /**
   * getNextTrack() method:
   * @param direction: direction in which to get next track
   * @return Track in specified direction
   *
   *            Takes a direction and returns the track piece in that direction
   *            relative to this track piece.
   */
  public synchronized Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    return left;
  }
  
  /**
   * getRight() method:
   * @return Track piece to the right
   */
  public Track getRight()
  {
    return right;
  }
  
  /**
   * setRight() method
   * @param right: Track piece to the right of current track
   * No output
   *            Sets the track piece to the right.
   */
  void setRight(Track right)
  {
    this.right = right;
  }

  /**
   * getLeft() method:
   * @return Track piece to the left
   */
  public Track getLeft()
  {
    return left;
  }

  /**
   * setLocked() method:
   * @param locked: boolean whether track should be locked
   */
  public synchronized void setLocked(boolean locked)
  {
    this.locked = locked;
  }
  
  /**
   * Gets the track piece to the left.
   * @param left
   */
  void setLeft(Track left)
  {
    this.left = left;
  }
  
  /**
   * Sets the train currently on this track.
   * @param train: current train to set on track
   */
  public void setTrain(Train train)
  {
    this.train = train;
  }
  
  /**
   * @return train currently on this track
   */
  public Train getTrain()
  {
    return train;
  }
  
  /**
   * Returns a boolean indicating whether or not there is a train on this track piece.
   * @return true if train is on track
   */
  boolean isOccupied()
  {
    return (train != null);
  }
  
  /**
   * @return Track type of track piece
   */
  TrackType getTrackType()
  {
    return trackType;
  }
  
  /**
   * Moves the train on this track to the next tack in the direction it is going.
   */
  public synchronized void moveTrain()
  {
    Track next;
    if(train != null)
    {
      next = getNextTrack(train.getDirection());
      if(next.isOccupied())
      {
        System.out.println("CRASH");
      }
      if(next.message == MessageType.SECURED)
      {
        train.setCurrentTrack(next);
        next.setTrain(train);
        train = null;

        try
        {
          Thread.sleep(500);
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }

        next.moveTrain();
      }
    }
  }
  
//  public void kill()
//  {
//    while(Thread.currentThread().isAlive())
//    {
//      Thread.currentThread().interrupt();
//    }
//
//    if(getNextTrack(Direction.RIGHT) != null) getNextTrack(Direction.RIGHT).kill();
//  }
  
  double getX()
  {
    return x;
  }
  
  double getY()
  {
    return y;
  }

  /**
   * receiveMessage() method:
   * @param msg: message to be received
   */
  synchronized void receiveMessage(Message msg)
  {
    message = msg.messageType;
    messages.add(msg);
  }

  /**
   * readMessage() method:
   * @param msg: Message to be read
   */
  public synchronized void readMessage(Message msg)
  {
    msg.print((int)x, (int)y);
    if(msg.isRecipient(train)) sendMessageToTrain(msg);
    else
    {
      message = msg.messageType;
      switch (message)
      {
        case MOVE:
          moveTrain();
          break;

        case SECURE:
          if(getNextTrack(msg.msgDir).message == MessageType.FOUND)
          {
            secureTrack(msg);
            sendMessageToNextTrack(msg);
          }
          break;

        case FREE:
          if(!msg.forward)
          {
            freeTrack();
            sendMessageToNextTrack(msg);
          }
          else
          {
            freeTrack();
          }
          break;

        default:
          sendMessageToNextTrack(msg);
          break;
      }
    }
  }

  /**
   * secureTrack() method:
   * @param msg: message to secure current track
   *           Secures and sends message to next track
   */
  synchronized void secureTrack(Message msg)
  {
    if(message == MessageType.FOUND)
    {
      System.out.println("Track " + x + ", " + y + " is locked");
      locked = true;
      sendMessageToNextTrack(msg);
    }
  }

  /**
   * freeTrack() method:
   * No parameters
   * No output
   *                frees current track
   */
  synchronized void freeTrack()
  {
    System.out.println("Track "+x+", "+y+" is freed");
    locked = false;
  }
  
  public synchronized void sendMessageToNextTrack(Message msg)
  {
    if(getNextTrack(msg.msgDir) != null) getNextTrack(msg.msgDir).receiveMessage(msg);
  }

  /**
   * sendMessageToTrain() method:
   * @param msg:
   * No output
   *           sends message to train on track if recipient
   */
  private synchronized void sendMessageToTrain(Message msg)
  {
    if(msg.isRecipient(train)) train.receiveMessage(msg);
  }

  /**
   * readNextMessage() method:
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
  
  @Override
  public void run()
  {
    System.out.println(x+", "+y+": "+Thread.currentThread().getName());
    while(true)
    {
      readNextMessage();
//      if(messages.size() > 0)
//      {
//        msg = messages.get(0);
//        msg.print(0, 0);
//        readMessage(msg);
//        messages.remove(msg);
//      }
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
