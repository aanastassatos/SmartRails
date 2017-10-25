
public class StationTrack extends Track
{
  //Sets direction of track
  Track right;
  Track left;

  StationTrack()
  {
    setTrackType(TrackType.STATION);
  }

  void initDirection()
  {
    if(right == null) setDirection(Direction.LEFT);
    else if(left == null) setDirection(Direction.RIGHT);
  }
}
