package model.commands;

/**
 * Represents the kernel values to blur an image.
 */

public class Blur extends Filter {

  /**
   * Constructs a 3x3 kernel which is used to blur the image using a filter.
   */
  public Blur() {
    this.kernel = new double[][] {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}};
  }
}
