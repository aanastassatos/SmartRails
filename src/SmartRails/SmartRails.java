/**
 * SmartRails class creates a new thread with
 * each trip and starts each train respectively
 *
 * TODO: Will read trips from text file
 */

package SmartRails;

import Train_and_Track.Message.*;
import Train_and_Track.Train;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SmartRails implements Runnable
{
  private ArrayList<Train> trains;
  private boolean running;
  private String destination;


  /**
   * SmartRails constructor:
   * @param trains : Arraylist containing all trains
   * No output
   *
   * Sets trains arraylist to contain all trains
   */
  public SmartRails(ArrayList<Train> trains)
  {
    this.trains = trains;
  }

  public void setDestination(String destination)
  {
    this.destination = destination;
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
    /*LinkedList<String> schedule1 = new LinkedList<>();
    schedule1.add("B1");
    schedule1.add("A1");
    schedule1.add("B0");
    schedule1.add("A2");
    schedule1.add("B3");
    schedule1.add("A0");
    schedule1.add("B0");
    schedule1.add("A1");
    schedule1.add("B2");
    schedule1.add("A3");
    trains.get(0).setSchedule(schedule1);
    */
    trains.get(0).receiveMessage(new Message(null, MessageType.START, null, null, -1));
    running = true;
  }
}
