import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class SmartRailsWindow extends Application
{
  public static final int WINDOW_WIDTH = 1600;
  public static final int WINDOW_HEIGHT = 800;
  public static final int NUM_TRAINS = 3;
  public static Random rand = new Random();
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override
  public void start(Stage stage) throws Exception
  {
    Group root = new Group();
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TrackMaker track = new TrackMaker(NUM_TRAINS, canvas.getGraphicsContext2D());
    
//    canvas.setOnMouseClicked(e -> {
//      doAction(e, track, train);
//    });
    
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
  
  private void doAction(MouseEvent event, TrackMaker trackMaker, Train train)
  {
    double IH = trackMaker.getImageHeight();
    double IW = trackMaker.getImageWidth();
    int xval = (int) Math.floor(event.getX() / IH);
    int yval = (int) Math.floor(event.getY() / IW);
    System.out.println("yval = " + yval);
    System.out.println("xval = " + xval);
    if(xval == 0)
    {
      System.out.println("First Station = " + yval);
    }
    else if (xval == trackMaker.getLengthofLine()-1)
    {
      System.out.println("Last Station = " + yval);
    }
  }
}
