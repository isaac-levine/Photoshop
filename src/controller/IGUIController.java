package controller;

import java.awt.event.ActionListener;

import model.commands.ICommand;

/**
 * Represents an interface for all the operations for the Image Processing GUI controller.
 * The GUI controller acts as an ActionListener to respond to user input.
 */
public interface IGUIController extends ActionListener {

  /**
   * Applies an image manipulation to the loaded image given a specific command.
   * Commands include component grayscale, sharpen and blur filters, sepia and greyscale color
   * transformations, and flipping horizontally and vertically.
   *
   * @param command The command ot apply to the image to edit it
   * @param name the name of the updated image
   */
  void editImage(ICommand command, String name);
}
