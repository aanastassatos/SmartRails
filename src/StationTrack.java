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
   * @param trainName
   * @param destination
   */
  public void startTrain(String trainName, StationTrack destination)
  {
    Train train = findTrain(trainName);
    if(train != null)
    {
      train.setDirection(initDirection());
      train.setCurrentTrack(this);
      super.setTrain(train);
      trains.remove(train);
      train.secureRoute(destination);
      moveTrain();
    }
  }
  
  public void addTrain(Train train)
  {
    trains.add(train);
    train.setCurrentTrack(this);
  }
  
  @Override
  public void moveTrain()
  {
    if(getNextTrack(getTrain().getDirection()) == null)
    {
      trains.add(getTrain());
      System.out.println("Train is now in Station "+ name);
      setTrain(null);
    }
    
    else
    {
      super.moveTrain();
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
