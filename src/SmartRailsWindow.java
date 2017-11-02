import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class SmartRailsWindow extends Application
{
  public static final int WINDOW_WIDTH = 1600;
  public static final int WINDOW_HEIGHT = 800;
  public static final int NUM_TRAINS = 1;
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override
  public void start(Stage stage) throws Exception
  {
    //String musicFile = SmartRailsWindow.class.getResource("ThomasTheTank.mp3").toString();
    String musicFile = new File("src/ThomasTheTank.mp3").toURI().toString();
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
