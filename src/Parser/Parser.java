import java.io.IOException;

class Parser {
  private SVGParser parser;

  // constructeur
  public Parser(String url) {
    parser = new SVGParser();
    parser.setUrl(url);
    try {
      parser.parse();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(" => Le fichier semble inexistant.");
    }
  }

  // on affiche le SVG sous forme de texte dans le cas où
  // l'on souhaite le sauvegarder dans un fichier
  public String toString() {
    return "" + parser;
  }
}
