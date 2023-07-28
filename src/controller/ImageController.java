package controller;

/**
 * Represents an interface for all the operations for the Image Processing controller.
 */
public interface ImageController {

  /**
   * Runs the controller.
   * Take in user input and transmit information in an image processor.
   *
   * @throws IllegalStateException if the controller is unable to successfully
   *                               read input or transmit output.
   */
  void processImage() throws IllegalStateException;

  /**
   * Load the image file to perform operations on.
   *
   * @param imagePath path to the image
   * @param imageName name of the image
   * @throws IllegalArgumentException if image path or image name is null, or if the file to load
   *                                  does not exist
   */
  void load(String imagePath, String imageName) throws IllegalArgumentException;

  /**
   * Save the image file.
   *
   * @param imagePath path to the image
   * @param imageName name of the image
   * @throws IllegalArgumentException if image path or name is null, or if the image to save
   *                                  does not exist
   */
  void save(String imagePath, String imageName) throws IllegalArgumentException;
}

