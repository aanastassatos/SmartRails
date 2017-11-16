/**
 * Stores the messages of a specified correspondence between the train and the tracks (never used)
 */

package Train_and_Track.Message;

import java.util.ArrayList;

public class Correspondence
{
  private int id; //The identification number of the correspondence
  private ArrayList<MessageType> messages; //The messages that have been sent
  private boolean switchValue;  //used in switch correspondences to determine the final switch value (never used)
  
  /**
   * Constructs the correspondence based on the given message.
   * @param msg
   */
  public Correspondence(Message msg)
  {
    messages = new ArrayList<>();
    id = msg.correspondenceID;
    switchValue = false;
  }
  
  /**
   * Checks whether or not a message belongs in this correspondence by comparing the id of both the message and correspondence.
   * @param msg
   * @return boolean
   */
  public boolean messageBelongsHere(Message msg)
  {
    return (msg.correspondenceID == id);
  }
  
  /**
   * Checks whether or not a correspondence contains a certain message type.
   * @param msg
   * @return boolean
   */
  public boolean contains(MessageType msg)
  {
    for(MessageType messageType : messages)
    {
      if(messageType == msg) return true;
    }
    
    return false;
  }
  
  /**
   * Adds the given message to the correspondence.
   * @param msg
   */
  public void addMessage(Message msg)
  {
    messages.add(msg.messageType);
  }
  
  /**
   * Sets the switch value for this correspondenc
   * @param switchValue
   */
  public void setSwitchValue(boolean switchValue)
  {
    this.switchValue = switchValue;
  }
  
  /**
   * returns the switch value for this correspondence
   * @return
   */
  public boolean getSwitchValue()
  {
    return switchValue;
  }
  
  /**
   * prints a visual representation of the correspondence
   */
  public void print()
  {
    System.out.print("CorrID: " +id+"\n" +
                     "Messages:"+ "\n");
    
    for(MessageType messageType : messages)
    {
      System.out.print(messageType+"\n\n");
    }
  }
}
