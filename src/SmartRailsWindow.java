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
    Group root = new Group();
    Canvas canvas = new Canvas(1600, 800);
    track.makeTrack(canvas.getGraphicsContext2D());
    SmartRails smartRails = new SmartRails(track.getLines());
    Scene scene = new Scene(new Group(canvas));
    stage.setScene(scene);
    stage.show();
  }
}
