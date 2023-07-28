package model.commands;

import model.Image;
import model.ImageModel;
import model.Pixel;

/**
 * ColorTransformation class represents the abstract operation in order to apply a color
 * transformation using a matrix of integers that modify each red green and blue component
 * of each pixel.
 */
public abstract class ColorTransformation implements ICommand {

  protected double[][] matrix;

  @Override
  public void execute(ImageModel model, String name, String destination)
          throws IllegalArgumentException {
    Image image = model.getImage(name);
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    Image newImage;

    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];
    Pixel pixel;
    int[] rgb = new int[3];

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        pixel = image.getPixelAt(row, col);
        for (int i = 0; i < matrix.length; i++) {
          int r = (int) (pixel.getRed() * matrix[i][0]);
          int g = (int) (pixel.getGreen() * matrix[i][1]);
          int b = (int) (pixel.getBlue() * matrix[i][2]);

          // clamp values
          rgb[i] = Math.max(0, Math.min(image.getMax(), r + g + b));
        }
        newPixels[row][col] = new Pixel(rgb[0], rgb[1], rgb[2]);
      }
    }
    newImage = new Image(image.getWidth(), image.getHeight(), image.getMax(), newPixels);
    model.addImage(destination, newImage);
    System.out.println("Color transformation applied");
  }
}