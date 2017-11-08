import java.util.ArrayList;

/**
 * SwitchTrack class extends Track
 * Is used for all switch tracks
 *
 *
 */

public class SwitchTrack extends Track
{
  private boolean switchOn; //If on, switch track will move train to switched line
  private SwitchTrack connection; //SwitchTrack connected to current switch track
  private Direction direction;

  /**
   * SwitchTrack() Constructor:
   * @param trackType: Track type to be set in Track class
   * @param x: x coordinate for track image
   * @param y: y coordinate for track image
   */
  SwitchTrack(TrackType trackType, double x, double y)
  {
    super(trackType, x, y);
    switchOn = false;
    
    switch (trackType)
    {
      case LEFT_DOWN_SWITCH:
      case LEFT_UP_SWITCH:
        direction = Direction.LEFT;
        break;
      
      case RIGHT_UP_SWITCH:
      case RIGHT_DOWN_SWITCH:
        direction = Direction.RIGHT;
        break;
    }
  }
  
  /**
   * getNextTrack() method:
   * @param direction: Direction to pull track from
   * @return Track on correct direction of current track
   *
   *                Returns track in direction of parameter direction
   *                If switch is on, returns track on next line
   */
  @Override
  synchronized Track getNextTrack(Direction direction)
  {
    TrackType trackType = getTrackType();
    if(switchOn)
    {
      if((direction == Direction.RIGHT &&
          (trackType == TrackType.LEFT_DOWN_SWITCH || trackType == TrackType.LEFT_UP_SWITCH)) ||
          (direction == Direction.LEFT &&
          (trackType == TrackType.RIGHT_DOWN_SWITCH || trackType == TrackType.RIGHT_UP_SWITCH))) return connection;
    }
    
    return super.getNextTrack(direction);
  }

  /**
   * readMessage() method:
   * @param msg: Message to be read
   * No output
   *              Depending on message type, sends message
   *              to next track
   */
  @Override
  synchronized void readMessage(Message msg)
  {
    //System.out.println("Currently at switch");
    msg.print(getX(), getY());
    switch (msg.messageType)
    {
      case SEARCH:
        search(msg, true);
        break;
        
      case NOTFOUND:
        search(msg, false);
        break;
        
      case FOUND:
        sendMessageToNextTrack(msg);
//      case FREE:
//        freeTrack(msg);
//        break;

      default:
        super.readMessage(msg);
        break;
    }
  }
  
  synchronized void search(Message msg, boolean switchOn)
  {
    switchDirection(switchOn);
    sendMessageToNextTrack(msg);
    
  }

  /**
   * sendMessageToNextTrack() method:
   * @param msg: Message to be sent
   *
   *           If searching in forward direction, splits
   *           message and sends on both sides of switch track
   *           If searching in backward direction, only sends
   *           message in direction according to switchOn field
   */
  @Override
  synchronized void sendMessageToNextTrack(Message msg)
  {
    connection.findCorrespondence(msg).print();
    super.getNextTrack(msg.msgDir).receiveMessage(msg);
    
    if(connection.direction == msg.msgDir && (msg.messageType == MessageType.SEARCH ||
        (msg.messageType == MessageType.FOUND && connection.findCorrespondence(msg).contains(MessageType.SEARCH))||
        (msg.messageType == MessageType.SECURE && connection.findCorrespondence(msg).contains(MessageType.FOUND)) ||
        (msg.messageType == MessageType.SECURED && connection.findCorrespondence(msg).contains(MessageType.SECURE))))
    {
      connection.receiveMessage(msg);
    }
  }
  
  @Override
  synchronized void secureTrack(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    if(c.contains(MessageType.FOUND))
    {
      System.out.println("Track " + getX() + ", " + getY() + " is locked");
      locked = true;
      c = connection.findCorrespondence(msg);
      if(c.contains(MessageType.SEARCH) && c.contains(MessageType.FOUND))
      {
        switchDirection(true);
      }
    }
  }
  
  /**
   * setConnection() method:
   * @param connection: SwitchTrack to be set as connection
   * No output
   *                  Sets this instances connection SwitchTrack to connection
   */
  void setConnection(SwitchTrack connection)
  {
    this.connection = connection;
  }
  
  /**
   * Changes the direction of the switch.
   */
  synchronized void switchDirection(boolean switchOn)
  {
    changeLight(switchOn);
    this.switchOn = switchOn;
  }
  
  synchronized void changeLight(boolean lightOn)
  {
    Message msg0;
    if(lightOn)
    {
      msg0 = new Message(null, MessageType.LIGHTON, null, direction.getOpposite(), -1);
    }
    
    else
    {
      msg0 = new Message(null, MessageType.LIGHTOFF, null, direction.getOpposite(), -1);
    }
    
    super.getNextTrack(msg0.msgDir).receiveMessage(msg0);
  }
  
  /**
   * Returns whether or not the switch is on.
   * @return boolean true if switch is on
   */
  boolean isSwitchOn()
  {
    return switchOn;
  }
}
