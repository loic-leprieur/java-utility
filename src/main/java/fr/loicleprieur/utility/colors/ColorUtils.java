package fr.loicleprieur.utility.colors;
/**
 * Défini une couleur entre 0 et 255 pour les trois canaux RGB (rouge, vert, bleu)
 *
 * Permet de mélanger les canaux pour obtenir un gradient entre vert (valeur minimale),
 * jaune (valeur moyenne) et rouge (valeur maximale) à partir d'un entier représentant un pourcentage.
 */
public class Color {

  public static final Color RED = new Color(255, 0, 0);
  public static final Color YELLOW = new Color(255, 255, 0);
  public static final Color GREEN = new Color(0, 255, 0);
  private static final Color WHITE = new Color(255, 255, 255);

  private int red;
  private int green;
  private int blue;

  public Color(int r, int g, int b) {
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  public int getRed() {
    return red;
  }

  public int getGreen() {
    return green;
  }

  public int getBlue() {
    return blue;
  }

  /**
   * Cette méthode mélange deux couleurs en fonction d'un pourcentage donné.
   * Si le pourcentage est inférieur ou égal à 50%, elle mélange entre vert et jaune, sinon entre jaune et rouge.
   *
   * @param percentage le pourcentage utilisé pour le mélange
   * @return la couleur mélangée
   */
  public static Color getBlendedColor(int percentage) {
    Color targetColor = Color.WHITE;

    if (percentage <= 50) {
      // Mélange entre vert et jaune (du vert 0% au jaune 50%)
      targetColor = interpolate(Color.GREEN, Color.YELLOW, percentage / 50.0);
    } else if (percentage > 50 && percentage <= 100) {
      // Mélange entre jaune et rouge (du jaune 50% au rouge 100%)
      targetColor = interpolate(
        Color.YELLOW,
        Color.RED,
        (percentage - 50) / 50.0
      );
    }

    return targetColor;
  }

  /**
   * Cette méthode effectue l'interpolation des couleurs.
   * Elle calcule les composantes rouge, verte et bleue en fonction d'une fraction donnée.
   *
   * @param color1 la première couleur
   * @param color2 la deuxième couleur
   * @param fraction la fraction utilisée pour l'interpolation (entre 0.0 et 1.0)
   * @return la couleur interpolée
   */
  private static Color interpolate(
    Color color1,
    Color color2,
    double fraction
  ) {
    // Interpolation des composantes rouge, verte et bleue
    double r = interpolate(color1.getRed(), color2.getRed(), fraction);
    double g = interpolate(color1.getGreen(), color2.getGreen(), fraction);
    double b = interpolate(color1.getBlue(), color2.getBlue(), fraction);
    return new Color(
      (int) Math.round(r),
      (int) Math.round(g),
      (int) Math.round(b)
    );
  }

  /**
   * Cette méthode effectue l'interpolation entre deux valeurs numériques.
   *
   * @param d1 la première valeur
   * @param d2 la deuxième valeur
   * @param fraction la fraction utilisée pour l'interpolation (entre 0.0 et 1.0)
   * @return la valeur interpolée
   */
  private static double interpolate(double d1, double d2, double fraction) {
    return d1 + (d2 - d1) * fraction;
  }
}
