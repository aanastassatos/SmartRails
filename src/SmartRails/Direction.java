/**
 * The enum values for direction used throughout the code.
 */

package SmartRails;

/**
 * Created by catherinewright on 10/21/17.
 */
public enum Direction
{
  LEFT, RIGHT, UP, DOWN;
  
  private Direction opposite;
  static {
    LEFT.opposite = RIGHT;
    RIGHT.opposite = LEFT;
    UP.opposite = DOWN;
    DOWN.opposite = UP;
  }
  
  /**
   * Returns the opposite of this enum
   * @return
   */
  public Direction getOpposite(){
    return opposite;
  }
}
