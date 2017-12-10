import java.io.IOException;


class Parser {
  private XMLParser parser;
  private XMLDocument xml = null;
  private SVGDocument svg;

  // constructeur
  public Parser(String url) {
    parser = new XMLParser();
    parse(url);
  }

  public Parser() {
    parser = new XMLParser();
  }

  public SVGDocument parse(String url) {
    parser.setUrl(url);
    try {
      xml = parser.parse();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(" => Le fichier semble inexistant.");
      System.exit(1);
    }
    svg = new SVGDocument(xml);
    return svg;
  }

  // on affiche le SVG sous forme de texte dans le cas où
  // l'on souhaite le sauvegarder dans un fichier
  public String toString() {
    return parser.toString();
  }

  public SVGDocument scale(int zoom) {
    svg = new SVGDocument(xml);
    svg.scale(zoom);
    return svg;
  }
}
