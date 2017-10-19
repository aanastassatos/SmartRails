public class Track
{
  private TrackType trackType;
  private boolean occupied;
  private Track left;
  private Track right;
  
  public Track(final TrackType trackType, Track left, Track right)
  {
    this.trackType = trackType;
  }
  
  public Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    else return left;
    
  }
  
  public TrackType getTrackType()
  {
    return trackType;
  }
  
  public boolean isOccupied()
  {
    return occupied;
  }
  
  public void setOccupied(boolean occupied)
  {
    this.occupied = occupied;
  }
}
