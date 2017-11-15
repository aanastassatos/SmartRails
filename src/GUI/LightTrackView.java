/**
 * LightTrackView Class
 * Extends ImageView
 *
 *
 */

package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LightTrackView extends ImageView
{
  private final Image greenLight;
  private final Image redLight;

  /**
   * getListener method
   * @return ChangeListener listener
   */
  public ChangeListener getListener()
  {
    return listener;
  }

  /**
   *
   */
  private ChangeListener listener = new ChangeListener()
  {
    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1)
    {
      if((boolean) t1) setImage(redLight);
      else setImage(greenLight);
    }
  };

  /**
   * LightTrackView Constructor
   * @param greenLight: Image of green light - train can pass
   * @param redLight: Image of red light - train cannot pass
   *
   *                Stores images and sets original to green
   */
  LightTrackView(Image greenLight, Image redLight)
  {
    this.greenLight = greenLight;
    this.redLight = redLight;
    setFitWidth(greenLight.getWidth());
    setFitHeight(greenLight.getHeight());
    setImage(greenLight);
  }
}
