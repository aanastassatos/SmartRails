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
          (trackType == TrackType.RIGHT_DOWN_SWITCH || trackType == TrackType.RIGHT_UP_SWITCH))) return connection.getNextTrack(direction);
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
//    if(locked) super.readMessage(msg);
//    else
//    {
      switch (msg.messageType)
      {
        case SEARCH:
          switchDirection(connection.findCorrespondence(msg).getSwitchValue());
          search(new Message(msg.sender, MessageType.SEARCH, msg.recipient, msg.msgDir, msg.correspondecnceID), switchOn);
          break;
    
        case NOTFOUND:
          switchDirection(true);
          search(new Message(msg.sender, MessageType.SEARCH, msg.recipient, msg.msgDir.getOpposite(), msg.correspondecnceID), switchOn);
          break;
    
        case FOUND:
          saveSwitchValues(msg);
          sendMessageToNextTrack(msg);
          break;
          
        case FREE:
          connection.receiveMessage(msg);
          super.getNextTrack(msg.msgDir).receiveMessage(msg);
          break;
    
        default:
          super.readMessage(msg);
          break;
      }
  }
  
  synchronized void search(Message msg, boolean switchOn)
  {
    if(!switchOn || (switchOn && msg.msgDir == direction.getOpposite()))
    {
      findCorrespondence(msg).setSwitchValue(switchOn);
      if(switchOn) turnSwitchOn();
      else if(!switchOn && msg.msgDir == direction.getOpposite()) turnSwitchOff();
      sendMessageToNextTrack(msg);
    }
    
  }

  synchronized void saveSwitchValues(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    if (connection.findCorrespondence(msg).getSwitchValue()) c.setSwitchValue(true);
  }
  
  @Override
  synchronized void secureTrack(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    if(c.contains(MessageType.FOUND))
    {
      if(!locked)
      {
        System.out.println("Track " + getX() + ", " + getY() + " is locked");
        locked = true;
  
        if (findCorrespondence(msg).getSwitchValue()) turnSwitchOn();
        else turnSwitchOff();
      }
      
      else
      {
        receiveMessage(msg);
      }
    }
  }
  
  @Override
  synchronized void freeTrack(Message msg)
  {
    turnSwitchOff();
    clearCorrespondence(msg);
    locked = false;
    super.getNextTrack(msg.msgDir).freeTrack(msg);
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
  synchronized void turnSwitchOn()
  {
    switchDirection(true);
    connection.switchDirection(true);
  }
  
  synchronized void turnSwitchOff()
  {
    switchDirection(false);
    connection.switchDirection(false);
  }
  
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
