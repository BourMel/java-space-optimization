import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

// Classe (singleton threadsafe) qui contient un peu toutes les ressources
class Core {
  private static Core instance;
  private boolean printDebug = true;
  private Parser parser;
  private Interface i;

  // @TODO: remove
  private XMLDocument xmldoc = null;

  private Core() {
    // initialisation du parseur
    parser = new Parser();

    // initialisation de l'UI
    changeLookDefaultUI();
    EventQueue.invokeLater(() -> {
      i = new Interface();
      i.setVisible(true);
    });
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
    xmldoc = parser.parse(url);
  }

  public String getParsedContent() {
    return (xmldoc != null) ? xmldoc.toString() : "";
  }

  public void startDebug() {
    printDebug = true;
  }

  public void stopDebug() {
    printDebug = false;
  }

  public void debug(String msg) {
    if (printDebug) {
      System.out.println("DEBUG: " + msg);
    }
  }

  public void error(String msg) {
    System.out.println("ERROR: " + msg);
    System.exit(1);
  }

  public void setSvgUri(String uri) {
    i.setCurrentURI(uri);
  }

  public String getSvgUri() {
    return i.getCurrentURI();
  }

  private void changeLookDefaultUI() {
    try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException
      | InstantiationException
      | IllegalAccessException
      | UnsupportedLookAndFeelException ex) {
      error("Impossible de définir un look pour l'UI. Arrêt.");
    }
  }
}
