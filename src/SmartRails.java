/**
 * SmartRails class creates a new thread with
 * each trip and starts each train respectively
 *
 * TODO: Will read trips from text file
 */

import java.util.ArrayList;
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
  SmartRails(ArrayList<Train> trains)
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
    //Trip trip = new Trip("B0", "1");
    //Trip otherTrip = new Trip("B2", "3");
    //Trip another = new Trip("A0", "2");
    Queue<String> schedule1 = new ConcurrentLinkedQueue<String>();
    //Queue<String> schedule2 = new ConcurrentLinkedQueue<String>();
    if(destination != null) schedule1.add(destination);
    //schedule1.add("A1");
    trains.get(0).setSchedule(schedule1);
    trains.get(0).receiveMessage(new Message(null, MessageType.START, null, null, -1));
    running = true;
  }
}
