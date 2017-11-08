import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class SmartRailsWindow extends Application
{
  static final int WINDOW_WIDTH = 1400;
  static final int WINDOW_HEIGHT = 700;
  static Random rand = new Random();
  private static ArrayList<Train> trains = new ArrayList<>();

  private static final int NUM_TRAINS = 3;

  /**
   * main method of project SmartRails
   * @param args
   */
  public static void main(String[] args)
  {
    launch(args);
  }

  /**
   * start() method from Application class
   * @param stage
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception
  {
//    String musicFile = new File("src/res/ThomasTheTank.mp3").toURI().toString();
//    Media sound = new Media(musicFile);
//    MediaPlayer mediaPlayer = new MediaPlayer(sound);
//    mediaPlayer.play();
    Group root = new Group();
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TrackMaker track = new TrackMaker(NUM_TRAINS, canvas.getGraphicsContext2D());
    makeTrains(track);
    SmartRails smartRails = new SmartRails(trains);
    
    Button button = new Button("I Am A Button");
    button.setOnAction(e->
    {
      button.setVisible(false);
      new Thread(smartRails).start();
    });
    
    root.getChildren().addAll(canvas, button);
    
    for(LightTrackView lightTrackView : track.getLightViews())
    {
      root.getChildren().add(lightTrackView);
    }
    
    for(TrainView trainView  : track.getTrainViews())
    {
      root.getChildren().add(trainView);
    }
    
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setOnCloseRequest(e -> System.exit(0));
    stage.show();
  }
  
  private void makeTrains(TrackMaker track)
  {
    int i = 1;
    Train train;
    for(TrainView trainView : track.getTrainViews())
    {
      train = new Train(String.valueOf(i));
      train.setTrainView(trainView);
      trains.add(train);
      i++;
    }
    
    i = 0;
    
    while(i < trains.size())
    {
      for(Line line : track.getLines())
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
}
