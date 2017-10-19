public class Train
{
  private Track currentTrack;
  private  Direction direction;
  
  public void moveTrain()
  {
    Track nextTrack = currentTrack.getNextTrack(direction);
    if(nextTrack.isOccupied()) System.out.println("CRASH");
    else
    {
      currentTrack.setOccupied(false);
      currentTrack = currentTrack.getNextTrack(direction);
      currentTrack.setOccupied(true);
    }
  }
  
}
