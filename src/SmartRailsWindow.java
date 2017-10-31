import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SmartRailsWindow extends Application
{
  public static final int WINDOW_WIDTH = 1600;
  public static final int WINDOW_HEIGHT = 800;
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override
  public void start(Stage stage) throws Exception
  {
    TrackMaker track = new TrackMaker();
    Train train = new Train("train");
    Pane root = new Pane();
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    track.makeTrack(canvas.getGraphicsContext2D());
    canvas.setOnMouseClicked(e -> {
      doAction(e, track, train);
    });
    track.getLines().get(0).getStartPoint().addTrain(train);
    SmartRails smartRails = new SmartRails(track.getLines());
    train.printDirection();
    Button button = new Button("I Am A Button");
    button.setOnAction(e->
    {
      button.setVisible(false);
      new Thread(smartRails).start();
    });
    root.getChildren().addAll( canvas, train.getTrainView(), button);
    Scene scene = new Scene(root);
    stage.setScene(scene);
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
