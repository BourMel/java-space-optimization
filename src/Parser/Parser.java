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

  public void parse(String url) {
    Core core = Core.getInstance();
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
