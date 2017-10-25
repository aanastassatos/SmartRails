import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Train extends ImageView
{
  private final double IMAGE_SIZE = 200;
  private Track currentTrack;
  private  Direction direction;
  private ImageView trainImage = new ImageView(res.ResourceLoader.getTrainImage("train.png", IMAGE_SIZE, IMAGE_SIZE, false, false));
  private GraphicsContext gc;
  
  public Train(Station currentTrack)
  {
    this.currentTrack = currentTrack;
    this.gc = gc;
    train.setVisible(false);
    gc.drawImage(train, currentTrack.getX()*IMAGE_SIZE, currentTrack.getY()*IMAGE_SIZE);
  }
  
  public void setCurrentTrack(Track currentTrack)
  {
    this.currentTrack = currentTrack;
  }
  
  public void draw()
  {
  
  }
}
