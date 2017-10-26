public class Train extends Component
{
  private Track currentTrack;
  private  Direction direction;
  private String name;
  
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
}
