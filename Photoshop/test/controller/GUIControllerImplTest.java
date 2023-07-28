package controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import model.Image;
import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import model.commands.Mosaic;
import model.commands.Sepia;
import view.IView;
import view.JFrameView;
import view.MockView;

import static org.junit.Assert.assertEquals;

/**
 * Represents testing for our GUI Controller.
 */
public class GUIControllerImplTest {
  private Image sample;

  @Before
  public void setUp() {

    Pixel[][] pixels = new Pixel[3][3];
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

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    ImageModel model = null;
    IView view = new JFrameView(model);
    IGUIController controller = new GUIControllerImpl(model, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    ImageModel model = new ImageModelImpl();
    IView view = null;
    IGUIController controller = new GUIControllerImpl(model, view);
  }

  @Test
  public void testLoad() {
    ImageModel model = new ImageModelImpl();
    Appendable log = new StringBuilder();
    IView view = new MockView(log);
    IGUIController controller = new GUIControllerImpl(model, view);

    assertEquals("", log.toString());
    controller.actionPerformed(new ActionEvent("load", 0, view.loadFile()));
    assertEquals("Image loaded.", log.toString());
  }

  @Test
  public void testSave() {
    ImageModel model = new ImageModelImpl();
    Appendable log = new StringBuilder();
    IView view = new MockView(log);
    IGUIController controller = new GUIControllerImpl(model, view);

    assertEquals("", log.toString());
    controller.actionPerformed(new ActionEvent("save", 0, view.saveFile()));
    assertEquals("Image saved.", log.toString());
  }

  @Test
  public void testEditImage() {
    ImageModel model = new ImageModelImpl();
    Appendable log = new StringBuilder();
    IView view = new MockView(log);
    IGUIController controller = new GUIControllerImpl(model, view);
    model.addImage("editedImage", sample);
    controller.editImage(new Sepia(), "editedImage");
    assertEquals("Message rendered: Filter applied", log.toString());
  }

  // test mosaic button works
  @Test
  public void testMosaicImage() {
    ImageModel model = new ImageModelImpl();
    Appendable log = new StringBuilder();
    IView view = new MockView(log);
    IGUIController controller = new GUIControllerImpl(model, view);
    model.addImage("editedImage", sample);
    controller.editImage(new Mosaic(10), "editedImage");
    assertEquals("Mosaic applied", log.toString());
  }
}