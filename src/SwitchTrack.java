/**
 * SwitchTrack class extends Track
 * Is used for all switch tracks
 *
 *
 */

public class SwitchTrack extends Track
{
  private boolean switchOn; //If on, switch track will move train to switched line
  SwitchTrack connection; //SwitchTrack connected to current switch track

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
  public Track getNextTrack(Direction direction)
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
  public synchronized void readMessage(Message msg)
  {
    //System.out.println("Currently at switch");
    msg.print(getX(), getY());
    switch (msg.messageType)
    {
      case MOVE:
        moveTrain();
        break;

      case SECURE:
        if(getNextTrack(msg.msgDir).message == MessageType.FOUND)
        {
          secureTrack(msg);
          sendMessageToNextTrack(msg);
        }
        else
        {
          switchOn = !switchOn;
          if(getNextTrack(msg.msgDir).message == MessageType.FOUND)
          {
            secureTrack(msg);
            sendMessageToNextTrack(msg);
          }
          else
          {
            switchOn = ! switchOn;
          }
        }
        break;

      case FREE:
        if(msg.forward) freeTrack(msg);
        break;

      default:
        sendMessageToNextTrack(msg);
        break;
    }
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
  public synchronized void sendMessageToNextTrack(Message msg)
  {
    if(msg.forward)
    {
      Boolean switchValue = switchOn;

      switchOn = false;
      getNextTrack(msg.msgDir).receiveMessage(msg);

      switchOn = true;
      getNextTrack(msg.msgDir).receiveMessage(msg);

      switchOn = switchValue;
    }
    else
    {
      switchOn = false;
      MessageType nextType = getNextTrack(msg.msgDir).message;
      if(!(nextType == MessageType.SEARCH || nextType == MessageType.FOUND))
      {
        switchOn = true;
      }
      getNextTrack(msg.msgDir).receiveMessage(msg);
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
  public void switchDirection(boolean switchOn)
  {
    this.switchOn = switchOn;
  }
  
  /**
   * Returns whether or not the switch is on.
   * @return boolean true if switch is on
   */
  public boolean isSwitchOn()
  {
    return switchOn;
  }
}
