
public class LightTrack extends Track
{
  boolean lightOn;
  
  public LightTrack()
  {
    super(TrackType.LIGHT);
    lightOn = false;
  }
  
  void setLightOn(boolean lightOn)
  {
    this.lightOn = lightOn;
  }

  boolean returnLightOn()
  {
    return lightOn;
  }
}
