import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class TrainView extends ImageView
{
//  AnimationTimer timer = new AnimationTimer()
//  {
//    @Override
//    public void handle(long l)
//    {
//      move((getLayoutX()+5), getLayoutY());
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
