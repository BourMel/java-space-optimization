import java.io.IOException;


class Parser {
  private XMLParser parser;

  // constructeur
  public Parser(String url) {
    parser = new XMLParser();
    parse(url);
  }

  public Parser() {
    parser = new XMLParser();
  }

  public SVGDocument parse(String url) {
    SVGDocument svg;
    XMLDocument xml = null;
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
}
