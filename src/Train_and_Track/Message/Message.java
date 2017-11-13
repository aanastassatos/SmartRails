/**
 * Message class
 */
package Train_and_Track.Message;

import GUI.TrackMaker;
import SmartRails.*;
import Train_and_Track.StationTrack;
import Train_and_Track.SwitchTrack;
import Train_and_Track.Train;

public class Message
{
  public String sender; //Train name of sender of message
  public String recipient; //StationTrack of destination
  public MessageType messageType;
  public Direction msgDir; //Direction to send the message
  public int correspondecnceID;

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
    this.correspondecnceID = correspondenceID;
  }

  /**
   * isRecipient() method:
   * @param obj:
   * @return true if obj is intended recipient
   */
  public synchronized boolean isRecipient(Object obj)
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
  
  public String getRecipient()
  {
    return recipient;
  }
  
  public String getSender()
  {
    return sender;
  }
  
  /**
   * print() method:
   * @param x: x coordinate
   * @param y
   */
  public synchronized void print(double x, double y)
  {
    System.out.println("Location: X:"+x/ TrackMaker.IMAGE_HEIGHT+", Y:"+y/TrackMaker.IMAGE_WIDTH+"\n" +
                       "Sender: "+sender+"\n" +
                       "Message: "+messageType+"\n"+
                       "Recipient: "+recipient+"\n" +
                       "Direction: "+msgDir+"\n");
  }
  
  public synchronized Message changeDirection()
  {
    return new Message(sender, messageType, recipient, msgDir.getOpposite(), correspondecnceID);
  }
}
