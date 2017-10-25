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
    Track startPoint = null;
    Track endPoint = null;
    Track track = null;
    Track prev = null;
    Switch connection = null;
    Switch current = null;
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
            track = new Station(String.valueOf(i+1*j), j, i);
            System.out.println(String.valueOf(i+1*j));
            startPoint = track;
            break;
  
          case '&':
            gc.drawImage(R_STATION, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Station(String.valueOf(i+1*j), j, i);
            System.out.println(String.valueOf(i+1*j));
            endPoint = track;
            break;
  
          case '-':
            gc.drawImage(STRAIGHT_RAIL, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Track(TrackType.STRAIGHT, j, i);
            break;
  
          case '*':
            gc.drawImage(GREEN_LIGHT_RAIL, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Light(j, i);
            break;
  
          case '%':
            gc.drawImage(RED_LIGHT_RAIL, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Light(j, i);
            break;
  
          case 'z':
            gc.drawImage(Z_SWITCH, j * IMAGE_SIZE, i * IMAGE_SIZE);
            break;
  
          case '(':
            gc.drawImage(RIGHT_SWITCH_UP, j * IMAGE_SIZE, i * IMAGE_SIZE);
            connection = (Switch) trackMap[i-1][j];
            track = new Switch(TrackType.RIGHT_UP_SWITCH, j, i);
            ((Switch) track).setConnection(connection);
            connection.setConnection((Switch) track);
            break;
  
          case ')':
            gc.drawImage(LEFT_SWITCH_UP, j * IMAGE_SIZE, i * IMAGE_SIZE);
            connection = (Switch) trackMap[i-1][j];
            track = new Switch(TrackType.LEFT_UP_SWITCH, j, i);
            ((Switch) track).setConnection(connection);
            connection.setConnection((Switch) track);
            break;
  
          case '[':
            gc.drawImage(RIGHT_SWITCH_DOWN, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Switch(TrackType.RIGHT_DOWN_SWITCH, j, i);
            break;
  
          case ']':
            gc.drawImage(LEFT_SWITCH_DOWN, j * IMAGE_SIZE, i * IMAGE_SIZE);
            track = new Switch(TrackType.LEFT_DOWN_SWITCH, j, i);
            break;
  
          default:
            break;
        }
        
        trackMap[i][j] = track;
        track.setLeft(prev);
        
        if(prev != null) prev.setRight(track);
        
        prev = track;
        
      }
      lines.add(new Line((Station) startPoint, (Station) endPoint));
      prev = null;
    }
  }
  
  public ArrayList<Line> getLines()
  {
    return lines;
  }
  
  //  private void buildLines()
//  {
//    for(int i = 0; i < trackMap.length; i++)
//    {
//      for(int j = 0; j < trackMap[i].length; j++)
//      {
//        Track track = trackMap[i][j];
//        switch (track.getTrackType())
//        {
//          case STATION:
//            if(j == 0)
//            {
//              track.setLeft(null);
//              track.setRight(trackMap[i][j+1]);
//            }
//
//            else
//            {
//              track.setRight(null);
//              track.setLeft(trackMap[i][j+1]);
//            }
//            break;
//
//          case STRAIGHT:
//            track.setLeft(trackMap[i][j-1]);
//            track.setRight(trackMap[i][j+1]);
//            break;
//        }
//      }
//    }
//  }
}
