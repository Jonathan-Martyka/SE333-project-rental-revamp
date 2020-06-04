package shop.data;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// TODO:  complete the tests
public class DataTest {

  @Test
  @DisplayName("Test attributes created with correct values")
  public void testConstructorAndAttributes() {
    String title1 = "XX";
    String director1 = "XY";
    String title2 = " XX ";
    String director2 = " XY ";
    int year = 2002;

    Video v1 = Data.newVideo(title1, year, director1); //video title, year, director
    assertSame(title1, v1.title());
    assertEquals(year, v1.year());
    assertSame(director1, v1.director());

    Video v2 = Data.newVideo(title2, year, director2);
    assertEquals(title1, v2.title());
    assertEquals(director1, v2.director());
  }


  @ParameterizedTest
  @DisplayName("Strong normal testing of video creation")
  @MethodSource("StrongNormalInput")
  void testVideoStrongNormal(String title, int year, String director) {
    Video v = Data.newVideo(title, year, director);
    assertEquals(title, v.title());
    assertEquals(year, v.year());
    assertEquals(director, v.director());
  }

  private static Stream<Arguments> StrongNormalInput() {
    return Stream.of(
            Arguments.of("Inception 5", 3012, "Christopher Nolan Jr."),
            Arguments.of("Inception 5", 1801, "Christopher Nolan Jr."),
            Arguments.of("Inception 5", 4999, "Christopher Nolan Jr.")
            );
  }

  @ParameterizedTest
  @DisplayName("Strong robust testing additions of video creation")
  @MethodSource("StrongRobustInput")
  void testVideoStrongRobust(String title, int year, String director) {
    assertThrows(IllegalArgumentException.class, () -> Data.newVideo(title, year, director));
  }

  private static Stream<Arguments> StrongRobustInput() {
    return Stream.of(
            Arguments.of(" ", 3012, "Christopher Nolan Jr."),
            Arguments.of(null, 3012, "Christopher Nolan Jr."),
            Arguments.of("Inception 5", 100, "Christopher Nolan Jr."),
            Arguments.of("Inception 5", 15000, "Christopher Nolan Jr."),
            Arguments.of("Inception 5", 3012, " "),
            Arguments.of("Inception 5", 3012, null),
            Arguments.of("Inception 5", 1800, "Christopher Nolan Jr."),
            Arguments.of("Inception 5", 5000, "Christopher Nolan Jr.")
    );
  }

}
