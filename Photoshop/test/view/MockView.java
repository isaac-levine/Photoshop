package view;

import java.io.IOException;

/**
 * MockView class represents a mock version of our View for testing purposes
 * to make sure our controller functions properly.
 */
public class MockView implements IView {
  private final Appendable log;

  /**
   * Represents a mock view.
   * @param log appendable object
   */
  public MockView(Appendable log) {
    if (log == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.log = log;
  }

  @Override
  public void refresh() {
    // do nothing
  }

  @Override
  public void renderMessage(String message) {
    try {
      this.log.append("Message rendered: ").append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed.");
    }
  }

  @Override
  public String saveFile() {
    try {
      return this.log.append("Image saved.").toString();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed.");
    }
  }

  @Override
  public String loadFile() {
    try {
      return this.log.append("Image loaded.").toString();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed.");
    }
  }

  @Override
  public int getBrightnessIncrement() {
    return 0;
  }

  /**
   * Prompts user to enter a valid integer to select the number of seeds for mosaic.
   *
   * @return the number of seeds to mosaic.
   */
  @Override
  public int getMosaicSeeds() {
    return 0;
  }
}
