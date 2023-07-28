package model;

/**
 * Represents a single pixel - each pixel has a color.
 * The color is represented by three integers (components): red, green, blue.
 * Any color is a combination of these three base colors.
 */
public interface IPixel {

  /**
   * Change a single pixel to grayscale given a component to change.
   * All RGB values are rounded to the nearest integer.
   *
   * @param component the type of grayscale
   * @return new pixel with grayscale applied
   */
  public Pixel grayscale(ImageModel.Grayscale component);

  /**
   * Change the exposure of a single pixel given a positive integer to brighten the image or
   * a negative integer to darken the image.
   *
   * @param change the amount to brighten or darken the image by
   * @return new pixel with the exposure changed
   */
  public Pixel brighten(int change);

  /**
   * Get the red channel value of the pixel.
   *
   * @return red channel value
   */
  public int getRed();

  /**
   * Get the green channel value of the pixel.
   *
   * @return green channel value
   */
  public int getGreen();

  /**
   * Get the blue channel value of the pixel.
   *
   * @return blue channel value
   */
  public int getBlue();
}
