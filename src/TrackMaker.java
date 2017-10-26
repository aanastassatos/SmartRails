import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class TrackMaker
{
  private final double IMAGE_SIZE = 200;
  private final Image STRAIGHT_RAIL = res.ResourceLoader.getTrackImage("straightrail.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image RED_LIGHT_RAIL = res.ResourceLoader.getTrackImage("redlightrail.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image GREEN_LIGHT_RAIL = res.ResourceLoader.getTrackImage("greenlightrail.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image L_STATION = res.ResourceLoader.getTrackImage("station.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image R_STATION = res.ResourceLoader.getTrackImage("station.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image Z_SWITCH = res.ResourceLoader.getTrackImage("switchrailuprightdownleft.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image RIGHT_SWITCH_UP = res.ResourceLoader.getTrackImage("rightswitchup.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image LEFT_SWITCH_UP = res.ResourceLoader.getTrackImage("leftswitchup.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image RIGHT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("rightswitchdown.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image LEFT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("leftswitchdown.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private final Image TRAIN = res.ResourceLoader.getTrainImage("train.png", IMAGE_SIZE, IMAGE_SIZE, false, false);
  private ArrayList<Line> lines = new ArrayList<>();
  private Track startPoint;
  private Track endPoint;
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
  char [][] charMap =  {{'@', '-', ']', '-', '-', '-', '-', '&'},
                        {'@', '-', '(', '-', ']', '-', '-', '&'},
                        {'@', '-', '-', '-', '(', ']', '-', '&'},
                        {'@', '-', '-', '-', '-', '(', '-', '&'}};
  
  public void makeTrack(GraphicsContext gc)
  {
    Track [][] trackMap = new Track[charMap.length][charMap[0].length];
    StationTrack startPoint = null;
    StationTrack endPoint = null;
    Track track = null;
    Track prev = null;
    SwitchTrack connection;
    
    char c;
    
    for(int i = 0; i < charMap.length; i++)
    {
      for(int j = 0; j < charMap[i].length; j++)
      {
        c = charMap[i][j];
        switch (c)
        {
          case '@':
            gc.drawImage(L_STATION, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new StationTrack(String.valueOf(i+1*j));
            System.out.println(String.valueOf(i+1*j));
            startPoint = (StationTrack) track;
            break;
  
          case '&':
            gc.drawImage(R_STATION, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new StationTrack(String.valueOf(i+1*j));
            System.out.println(String.valueOf(i+1*j));
            endPoint = (StationTrack) track;
            break;
  
          case '-':
            gc.drawImage(STRAIGHT_RAIL, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Track(TrackType.STRAIGHT);
            break;
  
          case '*':
            gc.drawImage(GREEN_LIGHT_RAIL, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new LightTrack();
            break;
  
          case '%':
            gc.drawImage(RED_LIGHT_RAIL, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new LightTrack();
            break;
  
          case 'z':
            gc.drawImage(Z_SWITCH, j * IMAGE_SIZE, i * IMAGE_SIZE);
            break;
  
          case '(':
            gc.drawImage(RIGHT_SWITCH_UP, j * IMAGE_SIZE, i * IMAGE_SIZE);
            connection = (SwitchTrack) trackMap[i-1][j];
            track = new SwitchTrack(TrackType.RIGHT_UP_SWITCH);
            ((SwitchTrack) track).setConnection(connection);
            connection.setConnection((SwitchTrack) track);
            break;
  
          case ')':
            gc.drawImage(LEFT_SWITCH_UP, j * IMAGE_SIZE, i * IMAGE_SIZE);
            connection = (SwitchTrack) trackMap[i-1][j];
            track = new SwitchTrack(TrackType.LEFT_UP_SWITCH);
            ((SwitchTrack) track).setConnection(connection);
            connection.setConnection((SwitchTrack) track);
            break;
  
          case '[':
            gc.drawImage(RIGHT_SWITCH_DOWN, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new SwitchTrack(TrackType.RIGHT_DOWN_SWITCH);
            break;
  
          case ']':
            gc.drawImage(LEFT_SWITCH_DOWN, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new SwitchTrack(TrackType.LEFT_DOWN_SWITCH);
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
