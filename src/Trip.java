/**
 * Trip class holds destination and train name
 */

class Trip
{
  String destination; //StationTrack name of destination
  String train; //Train name travelling on trip

  /**
   * Trip constructor:
   * @param destination : Name of destination
   * @param train : Name of train
   */
  Trip(String destination, String train)
  {
    this.destination = destination;
    this.train = train;
  }
}
