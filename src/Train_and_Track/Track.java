/**
 * Track class (implements Runnable interface)
 * Deals with all the major functions of the tracks.
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

  private LinkedBlockingQueue<Message> incomingMessages; //Linked blocking queue containing all the messages to be read.
  private LinkedBlockingQueue<Message> outgoingMessages; //Linked blocking queue containing all the messages to be sent.
  private ArrayList<Correspondence> correspondences;  //ArrayList containing correspondences.
  private boolean locked = false; //Boolean denoting whether or not this track is locked.
  private boolean secure = false; //Boolean denoting whether or not this track is secure.
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
   * While the program is running, the track takes a message out of incoming messages, reads it, does what it needs
   * to do with it, then takes a message out of outgoing messages and sends it.
   */
  @Override
  public void run()
  {
    System.out.println(x+", "+y+": "+Thread.currentThread().getName());
    while(true)
    {
      try
      {
        Message msg = incomingMessages.take();
        
        if(msg.correspondenceID != -1)
        {
          findCorrespondence(msg).addMessage(msg);
        }
        
        readMessage(msg);
        msg = outgoingMessages.take();
        sendMessage(msg);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * receiveMessage() method:
   * @param msg: message to be received
   */
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
  
  /**
   * Sends the given message to the next track.
   * @param msg
   */
  void sendMessage(Message msg)
  {
    if(msg.isRecipient(train)) train.receiveMessage(msg);
    else if(getNextTrack(msg.msgDir) != null) getNextTrack(msg.msgDir).receiveMessage(msg);
  }
  
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
  
  /**
   * getNextTrack() method:
   * @param direction: direction in which to get next track
   * @return Track in specified direction
   *
   *            Takes a direction and returns the track piece in that direction
   *            relative to this track piece.
   */
  Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    return left;
  }
  
  /**
   * Sets the train currently on this track.
   * @param train: current train to set on track
   */
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
   * Moves the train on this track to the next tack in the direction it is going.
   */
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
  
  /**
   * Returns a boolean indicating whether or not there is a train on this track piece.
   * @return true if train is on track
   */
  synchronized boolean isOccupied()
  {
    return (train != null);
  }
  
  /**
   * Sets the locked boolean value.
   * @param locked
   */
  void setLocked(boolean locked)
  {
    this.locked = locked;
  }
  
  /**
   * Sets the secure boolean value.
   * @param secure
   */
  void setSecure(boolean secure)
  {
    this.secure = secure;
  }
  
  /**
   * secureTrack() method:
   * @param msg: message to secure current track
   *           Secures and sends message to next track
   */
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
  void freeTrack(Message msg)
  {
    if(containsCorrespondence(msg))
    {
      System.out.println("Track " + x + ", " + y + " is freed");
      clearCorrespondence(msg);
    }
    
    setSecure(false);
    setLocked(false);
  }
  
  /**
   * Finds the correspondence corresponding to the message given. If there is none, it creates one.
   * @param msg
   * @return Correspondence
   */
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
  
  /**
   * Returns a boolean value denoting whether or not this track's correspondences contains a correspondence corresponding
   * to the given message.
   * @param msg
   * @return boolean
   */
  boolean containsCorrespondence(Message msg)
  {
    for(Correspondence c : correspondences)
    {
      if(c.messageBelongsHere(msg)) return true;
    }
    
    return false;
  }
  
  /**
   * Clears the correspondence corresponding to the given message.
   * @param msg
   */
  void clearCorrespondence(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    correspondences.remove(c);
  }
  
  /**
   * Returns the X value of this track piece.
   * @return
   */
  double getX()
  {
    return x;
  }
  
  /**
   * Returns the Y value of this track piece.
   * @return
   */
  double getY()
  {
    return y;
  }
  
  /**
   * @return Track type of track piece
   */
  TrackType getTrackType()
  {
    return trackType;
  }
}
