/**
 * Message class
 */

class Message
{
  String sender; //Train name of sender of message
  String recipient; //StationTrack of destination
  MessageType messageType;
  Direction msgDir; //Direction to send the message

  /**
   * Message constructor:
   * @param sender
   * @param messageType
   * @param recipient
   * @param msgDir
   */
  Message(String sender, MessageType messageType, String recipient, Direction msgDir)
  {
    this.sender = sender;
    this.messageType = messageType;
    this.recipient = recipient;
    this.msgDir = msgDir;
  }

  /**
   * isRecipient() method:
   * @param obj:
   * @return true if obj is intended recipient
   */
  boolean isRecipient(Object obj)
  {
    if(obj instanceof Train)
    {
      if(((Train) obj).getName().equals(recipient)) return true;
    }
    
    else if(obj instanceof StationTrack)
    {
      if(((StationTrack) obj).getName().equals(recipient)) return true;
    }
    
    return false;
  }

  /**
   * print() method:
   * @param x: x coordinate
   * @param y
   */
  void print(double x, double y)
  {
    System.out.println("Location: "+x+", "+y+"\n" +
                       "Sender: "+sender+"\n" +
                       "Message: "+messageType+"\n"+
                       "Recipient: "+recipient+"\n" +
                       "Direction: "+msgDir+"\n");
  }
}
