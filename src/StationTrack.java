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
  
  public void startTrain(String trainName, String destination)
  {
    Train train = findTrain(trainName);
    secureRoute(destination);
    if(train != null)
    {
      train.setDirection(initDirection());
      train.setCurrentTrack(this);
      super.setTrain(train);
      trains.remove(train);
      moveTrain();
    }
  }
  
  public void addTrain(Train train)
  {
    trains.add(train);
    train.relocate(getX(), getY());
  }
  
  @Override
  public void moveTrain()
  {
    if(getNextTrack(getTrain().getDirection()) == null)
    {
      trains.add(getTrain());
      System.out.println("Train is now in Station "+name);
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
      if(train.getName() == trainName) return train;
    }
    
    return null;
  }

  private void secureRoute(String destination)
  {
    //TODO
  }
  
  private Direction initDirection()
  {
    if(getNextTrack(Direction.RIGHT) == null) return Direction.LEFT;
    else return Direction.RIGHT;
  }
}
