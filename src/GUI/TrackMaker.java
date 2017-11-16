package GUI;

import Train_and_Track.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrackMaker
{
  //@ = Station
  //- = Regular Track
  //* = Light
  //( = Right switch up
  //) = Left switch up
  //[ = Right switch down
  //] = Left switch down
  //z = Z switch
  //s = S switch
//  private static final char [][] CHAR_MAP =  {{'@', '*', ']', '-', '-', '[', '*', '&'},
//                                              {'@', '*', '(', ']', '[', ')', '*', '&'},
//                                              {'@', '*', '[', '(', ')', ']', '*', '&'},
//                                              {'@', '*', ')', '*', '*', '(', '*', '&'}};
  
  private static final BufferedReader trackMapReader = new BufferedReader(res.ResourceLoader.getTrackMap());
  private static final char [][] CHAR_MAP = makeCharMap();
  private static final int FONT_SIZE = 27;
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
  private final Image BLUE_TRAIN = res.ResourceLoader.getTrainImage("Train_Blue.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image GREEN_TRAIN = res.ResourceLoader.getTrainImage("Train_Green.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image PURPLE_TRAIN = res.ResourceLoader.getTrainImage("Train_Purple.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image RED_TRAIN = res.ResourceLoader.getTrainImage("Train_Red.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private final Image YELLOW_TRAIN = res.ResourceLoader.getTrainImage("Train_Yellow.png", IMAGE_WIDTH, IMAGE_HEIGHT);
  private ArrayList<Line> lines = new ArrayList<>();
  private ArrayList<TrainView> trainViews = new ArrayList<>();
  private ArrayList<LightTrackView> lightViews = new ArrayList<>();
  
  TrackMaker(int num_trains, GraphicsContext gc)
  {
    makeTrack(gc);
    makeTrainViews(num_trains);
  }

  ArrayList<Line> getLines()
  {
    return lines;
  }

  ArrayList<TrainView> getTrainViews()
  {
    return trainViews;
  }
  
  ArrayList<LightTrackView> getLightViews()
  {
    return lightViews;
  }
  
  private void makeTrack(GraphicsContext gc)
  {
    Track[][] trackMap = new Track[CHAR_MAP.length][CHAR_MAP[0].length];
    StationTrack startPoint = null;
    StationTrack endPoint = null;
    Track track = null;
    Track prev = null;
    LightTrackView light;
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
            track = new LightTrack(x, y);
            light = new LightTrackView(GREEN_LIGHT_RAIL, RED_LIGHT_RAIL);
            ((LightTrack) track).setLightListener(light);
            light.setLayoutX(x);
            light.setLayoutY(y);
            lightViews.add(light);
            break;
  
          case 'z':
            gc.drawImage(Z_SWITCH, x, y);
            break;
  
          case '(':
            gc.drawImage(RIGHT_SWITCH_UP, x, y);
            connection = (SwitchTrack)trackMap[i-1][j];
            track = new SwitchTrack(TrackType.RIGHT_UP_SWITCH, x, y);
            ((SwitchTrack) track).setConnection(connection);
            connection.setConnection((SwitchTrack) track);
            break;
  
          case ')':
            gc.drawImage(LEFT_SWITCH_UP, j * IMAGE_WIDTH, i * IMAGE_HEIGHT);
            connection = (SwitchTrack) trackMap[i-1][j];
            track = new SwitchTrack(TrackType.LEFT_UP_SWITCH, x, y);
            ((SwitchTrack) track).setConnection(connection);
            connection.setConnection((SwitchTrack) track);
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
            System.exit(3);
            break;
        }
        
        new Thread(track).start();
        trackMap[i][j] = track;
        track.setLeft(prev);
        
        if(prev != null) prev.setRight(track);
        
        prev = track;
        
      }
      lines.add(new Line(startPoint, endPoint));
      prev = null;
    }
  }
  
  private void makeTrainViews(int num_trains)
  {
    double random;
    Image trainImage;
    TrainView trainView;
    for(int i = 0; i < num_trains; i++)
    {
      random = SmartRailsWindow.rand.nextInt((5-1)+1) + 1;
  
      switch((int)random)
      {
        case 1:
          trainImage = BLUE_TRAIN;
          break;
    
        case 2:
          trainImage = GREEN_TRAIN;
          break;
    
        case 3:
          trainImage = PURPLE_TRAIN;
          break;
    
        case 4:
          trainImage = RED_TRAIN;
          break;
    
        case 5:
          trainImage = YELLOW_TRAIN;
          break;
    
        default:
          trainImage = BLUE_TRAIN;
          break;
      }
      trainView = new TrainView(trainImage);
      trainViews.add(trainView);
    }
  }
  
  private static char [][] makeCharMap()
  {
    ArrayList<char[]> lines = new ArrayList<>();
    String line;
    try
    {
      while ((line = trackMapReader.readLine()) != null)
      {
       lines.add(line.toCharArray());
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    
    char[][] charMap = new char[lines.size()][lines.get(0).length];
    
    for(int i = 0; i < lines.size(); i++)
    {
      charMap[i] = lines.get(i);
    }
    
    checkCharMap(charMap);
    
    return charMap;
  }
  
  private static void checkCharMap(char[][] charMap)
  {
    char c;
    for (int i = 0; i < charMap.length; i++)
    {
      for (int j = 0; j < charMap[i].length; j++)
      {
        c = charMap[i][j];
        if(c == ')' || c == '(')
        {
          if(i == 0) System.exit(1);
          else if(charMap[i-1][j] != ']' && charMap[i-1][j] != '[') System.exit(1);
        }
  
        if(c == '[' || c == ']')
        {
          if(i == charMap.length) System.exit(2);
          else if(charMap[i+1][j] != '(' && charMap[i+1][j] != ')') System.exit(2);
        }
      }
    }
  }
}
