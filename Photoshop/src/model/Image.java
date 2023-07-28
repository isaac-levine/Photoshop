package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Represents a single image. Each image has a width, height, and is represented by a
 * 2D array of pixels.
 */
public class Image implements IImage {

  private final int width;
  private final int height;
  private final int max;
  private Pixel[][] pixels;

  /**
   * Constructs an image with its width, height, and pixels.
   *
   * @param width  the width of the image represented in number of pixels
   * @param height the height of the image represented in number of pixels
   * @param max    the max value of the image
   * @param pixels the pixels with their respective RGB components
   * @throws IllegalArgumentException if width or height are invalid, if pixels are null,
   *                                  or if RGB channels in a pixel are over the max value for
   *                                  the image
   */
  public Image(int width, int height, int max, Pixel[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null.");
    }
    if (width < 0 || height < 0 || max < 0) {
      throw new IllegalArgumentException("Image attributes cannot be negative.");
    }
    if (pixels[0].length != width || pixels.length != height) {
      throw new IllegalArgumentException(
              "Invalid width and height for the given pixels in the image.");
    }

    this.width = width;
    this.height = height;
    this.max = max;
    this.pixels = pixels;
  }

  // counts all values in a row
  @Override
  public int getWidth() {
    int width = this.width;
    return width;
  }

  // counts how many arrays
  @Override
  public int getHeight() {
    int height = this.height;
    return height;
  }

  @Override
  public int getMax() {
    int max = this.max;
    return max;
  }

  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= height || col < 0 || col >= width) {
      System.out.println("INVALID JAWN RIGHT HERE" + row + ", " + col);
      System.out.println("This image's width: " + width);
      System.out.println("This image's height: " + height);
      throw new IllegalArgumentException("Invalid row or col");
    }
    Pixel pixel = this.pixels[row][col];
    return new Pixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
  }

  @Override
  public Image greyscale(ImageModel.Grayscale component) {
    Pixel[][] newPixels = new Pixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel pixel = this.pixels[row][col];
        pixel = pixel.grayscale(component);
        newPixels[row][col] = new Pixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
      }
    }
    return new Image(this.width, this.height, this.max, newPixels);
  }

  @Override
  public Image brighten(int increment) {
    Pixel[][] newPixels = new Pixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel pixel = this.pixels[row][col];
        pixel = pixel.brighten(increment);
        newPixels[row][col] = new Pixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
      }
    }
    return new Image(this.width, this.height, this.max, newPixels);
  }

  @Override
  public Image flip(boolean direction) {
    Pixel[][] newPixels = new Pixel[height][width];
    Pixel pixel;

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        if (direction) {
          pixel = pixels[row][(this.width - 1) - col];
        } else {
          pixel = pixels[(this.height - 1) - row][col];
        }
        newPixels[row][col] = pixel;
      }
    }
    return new Image(this.width, this.height, this.max, newPixels);
  }

  @Override
  public Image mosaic(int seeds) {
    Pixel[][] newPixels = new Pixel[height][width];
    if (seeds > width * height) {
      throw new IllegalArgumentException("Seeds can not be greater than number of pixels");
    }
    if (seeds == width * height) {
      return this;
    }
    // create hashmap of random IPixel seeds and their positions
    HashMap<IPixel, Point> seedMap = this.generateSeeds(seeds);
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        // find nearest seed to this current pixel
        IPixel nearestSeed = getNearestSeed(row, col, seedMap);
        // make the pixel at this position's color the same as the nearest seed
        newPixels[row][col] = new Pixel(nearestSeed.getRed(), nearestSeed.getGreen(),
                nearestSeed.getBlue());
      }
    }
    // make an image using the new array of pixels
    Image resultImage = new Image(width, height, 255, newPixels);
    return resultImage;
  }

  // returns the nearest seed to this position
  private IPixel getNearestSeed(int curRow, int curCol, HashMap<IPixel, Point> seedMap) {
    double currentLowestDistance = Integer.MAX_VALUE;
    IPixel resultSeed = new Pixel(1,1,1);
    for (IPixel seed : seedMap.keySet()) {
      double distance = Point.distance(curRow, curCol, seedMap.get(seed).x, seedMap.get(seed).y);
      if (distance < currentLowestDistance) {
        currentLowestDistance = distance;
        resultSeed = seed;
      }
    }
    return resultSeed;
  }

  private HashMap<IPixel, Point> generateSeeds(int numSeeds) {
    Random r = new Random();

    HashMap<IPixel, Point> result = new HashMap<>();

    while (result.size() != numSeeds) {
      int row = r.nextInt(height);
      int col = r.nextInt(width);
      System.out.println("(" + row + ", " + col + ")");
      Pixel pixel = new Pixel(this.getPixelAt(row,col).getRed(),
              this.getPixelAt(row,col).getGreen(),
              this.getPixelAt(row,col).getBlue());
      result.put(pixel, new Point(row,col));
    }
    return result;
  }

  @Override
  public BufferedImage bufferedImage() {

    BufferedImage bufferedImage = new BufferedImage(
            this.getWidth(), this.getHeight(), TYPE_INT_RGB);

    for (int col = 0; col < bufferedImage.getHeight(); col++) {
      for (int row = 0; row < bufferedImage.getWidth(); row++) {
        Color color = new Color(
                this.getPixelAt(col, row).getRed(),
                this.getPixelAt(col, row).getGreen(),
                this.getPixelAt(col, row).getBlue());
        bufferedImage.setRGB(row, col, color.getRGB());
      }
    }
    return bufferedImage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Image)) {
      return false;
    }

    Image image = (Image) o;

    return Arrays.deepEquals(this.pixels, image.pixels)
            && this.width == image.width
            && this.height == image.height
            && this.max == image.max;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.max, this.height, this.width);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("P3\n"
            + width + " " + height + "\n"
            + max + "\n");
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        stringBuilder.append(this.getPixelAt(row, col).toString() + "\n");
      }
    }
    return stringBuilder.toString();
  }
}