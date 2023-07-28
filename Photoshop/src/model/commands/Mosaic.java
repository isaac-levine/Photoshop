package model.commands;

import model.Image;
import model.ImageModel;

/**
 * Represents the Mosaic command.
 */
public class Mosaic implements ICommand {
  int seeds;

  /**
   * Creates a new Mosaic command.
   * @param seeds the number of seeds represented by an int.
   */
  public Mosaic(int seeds) {
    if (seeds < 1) {
      throw new IllegalArgumentException("Need at least 1 seed");
    }
    this.seeds = seeds;
  }


  /**
   * The method to execute all the commands to an image that is stored in the model.
   *
   * @param model       the model that stores all the images that are loaded
   * @param name        the name of the image to edit
   * @param destination the name of the new image
   */
  @Override
  public void execute(ImageModel model, String name, String destination)
          throws IllegalArgumentException {
    Image image = model.getImage(name);
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    Image copy = image.mosaic(seeds);
    System.out.println("Mosaic applied with " + seeds + " seeds.");
    model.addImage(destination, copy);
  }
}
