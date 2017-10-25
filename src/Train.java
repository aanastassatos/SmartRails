
public class Train extends Component
{
  private Track currentTrack;

  public void moveTrain()
  {
    Track nextTrack = currentTrack.getNextTrack();
    if(nextTrack.isOccupied()) System.out.println("CRASH");
    else
    {
      currentTrack.setOccupied(false);
      currentTrack = currentTrack.getNextTrack();
      currentTrack.setOccupied(true);
    }
  }
}
