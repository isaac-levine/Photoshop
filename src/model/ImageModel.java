package model;

import java.util.Map;

import model.commands.ICommand;

/**
 * This interface represents the operations that can be done to images.
 * The Image Model stores multiple images - any of those images can be modified and saved as
 * new images in the model.
 */
public interface ImageModel {

  /**
   * This enum represents the types of grayscale components. The grayscale can be implemented
   * by changing the red, green, blue, value, luma, or intensity components.
   */
  enum Grayscale { Red, Green, Blue, Value, Luma, Intensity }

  /**
   * Apply a command to one of the images in the model.
   *
   * @param cmd         the command to be applied
   * @param imageName   the image to edit
   * @param destination the name of the image after editing
   * @throws IllegalArgumentException if command, image name, or destination are null, or if the
   *                                  image to apply the command to does not exist
   */
  void apply(ICommand cmd, String imageName, String destination) throws IllegalArgumentException;

  /**
   * Adds an image to the map of images in the model.
   *
   * @param imageName the name of the image
   * @param image     the image to be added
   */
  void addImage(String imageName, Image image);

  /**
   * Get a certain image from the map of images given the name.
   *
   * @param imageName the name of the image
   * @return the image that corresponds to the given name
   */
  Image getImage(String imageName);

  /**
   * Get the map of images that have been loaded into the model.
   *
   * @return a map of images and their corresponding names
   */
  Map<String, Image> getLoadedImages();


  /**
   * Generate a table of (value,frequency) entries for an image.
   * Each array of 256 represents the number of values that are stored for the respective component.
   * @param imageName the image to generate frequencies for
   * @return a 2D array of frequencies representing the RGB and intensity values
   */
  int[][] createFrequencies(String imageName);

}
