package model.commands;

import model.Image;
import model.ImageModel;
import model.Pixel;

/**
 * Abstract Filter class implements ICommand in order to apply edits to an image using kernel
 * values. This class creates a kernel around each pixel in an image based on specified kernel
 * dimensions that vary among different filters. Then, each pixel value is modified accordingly
 * based off of the pixel values in the kernel.
 */
public abstract class Filter implements ICommand {

  protected double[][] kernel;

  @Override
  public void execute(ImageModel model, String name, String destination)
          throws IllegalArgumentException {
    Image image = model.getImage(name);
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    Image newImage;

    Pixel kernelPix;
    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];

    int length = kernel.length;
    int kernelStart = (int) (length - Math.floor(length + (length / 2)));
    int kernelEnd = (int) Math.floor(length / 2);


    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {

        int r = 0;
        int g = 0;
        int b = 0;

        // creates a kernel around every pixel with an appropriate size
        for (int i = kernelStart; i <= kernelEnd; i++) {
          for (int j = kernelStart; j <= kernelEnd; j++) {

            // get pixel and check for values that are out of bounds of the image
            try {
              kernelPix = image.getPixelAt(row + i, col + j);
            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
              kernelPix = new Pixel(0, 0, 0);
            }

            double pixelKernelValue = kernel[i + length / 2][j + length / 2];

            // create relative sums
            r += kernelPix.getRed() * pixelKernelValue;
            g += kernelPix.getGreen() * pixelKernelValue;
            b += kernelPix.getBlue() * pixelKernelValue;
          }
        }
        // clamp values and set new pixel
        r = Math.max(0, Math.min(image.getMax(), r));
        g = Math.max(0, Math.min(image.getMax(), g));
        b = Math.max(0, Math.min(image.getMax(), b));
        newPixels[row][col] = new Pixel(r, g, b);
      }
    }
    System.out.println("Filter applied.");
    newImage = new Image(image.getWidth(), image.getHeight(), image.getMax(), newPixels);
    model.addImage(destination, newImage);
  }
}


