public class Message
{
  String sender;
  String recipient;
  MessageType messageType;
  
  public Message(String sender, MessageType messageType, String recipient)
  {
    this.sender = sender;
    this.messageType = messageType;
    this.recipient = recipient;
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
}
