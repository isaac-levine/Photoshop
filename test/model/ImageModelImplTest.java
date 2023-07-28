package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.commands.Blur;
import model.commands.Brighten;
import model.commands.Flip;
import model.commands.Grayscale;
import model.commands.GreyscaleCT;
import model.commands.ICommand;
import model.commands.Mosaic;
import model.commands.Sepia;
import model.commands.Sharpen;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * Represents tests for the ImageModel.
 */
public class ImageModelImplTest {
  private ImageModel model;
  private Pixel[][] pixels;
  private Map<String, Image> images;

  @Before
  public void setUp() {
    this.model = new ImageModelImpl();
    this.images = new HashMap<>();
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(100, 200, 255);
    this.pixels[0][1] = new Pixel(45, 50, 55);
    this.pixels[1][0] = new Pixel(150, 120, 130);
    this.pixels[1][1] = new Pixel(20, 90, 200);
  }

  @Test
  public void testAddImage() {
    assertEquals(null, model.getImage("image"));

    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);
    assertEquals(image, model.getImage("image"));
  }

  @Test
  public void testGetImage() {
    Pixel[][] pixMap1 = new Pixel[1][1];
    pixMap1[0][0] = new Pixel(100, 200, 255);
    Pixel[][] pixMap2 = new Pixel[1][1];
    pixMap2[0][0] = new Pixel(50, 60, 70);
    Image image1 = new Image(1, 1, 255, pixMap1);
    Image image2 = new Image(1, 1, 255, pixMap2);

    model.addImage("image1", image1);
    model.addImage("image2", image2);

    assertEquals(image1, model.getImage("image1"));
    assertEquals(image2, model.getImage("image2"));
  }

  @Test
  public void testGetLoadedImages() {
    Pixel[][] pixMap1 = new Pixel[1][1];
    pixMap1[0][0] = new Pixel(100, 200, 255);
    Pixel[][] pixMap2 = new Pixel[1][1];
    pixMap2[0][0] = new Pixel(50, 60, 70);
    Image image1 = new Image(1, 1, 255, pixMap1);
    Image image2 = new Image(1, 1, 255, pixMap2);

    model.addImage("image1", image1);
    model.addImage("image2", image2);
    this.images.put("image1", image1);
    this.images.put("image2", image2);
    assertEquals(images, model.getLoadedImages());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyGrayscaleException_NullImage() {
    ICommand grayRed = new Grayscale(ImageModel.Grayscale.Red);
    model.apply(grayRed, "image", "imageRed");
  }

  @Test
  public void testApplyGreyscaleRed() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(100, 100, 100);
    pixels2[0][1] = new Pixel(45, 45, 45);
    pixels2[1][0] = new Pixel(150, 150, 150);
    pixels2[1][1] = new Pixel(20, 20, 20);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageRed", photo2);
    ICommand grayRed = new Grayscale(ImageModel.Grayscale.Red);
    model.apply(grayRed, "image", "imageRed");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyMosaic() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(100, 100, 100);
    pixels2[0][1] = new Pixel(45, 45, 45);
    pixels2[1][0] = new Pixel(150, 150, 150);
    pixels2[1][1] = new Pixel(20, 20, 20);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imgaeMosaic", photo2);
    ICommand grayRed = new Mosaic(10);
    model.apply(grayRed, "image", "imgaeMosaic");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyGreyscaleGreen() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(200, 200, 200);
    pixels2[0][1] = new Pixel(50, 50, 50);
    pixels2[1][0] = new Pixel(120, 120, 120);
    pixels2[1][1] = new Pixel(90, 90, 90);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageGreen", photo2);
    ICommand grayGreen = new Grayscale(ImageModel.Grayscale.Green);
    model.apply(grayGreen, "image", "imageGreen");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyGreyscaleBlue() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(255, 255, 255);
    pixels2[0][1] = new Pixel(55, 55, 55);
    pixels2[1][0] = new Pixel(130, 130, 130);
    pixels2[1][1] = new Pixel(200, 200, 200);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageBlue", photo2);
    ICommand grayBlue = new Grayscale(ImageModel.Grayscale.Blue);
    model.apply(grayBlue, "image", "imageBlue");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyGreyscaleLuma() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(185, 185, 185);
    pixels2[0][1] = new Pixel(50, 50, 50);
    pixels2[1][0] = new Pixel(133, 133, 133);
    pixels2[1][1] = new Pixel(103, 103, 103);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageLuma", photo2);
    ICommand grayLuma = new Grayscale(ImageModel.Grayscale.Luma);
    model.apply(grayLuma, "image", "imageLuma");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyGreyscaleValue() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(255, 255, 255);
    pixels2[0][1] = new Pixel(55, 55, 55);
    pixels2[1][0] = new Pixel(150, 150, 150);
    pixels2[1][1] = new Pixel(200, 200, 200);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageValue", photo2);
    ICommand grayValue = new Grayscale(ImageModel.Grayscale.Value);
    model.apply(grayValue, "image", "imageValue");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyGreyscaleIntensity() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(182, 182, 182);
    pixels2[0][1] = new Pixel(49, 49, 49);
    pixels2[1][0] = new Pixel(127, 127, 127);
    pixels2[1][1] = new Pixel(83, 83, 83);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageIntensity", photo2);
    ICommand grayIntensity = new Grayscale(ImageModel.Grayscale.Intensity);
    model.apply(grayIntensity, "image", "imageIntensity");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyBrightenException_NullImage() {
    ICommand brighten = new Brighten(10);
    model.apply(brighten, "image", "imageEdit");
  }

  @Test
  public void testApplyBrighten() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(200, 255, 255);
    pixels2[0][1] = new Pixel(145, 150, 155);
    pixels2[1][0] = new Pixel(250, 220, 230);
    pixels2[1][1] = new Pixel(120, 190, 255);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageBright", photo2);
    ICommand brighten = new Brighten(100);
    model.apply(brighten, "image", "imageBright");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyDarken() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(0, 100, 155);
    pixels2[0][1] = new Pixel(0, 0, 0);
    pixels2[1][0] = new Pixel(50, 20, 30);
    pixels2[1][1] = new Pixel(0, 0, 100);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageDark", photo2);
    ICommand darken = new Brighten(-100);
    model.apply(darken, "image", "imageDark");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFlipException_NullImage() {
    ICommand flip = new Flip(true);
    model.apply(flip, "image", "imageEdit");
  }

  @Test
  public void testApplyFlipHorizontal() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(45, 50, 55);
    pixels2[0][1] = new Pixel(100, 200, 255);
    pixels2[1][0] = new Pixel(20, 90, 200);
    pixels2[1][1] = new Pixel(150, 120, 130);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageHorizontal", photo2);
    ICommand horizontal = new Flip(true);
    model.apply(horizontal, "image", "imageHorizontal");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyFlipVertical() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(150, 120, 130);
    pixels2[0][1] = new Pixel(20, 90, 200);
    pixels2[1][0] = new Pixel(100, 200, 255);
    pixels2[1][1] = new Pixel(45, 50, 55);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageVertical", photo2);
    ICommand vertical = new Flip(false);
    model.apply(vertical, "image", "imageVertical");
    assertEquals(this.images, model.getLoadedImages());
  }

  // ADDED TESTS FOR ASSIGNMENT 5

  @Test
  public void testApplySepia() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(240, 213, 166);
    pixels2[0][1] = new Pixel(65, 58, 45);
    pixels2[1][0] = new Pixel(174, 155, 121);
    pixels2[1][1] = new Pixel(113, 100, 79);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageSEPIA", photo2);
    ICommand sepia = new Sepia();
    model.apply(sepia, "image", "imageSEPIA");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyGreyscaleCT() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(212, 212, 212);
    pixels2[0][1] = new Pixel(54, 54, 54);
    pixels2[1][0] = new Pixel(140, 140, 140);
    pixels2[1][1] = new Pixel(105, 105, 105);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageGREYSCALE", photo2);
    ICommand greyscale = new GreyscaleCT();
    model.apply(greyscale, "image", "imageGREYSCALE");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplyBlur() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(49, 76, 97);
    pixels2[0][1] = new Pixel(34, 55, 77);
    pixels2[1][0] = new Pixel(53, 69, 91);
    pixels2[1][1] = new Pixel(34, 55, 87);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageBLUR", photo2);
    ICommand blur = new Blur();
    model.apply(blur, "image", "imageBLUR");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testApplySharpen() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(153, 255, 255);
    pixels2[0][1] = new Pixel(112, 152, 200);
    pixels2[1][0] = new Pixel(191, 204, 255);
    pixels2[1][1] = new Pixel(93, 182, 255);
    Image photo2 = new Image(2, 2, 255, pixels2);

    this.images.put("image", image);
    this.images.put("imageSHARPEN", photo2);
    ICommand sharpen = new Sharpen();
    model.apply(sharpen, "image", "imageSHARPEN");
    assertEquals(this.images, model.getLoadedImages());
  }

  @Test
  public void testCreateFrequencies() {
    Image image = new Image(2, 2, 255, pixels);
    model.addImage("image", image);
    int[][] output = model.createFrequencies("image");

    int[][] expected = new int[4][256];
    expected[0][100] = 1;
    expected[0][45] = 1;
    expected[0][150] = 1;
    expected[0][20] = 1;
    expected[1][200] = 1;
    expected[1][50] = 1;
    expected[1][120] = 1;
    expected[1][90] = 1;
    expected[2][255] = 1;
    expected[2][55] = 1;
    expected[2][130] = 1;
    expected[2][200] = 1;
    expected[3][185] = 1;
    expected[3][50] = 1;
    expected[3][133] = 1;
    expected[3][103] = 1;

    assertArrayEquals(expected, output);
  }
}
