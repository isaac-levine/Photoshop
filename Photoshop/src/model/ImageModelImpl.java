package model;

import java.util.HashMap;
import java.util.Map;

import model.commands.ICommand;

/**
 * Represents an image model that can manipulate images.
 * An image model is represented as a hashmap that maps the name of an image to a single image.
 * The hashmap can store multiple images as more manipulations are applied.
 */
public class ImageModelImpl implements ImageModel {
  private Map<String, Image> images;

  /**
   * Constructs an initial image model with an empty map of images.
   */
  public ImageModelImpl() {
    this.images = new HashMap<>();
  }

  @Override
  public void apply(ICommand cmd, String imageName, String destination)
          throws IllegalArgumentException {
    if (cmd == null || imageName == null || destination == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }
    cmd.execute(this, imageName, destination);
  }

  @Override
  public void addImage(String imageName, Image image) {
    if (imageName == null || image == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }
    this.images.put(imageName, image);
  }

  @Override
  public Image getImage(String imageName) {
    if (imageName == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }
    return this.images.getOrDefault(imageName, null);
  }

  @Override
  public Map<String, Image> getLoadedImages() {
    return new HashMap<>(this.images);
  }

  @Override
  public int[][] createFrequencies(String imageName) {
    Image currentImage = this.getImage(imageName);

    int[][] frequencies = new int[4][256];
    for (int row = 0; row < currentImage.getHeight(); row++) {
      for (int col = 0; col < currentImage.getWidth(); col++) {
        Pixel pixel = currentImage.getPixelAt(row, col);
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        int avg = ((r + b + g) / 3);

        // red values
        frequencies[0][r] = frequencies[0][r] + 1;
        // green values
        frequencies[1][g] = frequencies[1][g] + 1;
        // blue values
        frequencies[2][b] = frequencies[2][b] + 1;
        // intensity values
        frequencies[3][avg] = frequencies[3][avg] + 1;

      }
    }
    return frequencies;
  }
}
