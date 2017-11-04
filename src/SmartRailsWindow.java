import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class SmartRailsWindow extends Application
{
  static final int WINDOW_WIDTH = 1400;
  static final int WINDOW_HEIGHT = 700;
  static Random rand = new Random();

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
    //String musicFile = SmartRailsWindow.class.getResource("ThomasTheTank.mp3").toString();
    String musicFile = new File("src/res/ThomasTheTank.mp3").toURI().toString();
    Media sound = new Media(musicFile);
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
    Group root = new Group();
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TrackMaker track = new TrackMaker(NUM_TRAINS, canvas.getGraphicsContext2D());
    
    SmartRails smartRails = new SmartRails(track.getLines());
    
    Button button = new Button("I Am A Button");
    button.setOnAction(e->
    {
      button.setVisible(false);
      new Thread(smartRails).start();
    });
    
    root.getChildren().addAll(canvas, button);
    
    for(TrainView trainView  : track.getTrainViews())
    {
      root.getChildren().add(trainView);
    }
    
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setOnCloseRequest(e -> System.exit(0));
    stage.show();
  }
}
