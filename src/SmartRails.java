import java.util.ArrayList;

public class SmartRails implements Runnable
{
  private ArrayList<Line> lines;
  private boolean running;
  
  public SmartRails(ArrayList<Line> lines)
  {
    this.lines = lines;
  }
  
  @Override
  public void run()
  {
    StationTrack endpoint = lines.get(0).getEndPoint();
    lines.get(0).getStartPoint().startTrain("1", endpoint);
    running = true;
//    while(true)
//    {
//      try
//      {
//        Thread.sleep(50);
//      } catch (InterruptedException e)
//      {
//        running = false;
//        killTracks();
//        System.out.println("Smart Rails is dead");
//        break;
//      }
//    }
  }
  
//  private void killTracks()
//  {
//    for(Line line : lines)
//    {
//      line.getStartPoint().kill();
//    }
//  }
//
//  public void kill()
//  {
//    while(running)
//    {
//      Thread.currentThread().interrupt();
//    }
//  }
}
