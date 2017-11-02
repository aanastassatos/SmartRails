import java.util.ArrayList;

public class StationTrack extends Track
{
  private String name;
  private ArrayList<Train> trains;
  
  StationTrack(String name, double x, double y)
  {
    super(TrackType.STATION, x, y);
    this.name = name;
    trains = new ArrayList<>();
  }
  
  /**
   * Takes a train name and a destination station name, finds that train within the station (if it is there), then
   * sets the train on the track, secures the route, and sends the train on its way.
   * @param trip
   */
  public void startTrain(Trip trip)
  {
    Train train = findTrain(trip.train);
    if(train != null)
    {
      train.setDirection(initDirection());
      train.setCurrentTrack(this);
      super.setTrain(train);
      trains.remove(train);
      train.sendOff(trip.destination);
    }
  }
  
  public void addTrain(Train train)
  {
    trains.add(train);
    train.setCurrentTrack(this);
  }
  
  public String getName()
  {
    return name;
  }
  
  @Override
  public synchronized void readMessage(Message msg)
  {
    if(msg.isRecipient(this))
    {
      System.out.println("Station "+name+ "received the message:");
      msg.print(getX(), getY());
      switch(msg.messageType)
      {
        //Do something
        
        case SEARCH:
          sendMessageToNextTrack(new Message(name, MessageType.FOUND, msg.sender, msg.msgDir.getOpposite()));
          break;
          
        case SECURE:
          sendMessageToNextTrack(new Message(name, MessageType.SECURED, msg.sender, msg.msgDir.getOpposite()));
          break;
          
        default:
          break;
      }
    }
    
    else
    {
      super.readMessage(msg);
    }
  }
  
  @Override
  public synchronized void moveTrain()
  {
    if(getTrain() != null)
    {
      if (getNextTrack(getTrain().getDirection()) == null)
      {
        trains.add(getTrain());
        System.out.println("Train is now in Station " + name);
        setTrain(null);
      } else
      {
        super.moveTrain();
      }
    }
  }
  
  private Train findTrain(String trainName)
  {
    for(Train train : trains)
    {
      if(train.getName().equals(trainName)) return train;
    }
    
    return null;
  }
  
  private Direction initDirection()
  {
    if(getNextTrack(Direction.RIGHT) == null) return Direction.LEFT;
    else return Direction.RIGHT;
  }
}
