package shop.data;

/**
 * Implementation of Video interface.
 * @see Data
 */
final class VideoObj implements Video {
  private final String _title;
  private final int    _year;
  private final String _director;

  /**
   * Initialize all object attributes.
   * Title and director are "trimmed" to remove leading and final space.
   * @throws IllegalArgumentException if object invariant violated.
   */
  VideoObj(String title, int year, String director) {
    _title = title;
    _director = director;
    _year = year;
  }

  public String director() {
    return _director;
  }

  public String title() {
    return _title;
  }

  public int year() {
    return _year;
  }

  public boolean equals(Object thatObject) {
	//if true, then same instance
	  if (this == thatObject)
		  return true;
	  
	  //should not throw ClassCastException. This checks for same class type
	  if (thatObject == null || !(this.getClass().equals(thatObject.getClass())))
		  return false;
	  
	  //cast for reading; check all elements are equal
	  VideoObj that = (VideoObj) thatObject;
	  return _title.equals(that._title)
			  && _year == that._year
			  && _director.equals(that._director);
  }

  private int hcode;
  public int hashCode() {
	  if (hcode == 0) {
		  hcode = 17;
		  hcode = 37*hcode + _title.hashCode();
		  hcode = 37*hcode + _year;
		  hcode = 37*hcode + _director.hashCode();
	  }
    return hcode;
  }

  public int compareTo(Object thatObject) {
	//if true, then same instance
	  if (this == thatObject)
		  return 0;
	  
	//should throw ClassCastException if incompatible
	  if (!(this.getClass().equals(thatObject.getClass())))
		  throw new ClassCastException();
	  
	  //compare each element and return based on comparison
	  VideoObj that = (VideoObj)thatObject;
	  if (_title.compareTo(that._title) != 0) {
		  return _title.compareTo(that._title);
	  } else if (_year - that._year != 0) {
		  return _year - that._year;
	  } else if (_director.compareTo(that._director) != 0) {
		  return _director.compareTo(that._director);
	  }
	  
	  //if everything is the same, return 0
	  return 0;
  }

  public String toString() {
	  return _title + " (" + _year + ") : " + _director;
  }
}
