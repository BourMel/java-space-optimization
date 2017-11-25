// Classe (singleton threadsafe) qui contient un peu toutes les ressources

class Core {
  private boolean printDebug = true;
  private Parser parser;
  private static Core instance;

  private Core() {
    // initialisation du parseur
    parser = new Parser();
  }

  public static Core getInstance() {
    if (instance == null) { // meilleures perfs
      synchronized (Core.class) {
        if (instance == null) {
          instance = new Core();
        }
      }
    }
    return instance;
  }

  public void parse(String url) {
    parser.parse(url);
  }

  public String getParsedContent() {
    return parser.toString();
  }

  public void debug(String msg) {
    if (printDebug) {
      System.out.println("DEBUG: " + msg);
    }
  }
}
