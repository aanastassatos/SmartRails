public class Train
{
  private Track currentTrack;
  private  Direction direction;
  private String name;
  private TrainView trainView;
  
  public Train(String name)
  {
    this.name = name;
  }
  
  /**
   * Takes the destination name, and goes through the track, securing the route to the destination by flipping track
   * switches, and turning stop lights red.
   * @param destination
   */
  public void secureRoute(String destination)
  {
    //TODO
    // SPECIFICATIONS:
    // This method should be called by StationTrack at the beginning of a trip and by LightTrack after a route is freed by
    // another train.
    //
    // Should move through the tracks starting at "currentTrack," finding until it finds the station corresponding to
    // "destination."  As it is doing this, it should check to see if there are any red light tracks in its path, as this
    // would mean the path is already secured by another train.  If the route is free, flip the appropriate track switches,
    // and change the appropriate lights in order to get the train to its destination.  If the route is secured by another
    // train, secure the route up to the first red light rail so the train can move to that light track where it should
    // wait for track to be freed.
  }
  
  /**
   * Frees the route behind where a train has already moved.
   */
  public void freeRoute()
  {
    //TODO
    //SPECIFICATIONS:
    //This method should be called every time the train passes a switch.
    //
    //Should go through and reset switch and lights corresponding to that switch.
  }
  
  public void setDirection(Direction direction)
  {
    this.direction = direction;
  }
  
  public Direction getDirection()
  {
    return direction;
  }
  
  public void setCurrentTrack(Track currentTrack)
  {
    this.currentTrack = currentTrack;
    relocate(currentTrack.getX(), currentTrack.getY());
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setTrainView(TrainView trainView)
  {
    this.trainView = trainView;
  }
  
  public void relocate(double x, double y)
  {
    if(currentTrack instanceof StationTrack)
    {
      trainView.setVisible(false);
    }
    else
    {
      trainView.setVisible(true);
    }
    
    trainView.move(x, y);
  }
}
