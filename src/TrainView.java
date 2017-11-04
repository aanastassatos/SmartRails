/**
 * TrainView class holds train image and moves across interface
 */

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

  /**
   * TrainView constructor
   * @param fileName: Train filename to be viewed
   * No output
   *                Reads file name from resource loader class in res package
   *
   */
  TrainView(String fileName)
  {
    super(res.ResourceLoader.getTrainImage(fileName, TrackMaker.IMAGE_WIDTH, TrackMaker.IMAGE_HEIGHT));
//    timer.start();
  }

  /**
   * move() method
   * @param x: x coordinate to move train to
   * @param y: y coordinate to move train to
   * No output
   *                Moves train to x, y position
   */
  void move(double x, double y)
  {
    relocate(x, y);
  }
}
