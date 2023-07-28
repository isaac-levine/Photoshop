package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.NoSuchElementException;

import model.Image;
import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Represents testing for the Image controller.
 */
public class ImageControllerImplTest {
  private ImageModel model;
  private Readable input;
  private ImageController controller;

  private Pixel[][] pixels;
  private Image sample;
  private Image sampleJPG;

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

    sample = new Image(3, 3, 255, pixels);

  }


  @Test
  public void testInvalidInput() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("bruh");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertNull(model.getImage("bruh"));
  }

  @Test(expected = NoSuchElementException.class)
  public void testInvalidCommand() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load no");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();
  }

  @Test
  public void testInvalidCommand2() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load a b c d");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertNull(model.getImage("b"));
  }

  @Test
  public void testInvalidCommand3() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load a b blur b c d");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertNull(model.getImage("c"));
  }

  @Test
  public void testLoadRedGray() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(100, 100, 100);
    pixels[0][1] = new Pixel(182, 182, 182);
    pixels[0][2] = new Pixel(200, 200, 200);

    pixels[1][0] = new Pixel(222, 222, 222);
    pixels[1][1] = new Pixel(49, 49, 49);
    pixels[1][2] = new Pixel(150, 150, 150);

    pixels[2][0] = new Pixel(55, 55, 55);
    pixels[2][1] = new Pixel(32, 32, 32);
    pixels[2][2] = new Pixel(180, 180, 180);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "red-grayscale sample red-grayscale");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("red-grayscale"));
  }

  @Test
  public void testLoadGreenGray() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(234, 234, 234);
    pixels[0][1] = new Pixel(48, 48, 48);
    pixels[0][2] = new Pixel(255, 255, 255);

    pixels[1][0] = new Pixel(101, 101, 101);
    pixels[1][1] = new Pixel(73, 73, 73);
    pixels[1][2] = new Pixel(29, 29, 29);

    pixels[2][0] = new Pixel(56, 56, 56);
    pixels[2][1] = new Pixel(109, 109, 109);
    pixels[2][2] = new Pixel(201, 201, 201);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "green-grayscale sample green-grayscale");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("green-grayscale"));
  }

  @Test
  public void testLoadBlueGray() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(50, 50, 50);
    pixels[0][1] = new Pixel(30, 30, 30);
    pixels[0][2] = new Pixel(78, 78, 78);

    pixels[1][0] = new Pixel(26, 26, 26);
    pixels[1][1] = new Pixel(123, 123, 123);
    pixels[1][2] = new Pixel(90, 90, 90);

    pixels[2][0] = new Pixel(57, 57, 57);
    pixels[2][1] = new Pixel(250, 250, 250);
    pixels[2][2] = new Pixel(130, 130, 130);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "blue-grayscale sample blue-grayscale");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("blue-grayscale"));
  }

  @Test
  public void testLoadLumaGray() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(128, 128, 128);
    pixels[0][1] = new Pixel(86, 86, 86);
    pixels[0][2] = new Pixel(177, 177, 177);

    pixels[1][0] = new Pixel(116, 116, 116);
    pixels[1][1] = new Pixel(81, 81, 81);
    pixels[1][2] = new Pixel(89, 89, 89);

    pixels[2][0] = new Pixel(56, 56, 56);
    pixels[2][1] = new Pixel(130, 130, 130);
    pixels[2][2] = new Pixel(170, 170, 170);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "luma-grayscale sample luma-grayscale");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("luma-grayscale"));
  }

  @Test
  public void testLoadValueGray() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(234, 234, 234);
    pixels[0][1] = new Pixel(182, 182, 182);
    pixels[0][2] = new Pixel(255, 255, 255);

    pixels[1][0] = new Pixel(222, 222, 222);
    pixels[1][1] = new Pixel(123, 123, 123);
    pixels[1][2] = new Pixel(150, 150, 150);

    pixels[2][0] = new Pixel(57, 57, 57);
    pixels[2][1] = new Pixel(250, 250, 250);
    pixels[2][2] = new Pixel(201, 201, 201);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "value-grayscale sample value-grayscale");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("value-grayscale"));
  }

  @Test
  public void testLoadIntensityGray() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(192, 192, 192);
    pixels[0][1] = new Pixel(75, 75, 75);
    pixels[0][2] = new Pixel(230, 230, 230);

    pixels[1][0] = new Pixel(121, 121, 121);
    pixels[1][1] = new Pixel(71, 71, 71);
    pixels[1][2] = new Pixel(59, 59, 59);

    pixels[2][0] = new Pixel(55, 55, 55);
    pixels[2][1] = new Pixel(102, 102, 102);
    pixels[2][2] = new Pixel(191, 191, 191);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample" +
            " intensity-grayscale sample intensity-grayscale");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("intensity-grayscale"));
  }

  @Test
  public void testLoadBrighten() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(200, 255, 150);
    pixels[0][1] = new Pixel(255, 148, 130);
    pixels[0][2] = new Pixel(255, 255, 178);

    pixels[1][0] = new Pixel(255, 201, 126);
    pixels[1][1] = new Pixel(149, 173, 223);
    pixels[1][2] = new Pixel(250, 129, 190);

    pixels[2][0] = new Pixel(155, 156, 157);
    pixels[2][1] = new Pixel(132, 209, 255);
    pixels[2][2] = new Pixel(255, 255, 230);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample" +
            " brighten 100 sample brighten");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("brighten"));
  }

  @Test
  public void testLoadDarken() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(0, 134, 0);
    pixels[0][1] = new Pixel(82, 0, 0);
    pixels[0][2] = new Pixel(100, 155, 0);

    pixels[1][0] = new Pixel(122, 1, 0);
    pixels[1][1] = new Pixel(0, 0, 23);
    pixels[1][2] = new Pixel(50, 0, 0);

    pixels[2][0] = new Pixel(0, 0, 0);
    pixels[2][1] = new Pixel(0, 9, 150);
    pixels[2][2] = new Pixel(80, 101, 30);


    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample darken -100 sample darken");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("darken"));
  }

  @Test
  public void testLoadFlipH() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(200 ,255, 78);
    pixels[0][1] = new Pixel(182 ,48, 30);
    pixels[0][2] = new Pixel(100 ,234 ,50);

    pixels[1][0] = new Pixel(150, 29, 90);
    pixels[1][1] = new Pixel(49, 73, 123);
    pixels[1][2] = new Pixel(222, 101, 26);

    pixels[2][0] = new Pixel(180 ,201, 130);
    pixels[2][1] = new Pixel(32 ,109 ,250);
    pixels[2][2] = new Pixel(55, 56, 57);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "horizontal-flip sample horizontal");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.processImage();

    assertEquals(sample, model.getImage("horizontal"));
  }

  @Test
  public void testLoadFlipV() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(55, 56, 57);
    pixels[0][1] = new Pixel(32, 109, 250);
    pixels[0][2] = new Pixel(180, 201, 130);

    pixels[1][0] = new Pixel(222, 101, 26);
    pixels[1][1] = new Pixel(49, 73, 123);
    pixels[1][2] = new Pixel(150, 29, 90);

    pixels[2][0] = new Pixel(100, 234, 50);
    pixels[2][1] = new Pixel(182, 48 ,30);
    pixels[2][2] = new Pixel(200 ,255 ,78);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "vertical-flip sample vertical");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("vertical"));
  }

  @Test
  public void testStackedCommand() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(55 ,55, 55);
    pixels[0][1] = new Pixel(32 ,32 ,32);
    pixels[0][2] = new Pixel( 180 ,180 ,180);

    pixels[1][0] = new Pixel(222 ,222 ,222);
    pixels[1][1] = new Pixel(49 ,49 ,49);
    pixels[1][2] = new Pixel(150 ,150 ,150);

    pixels[2][0] = new Pixel(100 ,100 ,100);
    pixels[2][1] = new Pixel(182 ,182, 182);
    pixels[2][2] = new Pixel(200 ,200 ,200);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample " +
            "red-grayscale sample sample-g \n " +
            "vertical-flip sample-g sample-g-flipped");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.processImage();

    assertEquals(sample, model.getImage("sample-g-flipped"));
  }

  @Test
  public void testBlur() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(77, 80, 25);
    pixels[0][1] = new Pixel(110, 88, 43);
    pixels[0][2] = new Pixel(93, 76, 40);

    pixels[1][0] = new Pixel(92, 79, 50);
    pixels[1][1] = new Pixel(115, 96, 96);
    pixels[1][2] = new Pixel(103, 81, 78);

    pixels[2][0] = new Pixel(47, 43, 55);
    pixels[2][1] = new Pixel(64, 75, 106);
    pixels[2][2] = new Pixel(70, 70, 81);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm test blur test blurred");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("blurred"));
  }

  @Test
  public void testSharp() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(134, 206, 14);
    pixels[0][1] = new Pixel(255, 172, 62);
    pixels[0][2] = new Pixel(220, 203, 70);

    pixels[1][0] = new Pixel(255,168, 112);
    pixels[1][1] = new Pixel(255, 255, 255);
    pixels[1][2] = new Pixel(255,150,222);

    pixels[2][0] = new Pixel(31,33, 109);
    pixels[2][1] = new Pixel(136,160, 255);
    pixels[2][2] = new Pixel(147,171, 215);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm test sharpen test sharp");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("sharp"));
  }

  // load and then apply mosaic to an image
  @Test
  public void testMosaic() {

    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(134, 206, 14);
    pixels[0][1] = new Pixel(255, 172, 62);
    pixels[0][2] = new Pixel(220, 203, 70);

    pixels[1][0] = new Pixel(255,168, 112);
    pixels[1][1] = new Pixel(255, 255, 255);
    pixels[1][2] = new Pixel(255,150,222);

    pixels[2][0] = new Pixel(31,33, 109);
    pixels[2][1] = new Pixel(136,160, 255);
    pixels[2][2] = new Pixel(147,171, 215);

    sample = new Image(3, 3, 255, pixels);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm test mosaic test 10 new");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("new"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_nullModel() {
    ImageModel model = null;
    Readable testIn = new StringReader("");
    ImageController controller = new ImageControllerImpl(model, testIn);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_nullReadable() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = null;
    ImageController controller = new ImageControllerImpl(model, testIn);
  }

  @Test
  public void test_loadppm() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.processImage();

    assertEquals(sample, model.getImage("sample"));
  }

  // pixel values change when converted to jpg because of compression
  @Test
  public void test_loadjpg() {

    Pixel[][] pixelsJPG;

    pixelsJPG = new Pixel[3][3];
    pixelsJPG[0][0] = new Pixel(171, 159, 171);
    pixelsJPG[0][1] = new Pixel(109, 97, 109);
    pixelsJPG[0][2] = new Pixel(214, 204, 106);

    pixelsJPG[1][0] = new Pixel(151, 139, 151);
    pixelsJPG[1][1] = new Pixel(65, 53, 65);
    pixelsJPG[1][2] = new Pixel(100, 90, 0);

    pixelsJPG[2][0] = new Pixel(27, 55, 77);
    pixelsJPG[2][1] = new Pixel(60, 88, 110);
    pixelsJPG[2][2] = new Pixel(181, 215, 121);


    sampleJPG = new Image(3, 3, 215, pixelsJPG);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.jpg sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.processImage();

    assertEquals(sampleJPG, model.getImage("sample"));

  }

  @Test
  public void test_loadpng() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.png sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.processImage();

    assertEquals(sample, model.getImage("sample"));

  }

  @Test
  public void test_loadbmp() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.bmp sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.processImage();

    assertEquals(sample, model.getImage("sample"));
  }

  @Test
  public void test_convertImageType() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.load("res/sample.bmp", "sample");
    assertEquals(sample, model.getImage("sample"));
  }

  @Test
  public void test_convertImageType2() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.png sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.load("res/sample.ppm", "sample");
    assertEquals(sample, model.getImage("sample"));
  }

  @Test public void testLoad_checkPixels() {
    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.load("res/sample.ppm", "sample");
    Image sample = model.getImage("sample");
    assertEquals(pixels[0][0], sample.getPixelAt(0,0));
    assertEquals(pixels[0][1], sample.getPixelAt(0,1));
    assertEquals(pixels[0][2], sample.getPixelAt(0,2));
    assertEquals(pixels[1][0], sample.getPixelAt(1,0));
    assertEquals(pixels[1][1], sample.getPixelAt(1,1));
    assertEquals(pixels[1][2], sample.getPixelAt(1,2));
    assertEquals(pixels[2][0], sample.getPixelAt(2,0));
    assertEquals(pixels[2][1], sample.getPixelAt(2,1));
    assertEquals(pixels[2][2], sample.getPixelAt(2,2));
  }

  @Test
  public void testSavePpm() {

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.ppm test save test test");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("test"));
  }

  @Test
  public void testSaveBmp() {

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.bmp test save test test");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("test"));
  }

  @Test
  public void testSavePng() {

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.png test save res/test.png test");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sample, model.getImage("test"));
  }

  @Test
  public void testSaveJpg() {

    Pixel[][] pixelsJPG;

    pixelsJPG = new Pixel[3][3];
    pixelsJPG[0][0] = new Pixel(171, 159, 171);
    pixelsJPG[0][1] = new Pixel(109, 97, 109);
    pixelsJPG[0][2] = new Pixel(214, 204, 106);

    pixelsJPG[1][0] = new Pixel(151, 139, 151);
    pixelsJPG[1][1] = new Pixel(65, 53, 65);
    pixelsJPG[1][2] = new Pixel(100, 90, 0);

    pixelsJPG[2][0] = new Pixel(27, 55, 77);
    pixelsJPG[2][1] = new Pixel(60, 88, 110);
    pixelsJPG[2][2] = new Pixel(181, 215, 121);


    sampleJPG = new Image(3, 3, 215, pixelsJPG);

    ImageModel model = new ImageModelImpl();
    Readable testIn = new StringReader("load res/sample.jpg test save test test");
    ImageController controller = new ImageControllerImpl(model, testIn);

    controller.processImage();

    assertEquals(sampleJPG, model.getImage("test"));
  }

  @Test public void testSave() {
    ImageModel model = new ImageModelImpl();
    StringBuilder stringBuilder = new StringBuilder();
    Readable testIn = new StringReader("load res/sample.ppm sample");
    ImageController controller = new ImageControllerImpl(model, testIn);
    controller.load("res/sample.ppm", "sample");
    controller.save("res/sample.ppm", "sample");
    Image test = model.getImage("sample");
    int width = test.getWidth();
    int height = test.getHeight();

    stringBuilder.append("P3\n"
            + width + " " + height + "\n"
            + test.getMax() + "\n");
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        stringBuilder.append(test.getPixelAt(row, col).toString() + "\n");
      }
    }
    assertEquals(test.toString(), stringBuilder.toString());
  }
}