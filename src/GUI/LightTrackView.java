package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LightTrackView extends ImageView
{
  private final Image greenLight;
  private final Image redLight;
  private ChangeListener listener = new ChangeListener()
  {
    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1)
    {
      if((boolean) t1) setImage(redLight);
      else setImage(greenLight);
    }
  };
  
  public LightTrackView(Image greenLight, Image redLight)
  {
    this.greenLight = greenLight;
    this.redLight = redLight;
    setFitWidth(greenLight.getWidth());
    setFitHeight(greenLight.getHeight());
    setImage(greenLight);
  }
  
  public ChangeListener getListener()
  {
    return listener;
  }
}
