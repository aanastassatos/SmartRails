import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.Iterator;

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
  private static final char [][] CHAR_MAP =  {{'@', '-', ']', '-', '-', '*', '-', '&'},
                                              {'@', '-', '(', '-', ']', '-', '-', '&'},
                                              {'@', '-', '*', '-', '(', ']', '-', '&'},
                                              {'@', '%', '-', '-', '-', '(', '-', '&'}};

  public static final int FONT_SIZE = 27;
  public static final double IMAGE_WIDTH = SmartRailsWindow.WINDOW_WIDTH/CHAR_MAP[1].length;
  public static final double IMAGE_HEIGHT = SmartRailsWindow.WINDOW_HEIGHT/CHAR_MAP.length;
  private final Image STRAIGHT_RAIL = res.ResourceLoader.getTrackImage("straightrail.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RED_LIGHT_RAIL = res.ResourceLoader.getTrackImage("redlightrail.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image GREEN_LIGHT_RAIL = res.ResourceLoader.getTrackImage("greenlightrail.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image L_STATION = res.ResourceLoader.getTrackImage("Station_Left.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image R_STATION = res.ResourceLoader.getTrackImage("Station_Right.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image Z_SWITCH = res.ResourceLoader.getTrackImage("switchrailuprightdownleft.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RIGHT_SWITCH_UP = res.ResourceLoader.getTrackImage("rightswitchup.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image LEFT_SWITCH_UP = res.ResourceLoader.getTrackImage("leftswitchup.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RIGHT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("rightswitchdown.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image LEFT_SWITCH_DOWN = res.ResourceLoader.getTrackImage("leftswitchdown.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private ArrayList<Line> lines = new ArrayList<>();
  private ArrayList<TrainView> trainViews = new ArrayList<>();
  
  public TrackMaker(int num_trains, GraphicsContext gc)
  {
    makeTrack(gc);
    makeTrains(num_trains);
  }
  
  private void makeTrack(GraphicsContext gc)
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
        gc.setFont(Font.font(FONT_SIZE));

        switch (c)
        {
          case '@':
            gc.drawImage(L_STATION, x, y);
            startPoint = new StationTrack("A" + stations, x, y);
            gc.fillText("A" + stations, x + IMAGE_WIDTH/2 - IMAGE_WIDTH/11, y + IMAGE_HEIGHT/6);
            track = startPoint;
            break;
  
          case '&':
            gc.drawImage(R_STATION, x, y);
            gc.fillText("B" + stations, x + IMAGE_WIDTH/2 - IMAGE_WIDTH/11, y + IMAGE_HEIGHT/6);
            endPoint = new StationTrack("B"+ stations, x, y);
            track = endPoint;
            stations += 1;
            break;
  
          case '-':
            gc.drawImage(STRAIGHT_RAIL, x, y);
            track = new StraightTrack(TrackType.STRAIGHT, x, y);
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
        
        //new Thread(track).start();
        trackMap[i][j] = track;
        track.setLeft(prev);
        
        if(prev != null) prev.setRight(track);
        
        prev = track;
        
      }
      lines.add(new Line(startPoint, endPoint));
      prev = null;
    }
  }
  
  private void makeTrains(int num_trains)
  {
    ArrayList<Train> trains = new ArrayList<>();
    for(int i = 0; i < num_trains; i++)
    {
      Train train = new Train(String.valueOf(i+1));
      trains.add(train);
      trainViews.add(train.getTrainView());
    }
    
    int i = 0;
    
    while(i < trains.size())
    {
      for(Line line : lines)
      {
        if(i < trains.size())
        {
          line.getStartPoint().addTrain(trains.get(i));
        }
        
        i += 1;
        
        if(i < trains.size())
        {
          line.getEndPoint().addTrain(trains.get(i));
        }
        
        i += 1;
      }
    }
  }
  
  public ArrayList<Line> getLines()
  {
    return lines;
  }
  
  public ArrayList<TrainView> getTrainViews()
  {
    return trainViews;
  }
  
  public int getLengthofLine()
  {
    return CHAR_MAP[0].length;
  }

  public double getImageWidth() { return IMAGE_WIDTH; }

  public double getImageHeight() { return IMAGE_HEIGHT; }
}
