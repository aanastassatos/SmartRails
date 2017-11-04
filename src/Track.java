import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Track implements Runnable
{
  private TrackType trackType; //Enum denoting the type of track this is.
  private MessageType message;
  private Train train;  //Train that is currently on the track. Null if none.
  private Track left; //Reference to track piece to the left.
  private Track right;  //Reference to track piece to the left.

  private Queue <Message> messages;
  private boolean locked = false;
  private double x;
  private double y;

  public Track(TrackType trackType, double x, double y)
  {
    this.trackType = trackType;
    this.x = x;
    this.y = y;
    messages = new ConcurrentLinkedQueue<>();
  }
  
  /**
   * Takes a direction and returns the track piece in that direction
   * relative to this track piece.
   * @param direction
   * @return
   */
  public synchronized Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    return left;
  }

  public void setMessage(MessageType message) { this.message = message; }

  public MessageType getMessage() { return message; }
  
  /**
   * Gets the track piece to the right.
   * @return
   */
  public Track getRight()
  {
    return right;
  }
  
  /**
   * Sets the track piece to the right.
   * @param right
   */
  public void setRight(Track right)
  {
    this.right = right;
  }
  
  /**
   * Gets the track piece to the left.
   * @return
   */
  public Track getLeft()
  {
    return left;
  }
  
  public synchronized void setLocked(boolean locked)
  {
    this.locked = locked;
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
   * @param train
   */
  public void setTrain(Train train)
  {
    this.train = train;
  }
  
  /**
   * Gets the train currently on this track.
   * @return
   */
  public Train getTrain()
  {
    return train;
  }
  
  /**
   * Returns a boolean indicating whether or not there is a train on this track piece.
   * @return
   */
  public boolean isOccupied()
  {
    return (train != null);
  }
  
  /**
   * Returns the type of track piece this is.
   * @return
   */
  public TrackType getTrackType()
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
  
  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public synchronized void receiveMessage(Message msg)
  {
    messages.add(msg);
  }
  
  public synchronized void readMessage(Message msg)
  {
    msg.print((int)x, (int)y);
    if(msg.isRecipient(train)) sendMessageToTrain(msg);
    else
    {
      switch (msg.messageType)
      {
        case MOVE:
          moveTrain();
          break;

        case SECURE:
          secureTrack(msg);
          break;

        case FREE:
          freeTrack(msg);
          break;

        default:
          sendMessageToNextTrack(msg);
        //Do something based on message type
      }
    }
  }
  
  private synchronized void secureTrack(Message msg)
  {
    System.out.println("Track "+x+", "+y+" is locked");
    locked = true;
    sendMessageToNextTrack(msg);
  }
  
  private synchronized void freeTrack(Message msg)
  {
    System.out.println("Track "+x+", "+y+" is freed");
    locked = false;
  }
  
  public synchronized void sendMessageToTrain(Message msg)
  {
    if(msg.isRecipient(train)) train.receiveMessage(msg);
  }
  
  public synchronized void sendMessageToNextTrack(Message msg)
  {
    if(getNextTrack(msg.msgDir) != null) getNextTrack(msg.msgDir).receiveMessage(msg);
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
    System.out.println(x+", "+y+": "+Thread.currentThread().getName());
    while(true)
    {
      readNextMessage();
    }
  }
}
