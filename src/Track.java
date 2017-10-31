public class Track implements Runnable
{
  private TrackType trackType; //Enum denoting the type of track this is.
  private Train train;  //Train that is currently on the track. Null if none.
  private Track left; //Reference to track piece to the left.
  private Track right;  //Reference to track piece to the left.
  private boolean locked = false;
  private double x;
  private double y;

  public Track(TrackType trackType, double x, double y)
  {
    //Starts thread through component
    this.trackType = trackType;
    this.x = x;
    this.y = y;
  }
  
  /**
   * Takes a direction and returns the track piece in that direction
   * relative to this track piece.
   * @param direction
   * @return
   */
  public Track getNextTrack(Direction direction)
  {
    if(direction == Direction.RIGHT) return right;
    return left;
  }
  
  /**
   * Gets the track piece to the right.
   * @return
   */
  public Track getRight()
  {
    return right;
  }
  
  /**
   * Sets the track piece to the right.
   * @param right
   */
  public void setRight(Track right)
  {
    this.right = right;
  }
  
  /**
   * Gets the track piece to the left.
   * @return
   */
  public Track getLeft()
  {
    return left;
  }

  public void setLocked(boolean locked)
  {
    this.locked = locked;
  }
  
  /**
   * Gets the track piece to the left.
   * @param left
   */
  public void setLeft(Track left)
  {
    this.left = left;
  }
  
  /**
   * Sets the train currently on this track.
   * @param train
   */
  public void setTrain(Train train)
  {
    this.train = train;
  }
  
  public Train getTrain()
  {
    return train;
  }
  
  /**
   * Returns a boolean indicating whether or not there is a train on this track piece.
   * @return
   */
  public boolean isOccupied()
  {
    return (train != null);
  }
  
  /**
   * Returns the type of track piece this is.
   * @return
   */
  public TrackType getTrackType()
  {
    return trackType;
  }
  
  /**
   * Moves the train on this track to the next tack in the direction it is going.
   */
  public void moveTrain()
  {
    Track next;
    if(train != null)
    {
      next = getNextTrack(train.getDirection());
      if(next.isOccupied())
      {
        System.out.println("CRASH");
      }
      train.setCurrentTrack(next);
      next.setTrain(train);
      train = null;
      
      try
      {
        Thread.sleep(500);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      
      next.moveTrain();
    }
  }
  
  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }
  
  @Override
  public void run()
  {
  
  }
}
