
public class SwitchTrack extends Track
{
  private boolean switchOn;
  Track connection;

  SwitchTrack(TrackType trackType, double x, double y)
  {
    super(trackType, x, y);
    switchOn = false;
  }
  
  /**
   * Returns the next track in the given direction. If the switch is on and the direction corresponds with the switch,
   * the connection is returned.
   * @param direction
   * @return
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

  public void setSwitchOn(boolean switchOn)
  {
    this.switchOn = switchOn;
  }

  public boolean getSwitchOn() { return switchOn; }
  
  /**
   * Sets the reference to the track that the switch is connected to.
   * @param connection
   */
  public void setConnection(Track connection)
  {
    this.connection = connection;
  }

}
