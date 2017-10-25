public class Switch extends Track
{
  private Track connection;
  
  public Switch(TrackType trackType, int x, int y)
  {
    super(trackType, x, y);
  }
  
  public void setConnection(Switch connection)
  {
    this.connection = connection;
  }
  
  public Track getConnection()
  {
    return connection;
  }
  
  //  public switchTrack()
//  {
//    TrackType type = getTrackType();
//    if()
//    Track temp;
//    switch (getTrackType())
//    {
//      case LEFT_DOWN_SWITCH:
//      case LEFT_UP_SWITCH:
//        temp = connection;
//        connection = getNextTrack(Direction.RIGHT);
//        setRight(temp);
//    }
//  }
}
