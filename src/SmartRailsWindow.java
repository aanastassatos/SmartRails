import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SmartRailsWindow extends Application
{
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override
  public void start(Stage stage) throws Exception
  {
    TrackMaker track = new TrackMaker();
    Canvas canvas = new Canvas(1400, 800);
    track.makeTrack(canvas.getGraphicsContext2D());
    Scene scene = new Scene(new Group(canvas));
    stage.setScene(scene);
    stage.show();
  }
}
