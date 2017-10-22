import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrackMaker
{
  private final double IMAGE_SIZE = 200;
  private final Image STRAIGHT_RAIL = res.ResourceLoader.getTrackImage("straightrail.png", 200, 200, false, false);
  private final Image RED_LIGHT_RAIL = res.ResourceLoader.getTrackImage("redlightrail.png", 200, 200, false, false);
  private final Image GREEN_LIGHT_RAIL = res.ResourceLoader.getTrackImage("greenlightrail.png", 200, 200, false, false);
  private final Image STATION = res.ResourceLoader.getTrackImage("station.png", 200, 200, false, false);
  char [][] trackMap = {{'-', '@', '%', '%', '%', '@', '*', '-'},
                        {'@', '-', '-', '-', '-', '-', '-', '@'},
                        {'@', '-', '*', '%', '*', '%', '-', '@'},
                        {'@', '*', '-', '%', '-', '*', '-', '@'}};
  
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
            
          default:
            break;
        }
      }
    }
  }
}
