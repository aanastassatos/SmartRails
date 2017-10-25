
public class SwitchTrack extends Track
{
  boolean switchOn;
  char switchType;

  SwitchTrack()
  {
    setTrackType(TrackType.SWITCH);
  }

  void setSwitchOn(boolean b)
  {
    this.switchOn = b;
  }

  void switchDirection()
  {
    if(switchOn)
    {
      //sets direction from switch
    }
  }
}
