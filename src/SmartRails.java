/**
 * SmartRails class creates a new thread with
 * each trip and starts each train respectively
 *
 * TODO: Will read trips from text file
 */

import java.util.ArrayList;

public class SmartRails implements Runnable
{
  private ArrayList<Line> lines; //All lines on tracks
  private boolean running;

  /**
   * SmartRails constructor:
   * @param lines : Arraylist containing all lines
   * No output
   *
   * Sets lines arraylist to contain all lines
   */
  SmartRails(ArrayList<Line> lines)
  {
    this.lines = lines;
  }

  /**
   * run() method:  From runnable interface
   * No parameters/No output
   *
   * Called when thread is started.  Sets trips and begins trains
   */
  @Override
  public void run()
  {
    Trip trip = new Trip("B0","1");
    Trip otherTrip = new Trip("B2", "3");
    Trip another = new Trip("A0", "2");
//    findTrain(trip);
     lines.get(0).getStartPoint().startTrain(trip);
    //lines.get(0).getEndPoint().startTrain(another);
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
