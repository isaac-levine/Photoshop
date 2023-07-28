package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Represents tests for Pixels.
 */
public class PixelTest {

  private Pixel pixel;

  @Before
  public void setUp() {
    pixel = new Pixel(50, 100, 150);
  }

  @Test
  public void getColors() {
    assertEquals(50, pixel.getRed());
    assertEquals(100, pixel.getGreen());
    assertEquals(150, pixel.getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeValues() {
    Pixel p1 = new Pixel(-2, 5, 10);
    Pixel p2 = new Pixel(2, -5, 10);
    Pixel p3 = new Pixel(2, 5, -10);
    Pixel p4 = new Pixel(-2, -5, -10);
  }

  @Test
  public void testGrayscale() {
    assertEquals(new Pixel(50, 50, 50), pixel.grayscale(ImageModel.Grayscale.Red));
    assertEquals(new Pixel(100, 100, 100), pixel.grayscale(ImageModel.Grayscale.Green));
    assertEquals(new Pixel(150, 150, 150), pixel.grayscale(ImageModel.Grayscale.Blue));
    assertEquals(new Pixel(100, 100, 100), pixel.grayscale(ImageModel.Grayscale.Luma));
    assertEquals(new Pixel(150, 150, 150), pixel.grayscale(ImageModel.Grayscale.Value));
    assertEquals(new Pixel(92, 92, 92), pixel.grayscale(ImageModel.Grayscale.Intensity));
  }

  @Test
  public void testExposure() {
    assertEquals(new Pixel(100, 150, 200), pixel.brighten(50));
    assertEquals(new Pixel(160, 210, 255), pixel.brighten(110));
    assertEquals(new Pixel(255, 255, 255), pixel.brighten(300));
    assertEquals(new Pixel(0, 50, 100), pixel.brighten(-50));
    assertEquals(new Pixel(0, 10, 60), pixel.brighten(-90));
    assertEquals(new Pixel(0, 0, 0), pixel.brighten(-300));
    assertEquals(new Pixel(50, 100, 150), pixel.brighten(0));
  }
}