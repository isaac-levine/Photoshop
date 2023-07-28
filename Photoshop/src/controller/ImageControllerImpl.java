package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.commands.Blur;
import model.commands.Brighten;
import model.commands.Flip;
import model.commands.Grayscale;
import model.Image;
import model.ImageModel;
import model.Pixel;
import model.commands.GreyscaleCT;
import model.commands.Mosaic;
import model.commands.Sepia;
import model.commands.Sharpen;

/**
 * Implements the operations for the image processing controller including
 * saving and loading images.
 */
public class ImageControllerImpl implements ImageController {
  private final ImageModel model;
  private final Readable input;

  /**
   * Constructs a controller given a model to delegate the operations to based on user input.
   *
   * @param model the model to perform operations
   * @param input the user input
   * @throws IllegalArgumentException if model or input is null
   */
  public ImageControllerImpl(ImageModel model, Readable input)
          throws IllegalArgumentException {
    if (model == null || input == null) {
      throw new IllegalArgumentException("Cannot be null.");
    }
    this.model = model;
    this.input = input;
  }

  @Override
  public void processImage() throws IllegalStateException {

    Scanner sc = new Scanner(this.input);

    while (sc.hasNext()) {
      String input = sc.next();

      if ((input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))) {
        System.out.println("Image processor quit.");
        return;
      }

      try {
        switch (input) {
          case "load":
            load(sc.next(), sc.next());
            break;
          case "save":
            save(sc.next(), sc.next());
            break;
          case "red-grayscale":
            model.apply(new Grayscale(ImageModel.Grayscale.Red), sc.next(), sc.next());
            break;
          case "green-grayscale":
            model.apply(new Grayscale(ImageModel.Grayscale.Green), sc.next(), sc.next());
            break;
          case "blue-grayscale":
            model.apply(new Grayscale(ImageModel.Grayscale.Blue), sc.next(), sc.next());
            break;
          case "value-grayscale":
            model.apply(new Grayscale(ImageModel.Grayscale.Value), sc.next(), sc.next());
            break;
          case "luma-grayscale":
            model.apply(new Grayscale(ImageModel.Grayscale.Luma), sc.next(), sc.next());
            break;
          case "intensity-grayscale":
            model.apply(new Grayscale(ImageModel.Grayscale.Intensity), sc.next(), sc.next());
            break;
          case "horizontal-flip":
            model.apply(new Flip(true), sc.next(), sc.next());
            break;
          case "vertical-flip":
            model.apply(new Flip(false), sc.next(), sc.next());
            break;
          case "brighten":
          case "darken":
            model.apply(new Brighten(Integer.parseInt(sc.next())), sc.next(), sc.next());
            break;
          case "greyscaleCT":
            model.apply(new GreyscaleCT(), sc.next(), sc.next());
            break;
          case "sepia":
            model.apply(new Sepia(), sc.next(), sc.next());
            break;
          case "blur":
            model.apply(new Blur(), sc.next(), sc.next());
            break;
          case "sharpen":
            model.apply(new Sharpen(), sc.next(), sc.next());
            break;
          case "mosaic":
            model.apply(new Mosaic(Integer.parseInt(sc.next())), sc.next(), sc.next());
            break;
          default:
            System.out.println("Not a valid command");
        }
        // handle errors and give user feedback
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Override
  public void load(String imagePath, String imageName) throws IllegalArgumentException {
    if (imagePath == null || imageName == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }

    // if it's a ppm, then use ppm helper
    if (imagePath.endsWith(".ppm")) {
      this.readPPM(imagePath, imageName);

      // if it is in a different format
    } else {
      BufferedImage image;
      try {
        image = ImageIO.read(new FileInputStream(imagePath));
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not read file.");
      }

      int width = image.getWidth();
      System.out.println("Width of image: " + width);
      int height = image.getHeight();
      System.out.println("Height of image: " + height);
      int maxValue = 0;

      Pixel[][] pixels = new Pixel[height][width];
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          Color color = new Color(image.getRGB(col, row));
          int r = color.getRed();
          int g = color.getGreen();
          int b = color.getBlue();

          // get the local max
          int maxForPixel = Math.max(r, Math.max(g, b));
          // if the local max is greater than current max, replace
          if (maxForPixel > maxValue) {
            maxValue = maxForPixel;
          }

          pixels[row][col] = new Pixel(r, g, b);
        }
      }
      System.out.println("Max value of image: " + maxValue);
      this.model.addImage(imageName, new Image(width, height, maxValue, pixels));
      System.out.println("Image loaded.");
    }
  }

  // loads a PPM file
  private void readPPM(String imagePath, String imageName) throws IllegalArgumentException {
    Scanner sc = null;

    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found.");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    // get all the values in the image
    int width = Integer.parseInt(sc.next());
    System.out.println("Width of image: " + width);
    int height = Integer.parseInt(sc.next());
    System.out.println("Height of image: " + height);
    int maxValue = Integer.parseInt(sc.next());
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    Pixel[][] image = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        image[i][j] = new Pixel(r, g, b);
      }
    }
    this.model.addImage(imageName, new Image(width, height, maxValue, image));
    System.out.println("Image loaded.");
  }

  @Override
  public void save(String imagePath, String imageName) throws IllegalArgumentException {
    if (imagePath == null || imageName == null) {
      throw new IllegalArgumentException("Values cannot be null.");
    }

    Image imageToSave = this.model.getImage(imageName);
    if (imageToSave == null) {
      throw new IllegalArgumentException("Image to save does not exist.");
    }

    if (imagePath.endsWith(".ppm")) {
      this.savePPM(imagePath, imageToSave);
      System.out.println("Image saved.");
    } else {

      File file = new File(imagePath);
      BufferedImage image = new BufferedImage(
              imageToSave.getWidth(), imageToSave.getHeight(), BufferedImage.TYPE_INT_RGB);

      for (int row = 0; row < imageToSave.getHeight(); row++) {
        for (int col = 0; col < imageToSave.getWidth(); col++) {
          int r = imageToSave.getPixelAt(row, col).getRed();
          int g = imageToSave.getPixelAt(row, col).getGreen();
          int b = imageToSave.getPixelAt(row, col).getBlue();
          Color color = new Color(r, g, b);
          image.setRGB(col, row, color.getRGB());
        }
      }

      String formatName = imagePath.substring(imagePath.length() - 3);
      try {
        ImageIO.write(image, formatName, new FileOutputStream(file));
        System.out.println("Image saved.");
      } catch (IOException e) {
        throw new IllegalArgumentException("Can't save file.");
      }
    }
  }


  // saves a PPM file to a file location
  private void savePPM(String imagePath, Image image) {
    StringBuilder stringBuilder = new StringBuilder();
    int width = image.getWidth();
    int height = image.getHeight();

    stringBuilder.append("P3\n"
            + width + " " + height + "\n"
            + image.getMax() + "\n");
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        stringBuilder.append(image.getPixelAt(row, col).toString() + "\n");
      }
    }

    try {
      FileWriter output = new FileWriter(imagePath);
      output.write(stringBuilder.toString());
      output.close();
    } catch (IOException e) {
      System.out.println("Can't save file");
    }
  }
}


