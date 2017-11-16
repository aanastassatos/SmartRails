/**
 * Resource loader used to retrieve resources from the res folder
 */

package res;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader
{
  static ResourceLoader rl = new ResourceLoader();
  
  /**
   * Gets the requested track image based on the fileName, then returns it with the requested width and height.
   * @param fileName
   * @param width
   * @param height
   * @return Image
   */
  public static Image getTrackImage(String fileName, double width, double height)
  {
    return getImage("tracks/"+fileName, width, height);
  }
  
  /**
   * Gets the requested train image based on the fileName, then returns it with the requested width and height.
   * @param fileName
   * @param width
   * @param height
   * @return
   */
  public static Image getTrainImage(String fileName, double width, double height)
  {
    return getImage("train/"+fileName, width, height);
  }
  
  /**
   * Gets the requested image based on the fileName, then returns it with the requested width and height.
   * @param fileName
   * @param width
   * @param height
   * @return
   */
  public static Image getImage(String fileName, double width, double height)
  {
    InputStream is = new BufferedInputStream(rl.getClass().getResourceAsStream(fileName));
    Image image = new Image(is, width, height, false, false);
    
    return image;
  }
  
  /**
   * Gets TrackMap.txt
   * @return
   */
  public static InputStreamReader getTrackMap()
  {
    InputStream trackMap = rl.getClass().getResourceAsStream("TrackMap.txt");
    InputStreamReader stream = new InputStreamReader(trackMap);
    return stream;
  }
  
  /**
   * Gets the music file used in the program.
   * @return
   */
  public static MediaPlayer getMediaPlayer()
  {
    String musicFile = ResourceLoader.class.getResource("ThomasTheTank.mp3").toString();
    Media sound = new Media(musicFile);
    return new MediaPlayer(sound);
  }
  
  /**
   * Returns the title images
   * @param filename
   * @param width
   * @param height
   * @return
   */
  public static Image getTitle(String filename, int width, int height)
  {
    return getImage(filename + ".png", width, height);
  }
}
