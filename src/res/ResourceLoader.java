package res;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class ResourceLoader
{
  static ResourceLoader rl = new ResourceLoader();
  
  public static Image getTrackImage(String fileName, double width, double height, boolean preserveRatio, boolean smooth)
  {
    return getImage("tracks/"+fileName, width, height, preserveRatio, smooth);
  }
  
  public static Image getTrainImage(String fileName, double width, double height, boolean preserveRatio, boolean smooth)
  {
    
    return getImage("trains/"+fileName, width, height, preserveRatio, smooth);
  }
  
  public static Image getImage(String fileName, double width, double height, boolean preserveRatio, boolean smooth)
  {
    InputStream is = new BufferedInputStream(rl.getClass().getResourceAsStream(fileName));
    Image image = new Image(is, width, height, preserveRatio, smooth);
    
    return image;
  }
}
