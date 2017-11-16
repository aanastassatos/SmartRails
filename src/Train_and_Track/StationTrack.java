/**
 * StationTrack class
 * Extends Track
 */
package Train_and_Track;

import SmartRails.Direction;
import Train_and_Track.Message.*;

import java.util.ArrayList;

public class StationTrack extends Track
{
  private String name; //Station's name
  private ArrayList<Train> trains; //Trains at station
  
  /**
   * StationTrack constructor
   *
   * @param name: Station name
   * @param x:    x coordinate for drawing station
   * @param y:    y coordinate for drawing station
   */
  public StationTrack(String name, double x, double y)
  {
    super(TrackType.STATION, x, y);
    this.name = name;
    trains = new ArrayList<>();
  }
  
  /**
   * getName() method:
   * No parameters
   *
   * @return String of Station track name
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * addTrain() method:
   *
   * @param train: train to be added to arrayList
   *               No output
   *               <p>
   *               Adds a train to train array list at station
   */
  void addTrain(Train train)
  {
    trains.add(train);
    System.out.println(train.getName() + " was added to " + getName());
    train.setCurrentTrack(this);
    train.setDirection(initDirection());
  }
  
  /**
   * readMessage() method:
   *
   * @param msg: Message to be read
   *             No output
   *             <p>
   *             Reads message and acts on type.
   *             Passes message to next track
   */
  @Override
  void readMessage(Message msg)
  {
    msg.print(getX(), getY());
    if (getNextTrack(msg.msgDir) == null)
    {
      if (msg.isRecipient(this))
      {
        System.out.println("Station " + name + " received the message:");
        switch (msg.messageType)
        {
          case SEARCH:
            addToOutGoing(new Message(name, MessageType.FOUND, msg.sender, msg.msgDir.getOpposite(), msg.correspondenceID));
            break;
          
          case SECURE:
            addToOutGoing(new Message(name, MessageType.SECURED, msg.sender, msg.msgDir.getOpposite(), msg.correspondenceID));
            break;
          
          case MOVE:
            moveTrain();
            addToOutGoing(new Message(name, MessageType.ARRIVED, msg.sender, msg.msgDir.getOpposite(), msg.correspondenceID));
            break;
          
          case FREE:
            addToOutGoing(new Message(name, MessageType.FREED, msg.sender, msg.msgDir.getOpposite(), -1));
            break;
          
          default:
            super.readMessage(msg);
            break;
        }
      }
      
      else
      {
        if (msg.messageType == MessageType.SEARCH)
        {
          addToOutGoing(new Message(msg.sender, MessageType.NOTFOUND, msg.recipient, msg.msgDir.getOpposite(), msg.correspondenceID));
        }
        
        else
        {
          addToOutGoing(msg);
        }
      }
    }
    
    else
    {
      super.readMessage(msg);
    }
  }
  
  /**
   * Sends the given message to the train it is addressed, to the next track, or off into the dark digital abyss that is "null"
   * @param msg
   */
  @Override
  void sendMessage(Message msg)
  {
    Train train = findTrain(msg.recipient);
    if (train != null)
    {
      train.receiveMessage(msg);
    }
    else
    {
      super.sendMessage(msg);
    }
  }
  
  /**
   * moveTrain() method:
   * No parameters
   * No output
   * <p>
   * If a train is at the station, train is added to trains
   */
  @Override
  void moveTrain()
  {
    if (getTrain() != null)
    {
      if (getNextTrack(getTrain().getDirection()) == null)
      {
        System.out.println("Train is now in Station " + name);
        addTrain(getTrain());
        setTrain(null);
      }
      else
      {
        super.moveTrain();
      }
    }
  }
  
  /**
   * findTrain() method:
   *
   * @param trainName: train name of train to be found at station
   * @return train if train is at station
   */
  private Train findTrain(String trainName)
  {
    for (Train train : trains)
    {
      if (train.getName().equals(trainName)) return train;
    }
    
    return null;
  }
  
  /**
   * initDirection() method:
   * No parameters
   *
   * @return Direction train must travel
   */
  private Direction initDirection()
  {
    if (getNextTrack(Direction.RIGHT) == null)
    {
      return Direction.LEFT;
    }
    else
    {
      return Direction.RIGHT;
    }
  }
}
