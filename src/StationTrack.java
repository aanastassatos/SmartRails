/**
 * StationTrack class
 */

import java.util.ArrayList;

public class StationTrack extends Track
{
  private String name; //Station's name
  private ArrayList<Train> trains; //Trains at station

  /**
   * StationTrack constructor:
   * @param name: Station name
   * @param x: x coordinate for drawing station
   * @param y: y coordinate for drawing station
   */
  StationTrack(String name, double x, double y)
  {
    super(TrackType.STATION, x, y);
    this.name = name;
    trains = new ArrayList<>();
  }

  /**
   * addTrain() method:
   * @param train: train to be added to arrayList
   * No output
   *
   *             Adds a train to train array list at station
   */
  void addTrain(Train train)
  {
    trains.add(train);
    System.out.println(train.getName()+" was added to "+getName());
    train.setCurrentTrack(this);
    train.setDirection(initDirection());
  }

  /**
   * getName() method:
   * No parameters
   * @return String of Station track name
   */
  String getName()
  {
    return name;
  }

  /**
   * readMessage() method:
   * @param msg: Message to be read
   * No output
   *
   *              Reads message and acts on type.
   *              Passes message to next track
   */
  @Override
  public synchronized void readMessage(Message msg)
  {
    if(msg.isRecipient(this))
    {
      System.out.println("Station "+name+ " received the message:");
      msg.print(getX(), getY());
      switch(msg.messageType)
      {
        case SEARCH:
          sendMessageToNextTrack(new Message(name, MessageType.FOUND, msg.sender, msg.msgDir.getOpposite(), msg.correspondecnceID));
          break;
          
        case SECURE:
          sendMessageToNextTrack(new Message(name, MessageType.SECURED, msg.sender, msg.msgDir.getOpposite(), msg.correspondecnceID));
          break;
          
        case MOVE:
          moveTrain();
          findTrain(msg.sender).receiveMessage(new Message(name, MessageType.ARRIVED, msg.sender, msg.msgDir.getOpposite(), msg.correspondecnceID));
          break;
          
        default:
          super.readMessage(msg);
          break;
      }
    }
    
    else if(findTrain(msg.recipient) != null)
    {
      findTrain(msg.recipient).receiveMessage(msg);
    }
    
    else
    {
      if(getNextTrack(msg.msgDir) == null)
      {
        if (msg.messageType == MessageType.SEARCH)
        {
          sendMessageToNextTrack(new Message(msg.sender, MessageType.NOTFOUND, msg.recipient, msg.msgDir.getOpposite(), msg.correspondecnceID));
        }
        else if (msg.messageType == MessageType.FREE && getNextTrack(msg.msgDir) == null)
        {
          freeTrack(msg.changeDirection());
        }
      }
      
      else super.readMessage(msg);
    }
  }
  
  /**
   * moveTrain() method:
   * No parameters
   * No output
   *
   *              If a train is at the station, train is added to trains
   *
   */
  @Override
  synchronized void moveTrain()
  {
    if(getTrain() != null)
    {
      if (getNextTrack(getTrain().getDirection()) == null)
      {
        trains.add(getTrain());
        System.out.println("Train is now in Station " + name);
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
   * @param trainName: train name of train to be found at station
   * @return train if train is at station
   */
  private Train findTrain(String trainName)
  {
    for(Train train : trains)
    {
      if(train.getName().equals(trainName)) return train;
    }
    
    return null;
  }

  /**
   * initDirection() method:
   * No parameters
   * @return Direction train must travel
   */
  private Direction initDirection()
  {
    if(getNextTrack(Direction.RIGHT) == null) return Direction.LEFT;
    else return Direction.RIGHT;
  }
}
