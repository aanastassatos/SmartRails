/**
 * Track class (implements Runnable interface)
 *
 *
 */
package Train_and_Track;

import SmartRails.Direction;
import Train_and_Track.Message.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Track implements Runnable
{
  private TrackType trackType; //Enum denoting the type of track this is.
  private Train train;  //Train that is currently on the track. Null if none.
  private Track left; //Reference to track piece to the left.
  private Track right;  //Reference to track piece to the left.

  private LinkedBlockingQueue<Message> incomingMessages;
  private LinkedBlockingQueue<Message> outgoingMessages;
  private ArrayList<Correspondence> correspondences;
  private int lockedBy;
  private boolean locked = false;
  private boolean secure = false;
  private double x;
  private double y;

  /**
   * Track constructor:
   * @param trackType: Type of instantiated track
   * @param x: x coordinate of track on interface
   * @param y: y coordinate of track on interface
   *         Messages queue instantiated
   */
  public Track(TrackType trackType, double x, double y)
  {
    this.trackType = trackType;
    this.x = x;
    this.y = y;
    incomingMessages = new LinkedBlockingQueue<>();
    outgoingMessages = new LinkedBlockingQueue<>();
    correspondences = new ArrayList<>();
    new Thread(this).start();
  }
  
  /**
   * getNextTrack() method:
   * @param direction: direction in which to get next track
   * @return Track in specified direction
   *
   *            Takes a direction and returns the track piece in that direction
   *            relative to this track piece.
   */
//  synchronized
  Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    return left;
  }
  
  /**
   * setRight() method
   * @param right: Track piece to the right of current track
   * No output
   *            Sets the track piece to the right.
   */
  public void setRight(Track right)
  {
    this.right = right;
  }
  
  /**
   * Gets the track piece to the left.
   * @param left
   */
  public void setLeft(Track left)
  {
    this.left = left;
  }
  
  /**
   * Sets the train currently on this track.
   * @param train: current train to set on track
   */
  public
//  synchronized
  void setTrain(Train train)
  {
    this.train = train;
  }
  
  /**
   * @return train currently on this track
   */
  Train getTrain()
  {
    return train;
  }
  
  /**
   * Returns a boolean indicating whether or not there is a train on this track piece.
   * @return true if train is on track
   */
  public
//  synchronized
  boolean isOccupied()
  {
    return (train != null);
  }
  
  /**
   * @return Track type of track piece
   */
  public TrackType getTrackType()
  {
    return trackType;
  }
  
  /**
   * Moves the train on this track to the next tack in the direction it is going.
   */
  synchronized
  void moveTrain()
  {
    Track next;
    if(train != null)
    {
      next = getNextTrack(train.getDirection());
      if(next.isOccupied())
      {
        System.out.println("CRASH");
      }
      
      train.setCurrentTrack(next);
      next.setTrain(train);
      train = null;
    }
    
    try
    {
      Thread.sleep(500);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  synchronized double getX()
  {
    return x;
  }
  
  synchronized double getY()
  {
    return y;
  }

  /**
   * receiveMessage() method:
   * @param msg: message to be received
   */
  //synchronized
  void receiveMessage(Message msg)
  {
    try
    {
      incomingMessages.put(msg);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  synchronized
  void sendMessage(Message msg)
  {
    if(msg.isRecipient(train)) train.receiveMessage(msg);
    else if(getNextTrack(msg.msgDir) != null) getNextTrack(msg.msgDir).receiveMessage(msg);
  }
  
//  synchronized
  void addToOutGoing(Message msg)
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
   * readMessage() method:
   * @param msg: Message to be read
   */
//  synchronized
  void readMessage(Message msg)
  {
    msg.print((int)x, (int)y);
    switch (msg.messageType)
    {
      case MOVE:
        moveTrain();
        addToOutGoing(msg);
        break;

      case SECURE:
        secureTrack(msg);
        addToOutGoing(msg);
        break;
          
      case FREE:
        freeTrack(msg);
        addToOutGoing(msg);
        break;
        
      default:
        addToOutGoing(msg);
        break;
    }
  }
  
  synchronized void setLocked(boolean locked)
  {
    this.locked = locked;
  }
  
  synchronized void setSecure(boolean secure)
  {
    this.secure = secure;
  }
  
  //  synchronized
  boolean containsCorrespondence(Message msg)
  {
    for(Correspondence c : correspondences)
    {
      if(c.messageBelongsHere(msg)) return true;
    }
    
    return false;
  }
  
//  synchronized
  Correspondence findCorrespondence(Message msg)
  {
    Correspondence newC = null;
    for(Correspondence c : correspondences)
    {
      if(c.messageBelongsHere(msg)) return c;
    }
    
    if(msg.correspondenceID != -1)
    {
      newC = new Correspondence(msg);
      correspondences.add(newC);
    }
    
    return newC;
  }
  
//  synchronized
  void clearCorrespondence(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    correspondences.remove(c);
  }
  
  /**
   * secureTrack() method:
   * @param msg: message to secure current track
   *           Secures and sends message to next track
   */
  synchronized
  void secureTrack(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    if(c.contains(MessageType.FOUND))
    {
      System.out.println("Track " + x + ", " + y + " is locked");
      setSecure(true);
    }
  }

  /**
   * freeTrack() method:
   * No parameters
   * No output
   *                frees current track
   */
  synchronized
  void freeTrack(Message msg)
  {
    if(containsCorrespondence(msg))
    {
      System.out.println("Track " + x + ", " + y + " is freed");
      clearCorrespondence(msg);
    }
    
    secure = false;
    locked = false;
  }
  
  @Override
  public void run()
  {
    System.out.println(x+", "+y+": "+Thread.currentThread().getName());
    while(true)
    {
      try
      {
        Message msg = incomingMessages.take();
        readMessage(msg);
        msg = outgoingMessages.take();
        sendMessage(msg);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
}
