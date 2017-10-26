public class Line extends Thread
{
  private StationTrack startPoint;
  private StationTrack endPoint;
  
  public Line(StationTrack startPoint, StationTrack endPoint)
  {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
  }
  
  public StationTrack getStartPoint()
  {
    return startPoint;
  }
  
  public StationTrack getEndPoint()
  {
    return endPoint;
  }
}
