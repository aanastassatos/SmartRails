public class Track extends Component
{
  private TrackType trackType;
  private boolean occupied;
  private Direction direction;
  private Track left;
  private Track right;

  Track()
  {
    //Starts thread through component
  }

  void setTrackType(final TrackType trackType)
  {
    this.trackType = trackType;
  }

  void setDirection(Direction direct)
  {
    //somehow talk to train here...
    this.direction = direct;
  }

  Track getNextTrack()
  {
    if(direction == Direction.RIGHT) return right;
    return left;
  }


  public TrackType getTrackType()
  {
    return trackType;
  }

  boolean isOccupied()
  {
    return occupied;
  }

  void setOccupied(boolean occupied)
  {
    this.occupied = occupied;
  }
}
