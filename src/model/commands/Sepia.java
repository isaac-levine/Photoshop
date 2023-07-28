package model.commands;


/**
 * Represents the kernel values to apply a Sepia filter to an image.
 */
public class Sepia extends ColorTransformation {

  /**
   * Constructs a 3x3 matrix which is used to apply a Sepia filter using color transformation.
   */
  public Sepia() {
    this.matrix = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
  }
}
