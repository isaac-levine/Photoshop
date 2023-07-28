package model.commands;

import model.ImageModel;

/**
 * Represents an interface for all the commands to edit an image.
 * When called, information is delegated to the image class to operate on the image, which
 * delegates to the pixel class to manipulate each pixel in the image.
 */
public interface ICommand {

  /**
   * The method to execute all the commands to an image that is stored in the model.
   * @param model the model that stores all the images that are loaded
   * @param name the name of the image to edit
   * @param destination the name of the new image
   */
  void execute(ImageModel model, String name, String destination) throws IllegalArgumentException;

}
