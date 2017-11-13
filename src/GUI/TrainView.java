/**
 * TrainView class holds train image and moves across interface
 */
package GUI;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
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
   * @param train: Train image to be viewed
   * No output
   *                Reads file name from resource loader class in res package
   *
   */
  TrainView(Image train)
  {
    super(train);
//    timer.start();
  }

  /**
   * move() method
   * @param x: x coordinate to move train to
   * @param y: y coordinate to move train to
   * No output
   *                Moves train to x, y position
   */
  public void move(double x, double y)
  {
    relocate(x, y);
  }
}
