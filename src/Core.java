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
  private double zoom = 1; // zoom level

  private SVGDocument svg = null;

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
    svg = parser.parse(url);
  }

  public String getParsedContent() {
    return (svg != null) ? svg.toString() : "";
  }

  public SVGDocument getSVG() {
    return svg;
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

  public void setZoom(double zoom) {
    i.changeZoom(zoom);
    this.zoom = zoom;
  }

  public double getZoom() {
    return zoom;
  }

  // //actualiser l'affichage
  // public void display() {
  //   DrawingZone d = DrawingZone.getInstance();
  //   d.repaint();
  //   // d.validate();
  // }

  // public void changeZoom(int level) {
  //   svg.scale(level);
  //   //appeler l'affichage
  //   display();
  // }
}
