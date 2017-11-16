/**
 * Message class used by the tracks and the train to convey information to each other
 */
package Train_and_Track.Message;

import GUI.TrackMaker;
import SmartRails.*;
import Train_and_Track.StationTrack;
import Train_and_Track.Train;

public class Message
{
  public String sender; //Train name of sender of message
  public String recipient; //StationTrack of destination
  public MessageType messageType;  //The type of message this is.
  public Direction msgDir; //Direction to send the message
  public int correspondenceID;  //The id of this message for correspondence sorting

  /**
   * Message constructor:
   * @param sender
   * @param messageType
   * @param recipient
   * @param msgDir
   */
  public Message(String sender, MessageType messageType, String recipient, Direction msgDir, int correspondenceID)
  {
    this.sender = sender;
    this.messageType = messageType;
    this.recipient = recipient;
    this.msgDir = msgDir;
    this.correspondenceID = correspondenceID;
  }

  /**
   * Checks if the given object is the recipient of this message.
   * @param obj:
   * @return true if obj is intended recipient
   */
  public boolean isRecipient(Object obj)
  {
    if(obj != null)
    {
      if (obj instanceof Train)
      {
        if (((Train) obj).getName().equals(recipient)) return true;
      }
      
      else if (obj instanceof StationTrack)
      {
        if (((StationTrack) obj).getName().equals(recipient)) return true;
      }
    }
    
    return false;
  }
  
  /**
   * Prints a visual representation of this message and the current location of it.
   * @param x
   * @param y
   */
  public void print(double x, double y)
  {
    System.out.println("Location: X:"+x/ TrackMaker.IMAGE_HEIGHT+", Y:"+y/TrackMaker.IMAGE_WIDTH+"\n" +
                       "Sender: "+sender+"\n" +
                       "Message: "+messageType+"\n"+
                       "Recipient: "+recipient+"\n" +
                       "Direction: "+msgDir+"\n");
  }
  
  /**
   * Returns the same message except going the opposite direction
   * @return
   */
  public Message changeDirection()
  {
    return new Message(sender, messageType, recipient, msgDir.getOpposite(), correspondenceID);
  }
}
