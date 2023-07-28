package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;






import javax.swing.filechooser.FileNameExtensionFilter;

import controller.GUIControllerImpl;
import controller.IGUIController;
import model.Image;
import model.ImageModel;

/**
 * Represents a GUI view, using Java Swing to display the image processing program.
 */
public class JFrameView extends JFrame implements IView {
  private final ImageModel model;
  private JScrollPane imageScrollPane;
  private JPanel commandPanel;
  private IGUIController controller;
  private JLabel imageLabel;
  private JLabel histogramLabel;

  /**
   * Constructs a GUI view, and initializes the panels graphically.
   * @param model the Image Processing model to be used
   */
  public JFrameView(ImageModel model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;

    this.setTitle("Image Processing");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    this.createCommand();
    this.createLoadSave();
    this.createImage();
    this.createHistogram();

    setVisible(true);
    pack();
  }

  // adds the commands as buttons on the left side of the view
  private void createCommand() {
    controller = new GUIControllerImpl(model, this);
    commandPanel = new JPanel();
    commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
    commandPanel.setBorder(BorderFactory.createTitledBorder("Commands"));

    JButton grayscaleRed = new JButton();
    JButton grayscaleGreen = new JButton();
    JButton grayscaleBlue = new JButton();
    JButton grayscaleValue = new JButton();
    JButton grayscaleLuma = new JButton();
    JButton grayscaleIntensity = new JButton();
    JButton horizontalFlip = new JButton();
    JButton verticalFlip = new JButton();
    JButton brightness = new JButton();
    JButton grayscaleFilter = new JButton();
    JButton sepiaFilter = new JButton();
    JButton sharpen = new JButton();
    JButton blur = new JButton();
    JButton mosaic = new JButton();
    this.setButton(grayscaleRed, "Red Grayscale", "red-grayscale", controller);
    this.setButton(grayscaleGreen,
            "Green Grayscale", "green-grayscale", controller);
    this.setButton(grayscaleBlue, "Blue Grayscale", "blue-grayscale", controller);
    this.setButton(grayscaleValue,
            "Value Grayscale", "value-grayscale", controller);
    this.setButton(grayscaleLuma, "Luma Grayscale", "luma-grayscale", controller);
    this.setButton(grayscaleIntensity,
            "Intensity Grayscale", "intensity-grayscale", controller);
    this.setButton(horizontalFlip,
            "Horizontal Flip", "horizontal-flip", controller);
    this.setButton(verticalFlip, "Vertical Flip", "vertical-flip", controller);
    this.setButton(brightness, "Brightness", "brightness", controller);
    this.setButton(grayscaleFilter,
            "Greyscale Filter", "greyscaleCT", controller);
    this.setButton(sepiaFilter, "Sepia Filter", "sepia", controller);
    this.setButton(sharpen, "Sharpen", "sharpen", controller);
    this.setButton(blur, "Blur", "blur", controller);
    this.setButton(mosaic, "Mosaic", "mosaic", controller);

    this.add(commandPanel, BorderLayout.WEST);
  }

  // sets up each command button with an action command, and adds it to the command panel
  private void setButton(JButton button, String buttonName, String action,
                         IGUIController controller) {
    button = new JButton(buttonName);
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setActionCommand(action);
    button.addActionListener(controller);
    commandPanel.add(button);
  }

  // adds the load and save buttons on the top the view
  private void createLoadSave() {
    controller = new GUIControllerImpl(model, this);
    JPanel saveLoadPanel = new JPanel();
    saveLoadPanel.setLayout(new FlowLayout());

    JButton loadButton = new JButton("Load Image");
    loadButton.setActionCommand("load");
    loadButton.addActionListener(controller);
    saveLoadPanel.add(loadButton);

    JButton saveButton = new JButton("Save Image");
    saveButton.setActionCommand("save");
    saveButton.addActionListener(controller);
    saveLoadPanel.add(saveButton);

    this.add(saveLoadPanel, BorderLayout.NORTH);
  }

  // adds the scrollable image panel to the center of the view
  private void createImage() {

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Current Image"));
    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imagePanel.add(imageScrollPane);
    imageScrollPane.setPreferredSize(new Dimension(600, 500));
    this.add(imagePanel, BorderLayout.CENTER);
  }

  // adds the histogram panel to the right side of the view
  private void createHistogram() {
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Image Histogram"));

    histogramLabel = new JLabel();
    JScrollPane histogramScrollable = new JScrollPane(histogramLabel);
    histogramScrollable.setPreferredSize(new Dimension(255, 350));
    histogramPanel.add(histogramScrollable);
    this.add(histogramPanel, BorderLayout.EAST);
  }

  // creates a histogram to visualize the distribution of color and intensities in an image
  // formatted as a BufferedImage
  private BufferedImage drawHistogram() {
    Image image = this.model.getImage("editedImage");
    int[][] frequencies = this.model.createFrequencies("editedImage");

    int max = 1;

    for (int[] frequency : frequencies) {
      for (int j : frequency) {
        if (j > max) {
          max = j;
        }
      }
    }

    int scale = max / 350;


    BufferedImage bufferedImage =
            new BufferedImage(image.getMax(), 350, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    // red lines (red component)
    this.drawBars(graphics, new Color(255, 0, 0, 75), frequencies[0], scale);
    // green lines (green component)
    this.drawBars(graphics, new Color(0, 255, 0, 75), frequencies[1], scale);
    // blue lines (blue component)
    this.drawBars(graphics, new Color(0, 0, 255, 75), frequencies[2], scale);
    // black lines (intensity)
    this.drawBars(graphics, new Color(0, 0, 0, 75), frequencies[3], scale);
    graphics.drawImage(bufferedImage, null, 0, 0);
    return bufferedImage;
  }

  // draws lines for each of color components as well as intensity values
  private void drawBars(Graphics2D bar, Color pixelColor, int[] frequencies, int scale) {
    bar.setColor(pixelColor);
    for (int i = 0; i < 256; i++) {
      bar.drawLine(i, 350, i, Math.min(350, 350 - frequencies[i] / scale));

    }
  }

  // displays the histogram in the histogram panel in the view
  private void displayHistogram(BufferedImage histogram) {
    ImageIcon displayHistogram = new ImageIcon(histogram);
    this.histogramLabel.setIcon(displayHistogram);
  }

  @Override
  public void refresh() {
    Image image = model.getImage("editedImage");
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    ImageIcon icon = new ImageIcon(image.bufferedImage());
    ImageIcon scaled = icon;

    // scale the image to the side of the image panel
    if (icon.getIconWidth() < 500 || icon.getIconHeight() < 400) {
      double ratioW = (double) imageScrollPane.getWidth() / (double) icon.getIconWidth();
      double ratioH = (double) imageScrollPane.getHeight() / (double) icon.getIconHeight();
      double ratio = Math.min(ratioW, ratioH);

      Dimension dimension = new Dimension((int) (icon.getIconWidth() * ratio),
              (int) (icon.getIconHeight() * ratio));

      java.awt.Image iconImage = icon.getImage();
      scaled = new ImageIcon(iconImage.getScaledInstance(dimension.width, dimension.height,
              java.awt.Image.SCALE_SMOOTH));
    }

    this.imageLabel.setIcon(scaled);
    this.displayHistogram(this.drawHistogram());
    this.pack();
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message,
            "Message", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public String saveFile() {
    final JFileChooser fileChooser = new JFileChooser();
    int show = fileChooser.showSaveDialog(null);

    // if the user selects a location to save
    if (show == JFileChooser.APPROVE_OPTION) {
      File selectedImage = fileChooser.getSelectedFile();
      return selectedImage.getAbsolutePath();
    }
    return "";
  }

  @Override
  public String loadFile() {
    JFileChooser fileChooser = new JFileChooser();
    // show only files of images that have the extensions this program supports
    fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Image File Types", "ppm", "jpg", "png", "bmp"));

    // if the user selects a file
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      File selectedImage = fileChooser.getSelectedFile();
      return selectedImage.getAbsolutePath();
    } else {
      return "";
    }
  }

  @Override
  public int getBrightnessIncrement() {
    String input = JOptionPane.showInputDialog("Enter brightness increment: ");
    if (input != null) {
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        this.renderMessage("Must input a valid integer.");
      }
    }
    return 0;
  }

  /**
   * Prompts user to enter a valid integer to select the number of seeds for mosaic.
   *
   * @return the number of seeds to mosaic.
   */
  @Override
  public int getMosaicSeeds() {
    String input = JOptionPane.showInputDialog("Enter # of mosaic seeds: ");
    if (input != null) {
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        this.renderMessage("Must input a valid integer.");
      }
    }
    return 0;
  }
}
