package model.commands;

import model.Image;
import model.ImageModel;

/**
 * Represents the Brighten command that can brighten or darken an image.
 */
public class Brighten implements ICommand {
  private int increment;

  /**
   * Constructs a Brighten command with the given increment.
   *
   * @param increment amount to brighten an image by if a positive integer or darken an image
   *                  if given a negative integer
   */
  public Brighten(int increment) {
    this.increment = increment;
  }

  @Override
  public void execute(ImageModel model, String name, String destination)
          throws IllegalArgumentException {
    Image image = model.getImage(name);
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    Image copy = image.brighten(increment);
    System.out.println("Brightness " + increment + " applied.");
    model.addImage(destination, copy);
  }
}
