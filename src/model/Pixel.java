package model;

import java.util.Objects;

import model.ImageModel.Grayscale;

/**
 * Represents a single pixel and its operations.
 */
public class Pixel implements IPixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a pixel with its RGB channel values.
   *
   * @param red   the red channel value
   * @param green the green channel value
   * @param blue  the blue channel value
   * @throws IllegalArgumentException if channel value is not positive
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("Channel value must be positive.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public Pixel grayscale(Grayscale component) {
    Pixel pixel;

    switch (component) {
      case Red:
        pixel = new Pixel(this.red, this.red, this.red);
        break;
      case Green:
        pixel = new Pixel(this.green, this.green, this.green);
        break;
      case Blue:
        pixel = new Pixel(this.blue, this.blue, this.blue);
        break;
      case Value:
        int max = Math.max(this.red, Math.max(this.green, this.blue));
        pixel = new Pixel(max, max, max);
        break;
      case Luma:
        int avg = (this.red + this.green + this.blue) / 3;
        pixel = new Pixel(avg, avg, avg);
        break;
      case Intensity:
        int num = (int) ((0.2126 * this.red) + (0.7152 * this.green) + (0.0722 * this.blue));
        pixel = new Pixel(num, num, num);
        break;
      default:
        pixel = new Pixel(this.red, this.green, this.blue);
        break;
    }
    return pixel;
  }

  @Override
  public Pixel brighten(int increment) {
    int r = this.red;
    int g = this.green;
    int b = this.blue;

    // brighten and clamp at 255
    if (increment > 0) {
      r = Math.min(255, this.red + increment);
      g = Math.min(255, this.green + increment);
      b = Math.min(255, this.blue + increment);
      // darken and clamp at 0
    } else if (increment < 0) {
      r = Math.max(0, this.red + increment);
      g = Math.max(0, this.green + increment);
      b = Math.max(0, this.blue + increment);
      // do nothing if increment is 0
    } else {
      r = this.red;
      g = this.green;
      b = this.blue;
    }
    return new Pixel(r, g, b);
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public String toString() {
    return red + " " + green + " " + blue;
  }

  @Override
  public boolean equals(Object pixel) {
    if (this == pixel) {
      return true;
    }

    if (!(pixel instanceof Pixel)) {
      return false;
    }

    Pixel other = (Pixel) pixel;

    return this.red == other.red
            && this.green == other.green
            && this.blue == other.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }
}

