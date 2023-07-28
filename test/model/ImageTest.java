package model;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.Assert.assertEquals;


/**
 * Represents tests for Images.
 */
public class ImageTest {

  private Pixel[][] pixels;
  private Image image;

  @Before
  public void setUp() {
    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(100, 234, 50);
    pixels[0][1] = new Pixel(182, 48, 30);
    pixels[0][2] = new Pixel(200, 255, 78);

    pixels[1][0] = new Pixel(222, 101, 26);
    pixels[1][1] = new Pixel(49, 73, 123);
    pixels[1][2] = new Pixel(150, 29, 90);

    pixels[2][0] = new Pixel(55, 56, 57);
    pixels[2][1] = new Pixel(32, 109, 250);
    pixels[2][2] = new Pixel(180, 201, 130);

    image = new Image(3, 3, 255, pixels);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidth() {
    image = new Image(2, 3, 255, pixels);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHeight() {
    image = new Image(3, 4, 255, pixels);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegValues() {
    image = new Image(-3, 3, 255, pixels);
    image = new Image(3, 33, 255, pixels);
    image = new Image(3, 3, -255, pixels);
  }

  @Test
  public void testGetters() {
    assertEquals(255, image.getMax());
    assertEquals(3, image.getHeight());
    assertEquals(3, image.getWidth());
  }

  @Test
  public void getPixelAt() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    assertEquals(new Pixel(49, 73, 123), image.getPixelAt(1, 1));
    assertEquals(new Pixel(180, 201, 130), image.getPixelAt(2, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtException_rowMax() {
    image.getPixelAt(3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtException_colMax() {
    image.getPixelAt(2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtException_rowNeg() {
    image.getPixelAt(-2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtException_colNeg() {
    image.getPixelAt(0, -1);
  }

  @Test
  public void grayscale_red() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image red = image.greyscale(ImageModel.Grayscale.Red);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(100, 100, 100), red.getPixelAt(0, 0));
    assertEquals(new Pixel(150, 150, 150), red.getPixelAt(1, 2));

  }

  @Test
  public void grayscale_green() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image green = image.greyscale(ImageModel.Grayscale.Green);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(234, 234, 234), green.getPixelAt(0, 0));
    assertEquals(new Pixel(29, 29, 29), green.getPixelAt(1, 2));
  }

  @Test
  public void grayscale_blue() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image blue = image.greyscale(ImageModel.Grayscale.Blue);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(50, 50, 50), blue.getPixelAt(0, 0));
    assertEquals(new Pixel(90, 90, 90), blue.getPixelAt(1, 2));
  }

  @Test
  public void mosaic() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image newImage = image.mosaic(10);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(50, 50, 50), newImage.getPixelAt(0, 0));
    assertEquals(new Pixel(90, 90, 90), newImage.getPixelAt(1, 2));
  }

  @Test
  public void grayscale_value() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image value = image.greyscale(ImageModel.Grayscale.Value);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(234, 234, 234), value.getPixelAt(0, 0));
    assertEquals(new Pixel(150, 150, 150), value.getPixelAt(1, 2));
  }

  @Test
  public void grayscale_luma() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image luma = image.greyscale(ImageModel.Grayscale.Luma);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(128, 128, 128), luma.getPixelAt(0, 0));
    assertEquals(new Pixel(89, 89, 89), luma.getPixelAt(1, 2));
  }

  @Test
  public void grayscale_intensity() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image intensity = image.greyscale(ImageModel.Grayscale.Intensity);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(192, 192, 192), intensity.getPixelAt(0, 0));
    assertEquals(new Pixel(59, 59, 59), intensity.getPixelAt(1, 2));
  }

  @Test
  public void exposure_brighten() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image bright = image.brighten(10);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(110, 244, 60), bright.getPixelAt(0, 0));
    assertEquals(new Pixel(160, 39, 100), bright.getPixelAt(1, 2));
  }

  @Test
  public void exposure_darken() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image bright = image.brighten(-10);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(90, 224, 40), bright.getPixelAt(0, 0));
    assertEquals(new Pixel(140, 19, 80), bright.getPixelAt(1, 2));
  }

  @Test
  public void flip_horizontal() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image flipped = image.flip(true);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(200 ,255 ,78), flipped.getPixelAt(0, 0));
    assertEquals(new Pixel(222 ,101 ,26), flipped.getPixelAt(1, 2));
  }

  @Test
  public void flip_vertical() {
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    Image flipped = image.flip(false);
    // check to see that original pixel didn't change
    assertEquals(new Pixel(100, 234, 50), image.getPixelAt(0, 0));
    // check to see that new pixel has changed values
    assertEquals(new Pixel(55 ,56, 57), flipped.getPixelAt(0, 0));
    assertEquals(new Pixel(150 ,29, 90), flipped.getPixelAt(1, 2));
  }

  @Test
  public void testBufferedImage() {
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(255, 100, 250);
    this.pixels[0][1] = new Pixel(45, 50, 55);
    this.pixels[1][0] = new Pixel(150, 120, 130);
    this.pixels[1][1] = new Pixel(20, 90, 200);
    image = new Image(2, 2, 255, pixels);

    BufferedImage bi = new BufferedImage(2,2, TYPE_INT_RGB);
    Color a = new Color(255, 100, 250);
    bi.setRGB(0,0, a.getRGB());
    Color b = new Color(45, 50, 55);
    bi.setRGB(0,1, b.getRGB());
    Color c = new Color(150, 120, 130);
    bi.setRGB(1,0, c.getRGB());
    Color d = new Color(20, 90, 200);
    bi.setRGB(1,1, d.getRGB());

    assertEquals(bi.getRGB(0,0), image.bufferedImage().getRGB(0,0));
    assertEquals(bi.getRGB(1,1), image.bufferedImage().getRGB(1,1));
  }
}