import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrainView extends ImageView
{
  public TrainView(String fileName)
  {
    super(res.ResourceLoader.getTrainImage(fileName + ".png", TrackMaker.IMAGE_WIDTH, TrackMaker.IMAGE_HEIGHT));
  }
  
  public void move(double x, double y)
  {
    relocate(x, y);
  }
}
