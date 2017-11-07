/**
 * Line class creates line objects for each rail line, with a
 * start and end point
 */

class Line
{
  private StationTrack startPoint;
  private StationTrack endPoint;

  /**
   * Line constructor
   * @param startPoint: Station line begins
   * @param endPoint: Station line ends
   */
  Line(StationTrack startPoint, StationTrack endPoint)
  {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
  }

  /**
   * @return Station line begins with
   */
  StationTrack getStartPoint()
  {
    return startPoint;
  }

  /**
   * @return Station line ends with
   */
  StationTrack getEndPoint()
  {
    return endPoint;
  }
}
