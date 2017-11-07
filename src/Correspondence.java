import java.util.ArrayList;

public class Correspondence
{
  private String correspondent1;
  private String correspondent2;
  private ArrayList<MessageType> messages;
  
  public Correspondence(Message msg)
  {
    correspondent1 = msg.sender;
    correspondent2 = msg.recipient;
    messages = new ArrayList<>();
  }
  
  public boolean messageBelongsHere(Message msg)
  {
    return((correspondent1 == msg.sender && correspondent2 == msg.recipient) ||
        (correspondent1 == msg.sender && correspondent2 == msg.recipient));
  }
  
  public void addMessage(Message msg)
  {
    messages.add(msg.messageType);
  }
  
  public boolean contains(MessageType msg)
  {
    for(MessageType messageType : messages)
    {
      if(messageType == msg) return true;
    }
    
    return false;
  }
}
