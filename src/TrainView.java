import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrainView extends Canvas
{
  private Image trainImage;
  
  public TrainView(String fileName)
  {
    trainImage = res.ResourceLoader.getTrainImage("train.png", TrackMaker.IMAGE_WIDTH, TrackMaker.IMAGE_HEIGHT);
    setWidth(TrackMaker.IMAGE_WIDTH);
    setHeight(TrackMaker.IMAGE_HEIGHT);
    this.getGraphicsContext2D().drawImage(trainImage,0, 0);
  }
  
  public void move(double x, double y)
  {
    relocate(x, y);
  }
}
