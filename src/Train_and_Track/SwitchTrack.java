package Train_and_Track;

import SmartRails.Direction;
import Train_and_Track.Message.*;

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
  private Direction direction;  //The direction that the switch goes.
  
  /**
   * SwitchTrack() Constructor:
   * @param trackType: Track type to be set in Track class
   * @param x: x coordinate for track image
   * @param y: y coordinate for track image
   */
  public SwitchTrack(TrackType trackType, double x, double y)
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
   * setConnection() method:
   * @param connection: SwitchTrack to be set as connection
   * No output
   *                  Sets this instances connection SwitchTrack to connection
   */
  public void setConnection(SwitchTrack connection)
  {
    this.connection = connection;
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
  Track getNextTrack(Direction direction)
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
    msg.print(getX(), getY());
    
    if((msg.msgDir == direction.getOpposite() && !switchOn) || (msg.msgDir == direction && msg.messageType != MessageType.NOTFOUND)) super.readMessage(msg);
    
    else
    {
      switch (msg.messageType)
      {
        case NOTFOUND:
          if(!switchOn)
          {
            turnSwitchOn();
            addToOutGoing(new Message(msg.sender, MessageType.SEARCH, msg.recipient, msg.msgDir.getOpposite(), msg.correspondenceID));
          }
          
          else
          {
            if(getNextTrack(msg.msgDir) != connection)
            {
              turnSwitchOff();
            }
            addToOutGoing(msg);
          }
          break;
        
        case FREED:
          addToOutGoing(msg);
          connection.receiveMessage(msg);
          break;
        
        default:
          super.readMessage(msg);
          break;
      }
    }
  }
  
  /**
   * Secures track
   * @param msg: message to secure current track
   */
  @Override
  synchronized void secureTrack(Message msg)
  {
    Correspondence c = findCorrespondence(msg);
    if(c.contains(MessageType.FOUND))
    {
      System.out.println("Track " + getX() + ", " + getY() + " is locked");
      setSecure(true);
    }
  }
  
  /**
   * Frees the track.
   * @param msg
   */
  @Override
  synchronized void freeTrack(Message msg)
  {
    if(msg.msgDir == direction || !switchOn) super.freeTrack(msg);
    
    else
    {
      if (connection.containsCorrespondence(msg))
      {
        connection.receiveMessage(msg);
      }
      
      if (containsCorrespondence(msg))
      {
        clearCorrespondence(msg);
      }
      
      turnSwitchOff();
      
      setLocked(false);
      setSecure(false);
    }
  }
  
  /**
   * Changes the direction of the switch to on.
   */
  private void turnSwitchOn()
  {
    switchDirection(true);
    connection.switchDirection(true);
  }
  
  /**
   * Changes the direction of the switch to off.
   */
  private void turnSwitchOff()
  {
    switchDirection(false);
    connection.switchDirection(false);
  }
  
  /**
   * Changes the direction of the switch to the given boolean
   * @param switchOn
   */
  void switchDirection(boolean switchOn)
  {
    changeLight(switchOn);
    this.switchOn = switchOn;
  }
  
  /**
   * Changes the lights in the appropriate direction to the given boolean
   * @param lightOn
   */
  private void changeLight(boolean lightOn)
  {
    Track track = super.getNextTrack(direction.getOpposite());
    while(!(track instanceof StationTrack))
    {
      if(track instanceof LightTrack) ((LightTrack) track).setLightOn(lightOn);
      track = track.getNextTrack(direction.getOpposite());
    }
  }
}