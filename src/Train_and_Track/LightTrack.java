package Train_and_Track;

import GUI.LightTrackView;

import Train_and_Track.Message.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

public class LightTrack extends Track
{
  BooleanProperty lightOn = new SimpleBooleanProperty();
  
  public LightTrack(double x, double y)
  {
    super(TrackType.LIGHT, x, y);
  }
  
  public void setLightListener(LightTrackView lightTrackView)
  {
    lightOn.addListener(lightTrackView.getListener());
    lightOn.setValue(false);
  }
  
//  synchronized
  void setLightOn(boolean lightOn)
  {
    this.lightOn.setValue(lightOn);
  }

  boolean isLightOn()
  {
    return lightOn.get();
  }
}
