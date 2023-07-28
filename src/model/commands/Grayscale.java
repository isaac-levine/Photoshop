package model.commands;

import model.Image;
import model.ImageModel;

/**
 * Represents the Grayscale command.
 */
public class Grayscale implements ICommand {
  private ImageModel.Grayscale component;

  /**
   * Constructs a Grayscale command with a given component to change.
   *
   * @param component the type of grayscale to apply
   */
  public Grayscale(ImageModel.Grayscale component) {
    this.component = component;
  }

  @Override
  public void execute(ImageModel model, String name, String destination)
          throws IllegalArgumentException {
    Image image = model.getImage(name);
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    Image copy = image.greyscale(component);
    System.out.println(component + " grayscale applied.");
    model.addImage(destination, copy);
  }
}
