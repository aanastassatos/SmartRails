import java.util.ArrayList;

public class StationTrack extends Track
{
  private String name;
  private ArrayList<Train> trains;
  
  StationTrack(String name)
  {
    super(TrackType.STATION);
    this.name = name;
    trains = new ArrayList<>();
  }
  
  public void startTrain(String trainName)
  {
    Train train = findTrain(trainName);
    if(train != null)
    {
      train.setDirection(initDirection());
      train.setCurrentTrack(this);
      super.setTrain(train);
      trains.remove(train);
      moveTrain();
    }
  }
  
  public void setTrains(ArrayList<Train> trains)
  {
    this.trains = trains;
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

  private Direction initDirection()
  {
    if(getNextTrack(Direction.RIGHT) == null) return Direction.LEFT;
    else return Direction.RIGHT;
  }
}
