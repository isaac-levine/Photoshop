package controller;

import java.awt.event.ActionEvent;
import java.io.StringReader;

import model.ImageModel;
import model.commands.Blur;
import model.commands.Brighten;
import model.commands.Flip;
import model.commands.Grayscale;
import model.commands.GreyscaleCT;
import model.commands.ICommand;
import model.commands.Mosaic;
import model.commands.Sepia;
import model.commands.Sharpen;
import view.IView;

/**
 * Implements the operations for the image processing GUI controller by applying image
 * manipulations in response to user input.
 * Delegates to the standard image processing controller for saving and loading images.
 */
public class GUIControllerImpl implements IGUIController {

  private final ImageModel model;
  private final IView view;
  private final ImageController delegate;

  /**
   * Constructs a GUI Controller that initializes the model, view, and delegate controller
   * to perform operations to based on user input.
   * @param model the model to perform operations
   * @param view the view to be presented to the user
   * @throws IllegalArgumentException if the model or view are null
   */
  public GUIControllerImpl(ImageModel model, IView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }
    this.model = model;
    this.view = view;
    this.delegate = new ImageControllerImpl(this.model, new StringReader(""));
  }

  /**
   * Events that are invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
        case "load":
          String filePath = view.loadFile();
          if (!filePath.isEmpty()) {
            delegate.load(filePath, "editedImage");
            view.refresh();
          }
          break;
        case "save":
          String saveFile = view.saveFile();
          if (!saveFile.isEmpty() && (saveFile.endsWith("jpg") || saveFile.endsWith("png") ||
                  saveFile.endsWith("ppm") || saveFile.endsWith("bmp"))) {
            delegate.save(saveFile, "editedImage");
            view.refresh();
          } else {
            this.view.renderMessage("Must specify image type as .jpg, .png, .ppm, or .bmp");
          }
          break;
        case "red-grayscale":
          this.editImage(new Grayscale(ImageModel.Grayscale.Red), "editedImage");
          break;
        case "green-grayscale":
          this.editImage(new Grayscale(ImageModel.Grayscale.Green), "editedImage");
          break;
        case "blue-grayscale":
          this.editImage(new Grayscale(ImageModel.Grayscale.Blue), "editedImage");
          break;
        case "value-grayscale":
          this.editImage(new Grayscale(ImageModel.Grayscale.Value), "editedImage");
          break;
        case "luma-grayscale":
          this.editImage(new Grayscale(ImageModel.Grayscale.Luma), "editedImage");
          break;
        case "intensity-grayscale":
          this.editImage(new Grayscale(ImageModel.Grayscale.Intensity), "editedImage");
          break;
        case "horizontal-flip":
          this.editImage(new Flip(true), "editedImage");
          break;
        case "vertical-flip":
          this.editImage(new Flip(false), "editedImage");
          break;
        case "brightness":
          this.editImage(new Brighten(view.getBrightnessIncrement()), "editedImage");
          break;
        case "mosaic":
          this.editImage(new Mosaic(view.getMosaicSeeds()), "editedImage");
          break;
        case "greyscaleCT":
          this.editImage(new GreyscaleCT(), "editedImage");
          break;
        case "sepia":
          this.editImage(new Sepia(), "editedImage");
          break;
        case "sharpen":
          this.editImage(new Sharpen(), "editedImage");
          break;
        case "blur":
          this.editImage(new Blur(), "editedImage");
          break;
        default:
      }
    } catch (Exception exception) {
      this.view.renderMessage(exception.getMessage());
    }
  }

  @Override
  public void editImage(ICommand command, String name) {
    if (command == null || name == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }
    try {
      command.execute(model, name, "editedImage");
      this.view.refresh();
      view.renderMessage("Filter applied");
    } catch (NullPointerException e) {
      view.renderMessage("Must load image before performing actions.");
    }
  }
}
