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
}
