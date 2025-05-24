package eu.mikart.panoptic.config;

import de.exlll.configlib.Serializer;

import java.awt.*;

public final class ColorSerializer implements Serializer<Color, String> {

  @Override
  public String serialize(Color color) {
    if (color == null) {
      return null;
    }
    return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
  }

  @Override
  public Color deserialize(String o) {
    if (o == null) {
      return null;
    }

    String hex = o;
    if (hex.startsWith("#")) {
      hex = hex.substring(1);
    }

    return new Color(
        Integer.parseInt(hex.substring(0, 2), 16),
        Integer.parseInt(hex.substring(2, 4), 16),
        Integer.parseInt(hex.substring(4, 6), 16));
  }

}
