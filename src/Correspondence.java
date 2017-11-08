import java.util.ArrayList;

public class Correspondence
{
  private int id;
  private ArrayList<MessageType> messages;
  
  public Correspondence(Message msg)
  {
    messages = new ArrayList<>();
  }
  
  public boolean messageBelongsHere(Message msg)
  {
    return (msg.correspondecnceID == id);
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
