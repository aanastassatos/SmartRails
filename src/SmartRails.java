import java.util.ArrayList;

public class SmartRails implements Runnable
{
  private ArrayList<Line> lines;
  
  public SmartRails(ArrayList<Line> lines)
  {
    this.lines = lines;
    Train train = new Train("train");
    ArrayList<Train> trains = new ArrayList<>();
    trains.add(train);
    this.lines.get(0).getStartPoint().setTrains(trains);
    this.lines.get(0).getStartPoint().startTrain("train");
  }
  
  @Override
  public void run()
  {
  
  }
}
