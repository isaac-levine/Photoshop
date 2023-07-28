package model.commands;

/**
 * Represents the kernel values to sharpen an image.
 */
public class Sharpen extends Filter {

  /**
   * Constructs a 5x5 kernel which is used to sharpen the image using a filter.
   */
  public Sharpen() {
    this.kernel = new double[][] {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1.0, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}};
  }
}