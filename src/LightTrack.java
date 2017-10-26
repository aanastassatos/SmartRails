
public class LightTrack extends Track
{
  boolean lightOn;
  
  public LightTrack(double x, double y)
  {
    super(TrackType.LIGHT, x, y);
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
