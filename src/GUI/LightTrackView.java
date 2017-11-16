/**
 * LightTrackView Class
 * Extends ImageView
 *
 * LightTrackView is used in the GUI to visually represent the state of the light track it is attached to
 */

package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LightTrackView extends ImageView
{
  private final Image greenLight; //image for the green Light.
  private final Image redLight; //image for the red light.
  private ChangeListener listener = new ChangeListener()  //ChangeListener that is put on the light track to determine which color the light should be.
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
  
  /**
   * getListener method
   * @return ChangeListener listener
   */
  public ChangeListener getListener()
  {
    return listener;
  }
}
