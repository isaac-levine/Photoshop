import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import controller.GUIControllerImpl;
import controller.IGUIController;
import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.IView;
import view.JFrameView;

/**
 * Runs the controller. Takes in user input through the console.
 */
public class Main {
  /**
   * Runs the controller given a reader and an image model.
   *
   * @param args command arguments
   */

  public static void main(String[] args) {
    Readable reader = null;
    ImageModel model = new ImageModelImpl();

    // run script file
    if (args.length == 2 && args[0].equals("-file")) {
      try {
        reader = new InputStreamReader(new FileInputStream(args[1]));
      } catch (FileNotFoundException e) {
        System.out.println("File not found. Program ending.");
        return;
      }
      ImageController controller = new ImageControllerImpl(model, reader);
      controller.processImage();

      // run interactive text mode
    } else if (args.length == 1 && args[0].equals("-text")) {
      reader = new InputStreamReader(System.in);
      ImageController controller = new ImageControllerImpl(model, reader);
      controller.processImage();

      // run the GUI
    } else if (args.length == 0) {
      IView view = new JFrameView(model);
      IGUIController controller = new GUIControllerImpl(model, view);

      // quit if any other command arguments are given
    } else {
      System.out.println("Invalid command line. Program ending.");
      return;
    }
  }
}
