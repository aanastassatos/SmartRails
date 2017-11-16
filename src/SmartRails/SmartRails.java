/**
 * Sends a start message to each train.
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

  /**
   * run() method:  From runnable interface
   * No parameters/No output
   *
   * Sends a start message to each train and that is it.
   */
  @Override
  public void run()
  {
    for(int i = 0; i < trains.size(); i++)
    {
      trains.get(i).receiveMessage(new Message(null, MessageType.START, null, null, -1));
    }
  }
}
