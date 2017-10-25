public class Light extends Track
{
  boolean stop;
  public Light(int x, int y)
  {
    super(TrackType.LIGHT, x, y);
    stop = false;
  }
}
