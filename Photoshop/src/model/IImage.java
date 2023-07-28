package model;

import java.awt.image.BufferedImage;

/**
 * Represents a single image and its operations.
 * An image is comprised of a width, height, max value, and a sequence of pixels.
 * The width is represented by the number of pixels in a row and the height is represented
 * by the number of pixels in a column.
 * The pixels are represented by a 2D array in which each pixel has a location (row, col) in an
 * image.
 */
public interface IImage {

  /**
   * Get the width of the image in pixels.
   *
   * @return width of the image
   */
  public int getWidth();

  /**
   * Get the height of the image in pixels.
   *
   * @return height of the image
   */
  public int getHeight();

  /**
   * Get the max value of the image.
   *
   * @return max value of the image
   */
  public int getMax();

  /**
   * Get the pixel at a given coordinate of an image.
   *
   * @param row row of the pixel
   * @param col column of the pixel
   * @return a copy of the given pixel at a row and column
   */
  public Pixel getPixelAt(int row, int col);

  /**
   * Changes all the image's pixels to grayscale.
   *
   * @param component the component to manipulate the grayscale by
   * @return a copy of the image, grayscaled
   */
  public Image greyscale(ImageModel.Grayscale component);

  /**
   * Changes all the image's pixels by brighten by a positive integer or darken by a
   * negative integer.
   *
   * @param change amount to change the exposure by
   * @return a copy of the image, brightened or darkened
   */
  public Image brighten(int change);

  /**
   * Flips the image either vertically or horizontally.
   *
   * @param direction the direction to be flipped - true is horizontal, false is vertical
   * @return a copy of the image, flipped
   */
  public Image flip(boolean direction);

  /**
   * Mosaics the image according to number of seeds.
   * @param seeds the number of seeds.
   * @return a copy of the image, mosaic'd.
   */
  public Image mosaic(int seeds);

  /**
   * Converts an image to a BufferedImage.
   * @return the BufferedImage
   */
  BufferedImage bufferedImage();
}
