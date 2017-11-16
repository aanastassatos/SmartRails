/**
 * StraightTrack class
 * Extends Track
 */

package Train_and_Track;

public class StraightTrack extends Track
{
  /**
   * Constructor for the straight track. It's literally just the Track class with a different name.
   * @param trackType
   * @param x
   * @param y
   */
  public StraightTrack(TrackType trackType, double x, double y)
  {
    super(trackType, x, y);
  }
}
