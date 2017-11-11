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
  
  @Override
  synchronized void readMessage(Message msg)
  {
    if(msg.messageType == MessageType.LIGHTON)
    {
      setLightOn(true);
      sendMessageToNextTrack(msg);
    }
    
    else if(msg.messageType == MessageType.LIGHTOFF)
    {
      setLightOn(false);
      sendMessageToNextTrack(msg);
    }
    
    else
    {
      super.readMessage(msg);
    }
  }
  
  synchronized void setLightOn(boolean lightOn)
  {
    this.lightOn.setValue(lightOn);
  }

  boolean isLightOn()
  {
    return lightOn.get();
  }
}
