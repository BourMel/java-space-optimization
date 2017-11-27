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

  public XMLDocument parse(String url) {
    XMLDocument doc = null;
    parser.setUrl(url);
    try {
      doc = parser.parse();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(" => Le fichier semble inexistant.");
    }
    return doc;
  }

  // on affiche le SVG sous forme de texte dans le cas où
  // l'on souhaite le sauvegarder dans un fichier
  public String toString() {
    return "" + parser;
  }
}
