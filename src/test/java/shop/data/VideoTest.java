package shop.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

// TODO:  complete the tests
public class VideoTest {

	private static final Video A = new VideoObj("A", 2009, "Zebra");

	@ParameterizedTest
	@DisplayName("Test video equals true input")
	@MethodSource("equalsInputTrue")
	public void testEqualsTrue(VideoObj other) {
		assertTrue(A.equals(other));
	}

	private static Stream<Arguments> equalsInputTrue() {
		return Stream.of(
				Arguments.of(A),
				Arguments.of(new VideoObj("A", 2009, "Zebra")),
				Arguments.of(new VideoObj(new String("A"), 2009, "Zebra")),
				Arguments.of(new VideoObj("A", 2009, new String("Zebra")))
		);
	}

	@ParameterizedTest
	@DisplayName("Test video equals false input")
	@MethodSource("equalsInputFalse")
	public void testEqualsFalse(VideoObj other) {
		assertFalse(A.equals(other));
	}

	private static Stream<Arguments> equalsInputFalse() {
		return Stream.of(
				Arguments.of(new VideoObj("A" + "1", 2009, "Zebra")),
				Arguments.of(new VideoObj("A", 2009 + 1, "Zebra")),
				Arguments.of(new VideoObj("A", 2009, "Zebra" + "1"))
		);
	}

	@Test
	@DisplayName("Test video equals false on new Object")
	public void testEqualsFalseNewObject() {
		//For some reason parameterized test refuses to take just new Object, hence here
		assertFalse(A.equals(new Object()));
	}

	@Test
	@DisplayName("Test video equals false on null")
	public void testEqualsFalseNull() {
		//For some reason parameterized test refuses to take just null, hence here
		assertFalse(A.equals(null));
	}

  @Test
  public void testCompareTo() {
	  String title = "A", title2 = "B";
	    int year = 2009, year2 = 2010;
	    String director = "Zebra", director2 = "Zzz";
	    VideoObj a = new VideoObj(title,year,director);
	    VideoObj b = new VideoObj(title2,year,director);
	    assertTrue( a.compareTo(b) < 0 );
	    assertTrue( a.compareTo(b) == -b.compareTo(a) );
	    assertTrue( a.compareTo(a) == 0 );

	    b = new VideoObj(title,year2,director);
	    assertTrue( a.compareTo(b) < 0 );
	    assertTrue( a.compareTo(b) == -b.compareTo(a) );

	    b = new VideoObj(title,year,director2);
	    assertTrue( a.compareTo(b) < 0 );
	    assertTrue( a.compareTo(b) == -b.compareTo(a) );

	    b = new VideoObj(title2,year2,director2);
	    assertTrue( a.compareTo(b) < 0 );
	    assertTrue( a.compareTo(b) == -b.compareTo(a) );

	    try {
	      a.compareTo(null);
	      fail();
	    } catch ( NullPointerException e ) {}
  }

  @Test
  @DisplayName("Test video toString outputs as intended")
  public void testToString() {
	  String s = Data.newVideo("A",2000,"B").toString();
	  assertEquals( s, "A (2000) : B" );
  }

  @Test
  @DisplayName("Test hashing provides correct hash value")
  public void testHashCode() {
    assertEquals(-875826552, new VideoObj("None", 2009, "Zebra").hashCode());
  }
}
