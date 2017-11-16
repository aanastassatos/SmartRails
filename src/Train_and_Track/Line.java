package Train_and_Track;

/**
 * Line class creates line objects for each rail line, with a
 * start and end point
 */

public class Line
{
  private StationTrack startPoint; //Start po
  private StationTrack endPoint;

  /**
   * Line constructor
   * @param startPoint: Station line begins
   * @param endPoint: Station line ends
   */
  public Line(StationTrack startPoint, StationTrack endPoint)
  {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
  }
  
  /**
   * Adds given train to the start point of the line.
   * @param train
   */
  public void addTrainToStartPoint(Train train)
  {
    startPoint.addTrain(train);
  }
  
  /**
   * Adds given train to the end point of the line.
   * @param train
   */
  public void addTrainToEndPoint(Train train)
  {
    endPoint.addTrain(train);
  }

  /**
   * @return Station line begins with
   */
  public StationTrack getStartPoint()
  {
    return startPoint;
  }

  /**
   * @return Station line ends with
   */
  public StationTrack getEndPoint()
  {
    return endPoint;
  }
  
  
}
