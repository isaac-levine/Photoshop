package model.commands;

import model.Image;
import model.ImageModel;

/**
 * Represents the Flip command.
 */
public class Flip implements ICommand {
  private boolean direction;

  /**
   * Constructs a Flip command with a direction to flip the image.
   *
   * @param direction direction to flip the image - if true, horizontal, if false, vertical
   */
  public Flip(boolean direction) {
    this.direction = direction;
  }

  @Override
  public void execute(ImageModel model, String name, String destination)
          throws IllegalArgumentException {
    Image image = model.getImage(name);
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    Image copy = image.flip(direction);
    System.out.println("Flip applied.");
    model.addImage(destination, copy);
  }
}
