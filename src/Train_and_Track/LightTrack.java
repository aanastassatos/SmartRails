/**
 * Class for the light track.
 * Extends Track
 */

package Train_and_Track;

import GUI.LightTrackView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class LightTrack extends Track
{
  BooleanProperty lightOn = new SimpleBooleanProperty(); //Boolean property denoting whether the light is on or off.
  
  /**
   * Constructor for the light track.
   * @param x
   * @param y
   */
  public LightTrack(double x, double y)
  {
    super(TrackType.LIGHT, x, y);
  }
  
  /**
   * Connects the boolean property of this light to the corresponding lightTrackView so that when lightOn is changed internally,
   * it is also changed visually.
   * @param lightTrackView
   */
  public void setLightListener(LightTrackView lightTrackView)
  {
    lightOn.addListener(lightTrackView.getListener());
    lightOn.setValue(false);
  }
  
  /**
   * Sets the value of lightOn.
   * @param lightOn
   */
  void setLightOn(boolean lightOn)
  {
    this.lightOn.setValue(lightOn);
  }
  
  /**
   * Returns whether or not the light is on.
   * @return
   */
  boolean isLightOn()
  {
    return lightOn.get();
  }
}
