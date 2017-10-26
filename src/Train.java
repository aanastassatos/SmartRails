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
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setTrainView(TrainView trainView)
  {
    this.trainView = trainView;
    trainView.setVisible(true);
  }
  
  public void relocate(double x, double y)
  {
    trainView.move(x, y);
  }
}
