package model.commands;

/**
 * Represents the kernel values to greyscale an image using a color transformation.
 */
public class GreyscaleCT extends ColorTransformation {

  /**
   * Constructs a 3x3 kernel which is used to greyscale an image.
   */
  public GreyscaleCT() {

    this.matrix = new double[][] {
            { 0.2126, 0.7152, 0.189},
            { 0.2126, 0.7152, 0.189},
            { 0.2126, 0.7152, 0.189 }};
  }
}
