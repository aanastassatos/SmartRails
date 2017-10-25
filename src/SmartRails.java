import java.util.ArrayList;

public class SmartRails implements Runnable
{
  private ArrayList<Line> lines;
  
  public SmartRails(ArrayList<Line> lines)
  {
    this.lines = lines;
    this.lines.get(0).findStation("9");
  }
  
  @Override
  public void run()
  {
  
  }
}
