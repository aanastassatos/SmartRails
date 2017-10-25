public class Line extends Thread
{
  private Station startPoint;
  private Station endPoint;
  
  public Line(Station startPoint, Station endPoint)
  {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
  }
  
  public void findStation(String stationName)
  {
    startPoint.findStation(Direction.RIGHT, stationName);
  }
}
