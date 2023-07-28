package view;

import org.junit.Test;

import controller.GUIControllerImpl;
import controller.IGUIController;
import model.ImageModel;
import model.ImageModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Represents testing for our GUI view.
 */
public class JFrameViewTest {

  @Test
  public void testRenderMessage() {
    ImageModel model = new ImageModelImpl();
    Appendable log = new StringBuilder();
    IView view = new MockView(log);
    IGUIController controller = new GUIControllerImpl(model, view);

    view.renderMessage("Test this message.");
    assertEquals("Message rendered: Test this message.", log.toString());
  }
}