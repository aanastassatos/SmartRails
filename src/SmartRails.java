import java.util.ArrayList;

public class SmartRails implements Runnable
{
  private ArrayList<Line> lines;
  
  public SmartRails(ArrayList<Line> lines)
  {
    this.lines = lines;
  }
  
  @Override
  public void run()
  {
    this.lines.get(0).getStartPoint().startTrain("train", "B");
  }
}
