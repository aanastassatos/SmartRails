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
    Trip trip = new Trip("B0","1");
    Trip otherTrip = new Trip("B1", "3");
    Trip another = new Trip("A0", "2");
//    findTrain(trip);
    lines.get(0).getStartPoint().startTrain(trip);
    lines.get(0).getEndPoint().startTrain(another);
    //lines.get(1).getStartPoint().startTrain(otherTrip);
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
