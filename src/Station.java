import java.util.ArrayList;

public class Station extends Track
{
  private ArrayList<Train> trains;
  private String name;
  
  public Station(String name, int x, int y)
  {
    super(TrackType.STATION, x, y);
    this.name = name;
  }
  
  public String getName()
  {
    return name;
  }
}
