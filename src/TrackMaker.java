import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class TrackMaker
{
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
  private static final char [][] CHAR_MAP =  {{'@', '-', ']', '-', '-', '-', '-', '&'},
                                              {'@', '-', '(', '-', ']', '-', '-', '&'},
                                              {'@', '-', '-', '-', '(', ']', '-', '&'},
                                              {'@', '-', '-', '-', '-', '(', '-', '&'}};
  
  public static final double IMAGE_WIDTH = SmartRailsWindow.WINDOW_WIDTH/CHAR_MAP[1].length;
  public static final double IMAGE_HEIGHT = SmartRailsWindow.WINDOW_HEIGHT/CHAR_MAP.length;
  private final Image STRAIGHT_RAIL = res.ResourceLoader.getTrackImage("straightrail.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RED_LIGHT_RAIL = res.ResourceLoader.getTrackImage("redlightrail.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image GREEN_LIGHT_RAIL = res.ResourceLoader.getTrackImage("greenlightrail.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image L_STATION = res.ResourceLoader.getTrackImage("station.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image R_STATION = res.ResourceLoader.getTrackImage("station.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image Z_SWITCH = res.ResourceLoader.getTrackImage("switchrailuprightdownleft.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RIGHT_SWITCH_UP = res.ResourceLoader.getTrackImage("rightswitchup.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image LEFT_SWITCH_UP = res.ResourceLoader.getTrackImage("leftswitchup.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RIGHT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("rightswitchdown.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image LEFT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("leftswitchdown.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private ArrayList<Line> lines = new ArrayList<>();
  
  public void makeTrack(GraphicsContext gc)
  {
    Track [][] trackMap = new Track[CHAR_MAP.length][CHAR_MAP[0].length];
    StationTrack startPoint = null;
    StationTrack endPoint = null;
    Track track = null;
    Track prev = null;
    SwitchTrack connection;
    int stations = 0;
    double x;
    double y;
    
    char c;
    
    for(int i = 0; i < CHAR_MAP.length; i++)
    {
      for(int j = 0; j < CHAR_MAP[i].length; j++)
      {
        c = CHAR_MAP[i][j];
        x = j * IMAGE_WIDTH;
        y = i * IMAGE_HEIGHT;
        switch (c)
        {
          case '@':
            gc.drawImage(L_STATION, x, y);
            startPoint = new StationTrack(Character.toString((char)('A'+ stations)), x, y);
            track = startPoint;
            stations += 1;
            break;
  
          case '&':
            gc.drawImage(R_STATION, x, y);
            endPoint = new StationTrack(Character.toString((char)('A'+ stations)), x, y);
            track = endPoint;
            stations += 1;
            break;
  
          case '-':
            gc.drawImage(STRAIGHT_RAIL, x, y);
            track = new Track(TrackType.STRAIGHT, x, y);
            break;
  
          case '*':
            gc.drawImage(GREEN_LIGHT_RAIL, x, y);
            track = new LightTrack(x, y);
            break;
  
          case '%':
            gc.drawImage(RED_LIGHT_RAIL, x, y);
            track = new LightTrack(x, y);
            break;
  
          case 'z':
            gc.drawImage(Z_SWITCH, x, y);
            break;
  
          case '(':
            gc.drawImage(RIGHT_SWITCH_UP, x, y);
            connection = (SwitchTrack) trackMap[i-1][j];
            track = new SwitchTrack(TrackType.RIGHT_UP_SWITCH, x, y);
            ((SwitchTrack) track).setConnection(connection);
            connection.setConnection(track);
            break;
  
          case ')':
            gc.drawImage(LEFT_SWITCH_UP, j * IMAGE_WIDTH, i * IMAGE_HEIGHT);
            connection = (SwitchTrack) trackMap[i-1][j];
            track = new SwitchTrack(TrackType.LEFT_UP_SWITCH, x, y);
            ((SwitchTrack) track).setConnection(connection);
            connection.setConnection(track);
            break;
  
          case '[':
            gc.drawImage(RIGHT_SWITCH_DOWN, x, y);
            track = new SwitchTrack(TrackType.RIGHT_DOWN_SWITCH, x, y);
            break;
  
          case ']':
            gc.drawImage(LEFT_SWITCH_DOWN, x, y);
            track = new SwitchTrack(TrackType.LEFT_DOWN_SWITCH, x, y);
            break;
  
          default:
            break;
        }
        
        trackMap[i][j] = track;
        track.setLeft(prev);
        
        if(prev != null) prev.setRight(track);
        
        prev = track;
        
      }
      lines.add(new Line(startPoint, endPoint));
      prev = null;
    }
  }
  
  public ArrayList<Line> getLines()
  {
    return lines;
  }
}
