public class Message
{
  String sender;
  String recipient;
  MessageType messageType;
  Direction msgDir;
  
  public Message(String sender, MessageType messageType, String recipient, Direction msgDir)
  {
    this.sender = sender;
    this.messageType = messageType;
    this.recipient = recipient;
    this.msgDir = msgDir;
  }
  
  public boolean isRecipient(Object obj)
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
  
  public void print(double x, double y)
  {
    System.out.println("Location: "+x+", "+y+"\n" +
                       "Sender: "+sender+"\n" +
                       "Message: "+messageType+"\n"+
                       "Recipient: "+recipient+"\n" +
                       "Direction: "+msgDir+"\n");
  }
}
