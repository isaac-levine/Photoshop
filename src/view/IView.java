package view;

/**
 * IView interface represents methods necessary in order to visually display our Image Processing
 * program to the user.
 */
public interface IView {

  /**
   * Updates the view with the current state of the image and the histogram that corresponds with
   * the image's color distribution and intensity values.
   */
  void refresh();

  /**
   * Displays a message to the user.
   * @param message the message to be displayed
   */
  void renderMessage(String message);

  /**
   * Opens up a file chooser for user to select the location to save an image.
   * @return the absolute path to the location
   */
  String saveFile();

  /**
   * Opens up a file chooser to user to select an image to be loaded into the program.
   * @return the absolute path to the file
   */
  String loadFile();

  /**
   * Prompts user to enter a valid integer to change the brightness of an image.
   * @return the increment to increase or decrease the brightness by
   */
  int getBrightnessIncrement();

  /**
   * Prompts user to enter a valid integer to select the number of seeds for mosaic.
   * @return the number of seeds to mosaic.
   */
  int getMosaicSeeds();
}
