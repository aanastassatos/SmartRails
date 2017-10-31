import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrainView extends ImageView
{
//  AnimationTimer timer = new AnimationTimer()
//  {
//    @Override
//    public void handle(long l)
//    {
//      move((getLayoutX()+1.5), getLayoutY());
//    }
//  };
  
  public TrainView(String fileName)
  {
    super(res.ResourceLoader.getTrainImage(fileName, TrackMaker.IMAGE_WIDTH, TrackMaker.IMAGE_HEIGHT));
//    timer.start();
  }
  
  public void move(double x, double y)
  {
    relocate(x, y);
  }
}
