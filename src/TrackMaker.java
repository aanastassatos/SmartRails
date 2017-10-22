import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrackMaker
{
  private final double IMAGE_SIZE = 200;
  private final Image STRAIGHT_RAIL = res.ResourceLoader.getTrackImage("straightrail.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image RED_LIGHT_RAIL = res.ResourceLoader.getTrackImage("redlightrail.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image GREEN_LIGHT_RAIL = res.ResourceLoader.getTrackImage("greenlightrail.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image STATION = res.ResourceLoader.getTrackImage("station.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image Z_SWITCH = res.ResourceLoader.getTrackImage("switchrailuprightdownleft.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image RIGHT_SWITCH_UP = res.ResourceLoader.getTrackImage("rightswitchup.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image LEFT_SWITCH_UP = res.ResourceLoader.getTrackImage("leftswitchup.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image RIGHT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("rightswitchdown.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image LEFT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("leftswitchdown.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  
  //@ = Station
  //- = Regular Track
  //* = Green light
  //% = Red light
  //( = Right switch up
  //) = Left switch up
  //[ = Right switch down
  //] = Left switch down
  //z = Z switch
  //s = S switch
  char [][] trackMap = {{'@', '*', ']', '-', '-', '-', '@'},
                        {'@', '-', '(', '-', ']', '-', '@'},
                        {'@', '-', '-', '[', '(', '-', '@'},
                        {'@', '-', '-', ')', '-', '-', '@'}};
  
  public void makeTrack(GraphicsContext gc)
  {
    for(int i = 0; i < trackMap.length; i++)
    {
      for (int j = 0; j < trackMap[i].length; j++)
      {
        char c = trackMap[i][j];
        
        switch (c)
        {
          case '@':
            gc.drawImage(STATION, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
          
          case '-':
            gc.drawImage(STRAIGHT_RAIL, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
  
          case '*':
            gc.drawImage(GREEN_LIGHT_RAIL, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
  
          case '%':
            gc.drawImage(RED_LIGHT_RAIL, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
            
          case 'z':
            gc.drawImage(Z_SWITCH, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
  
          case '(':
            gc.drawImage(RIGHT_SWITCH_UP, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
  
          case ')':
            gc.drawImage(LEFT_SWITCH_UP, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
  
          case '[':
            gc.drawImage(RIGHT_SWITCH_DOWN, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
  
          case ']':
            gc.drawImage(LEFT_SWITCH_DOWN, j*IMAGE_SIZE, i*IMAGE_SIZE);
            break;
            
          default:
            break;
        }
      }
    }
  }
}
