public class SVGDocument {

  private XMLDocument xml;
  private Core core;

  public SVGDocument(XMLDocument xml) {
    this.xml = xml;
    core = Core.getInstance();
  }

  private void parse() {
    XMLTag tag = basicChecks();
    // @TODO
  }

  // effectue les vérifications de base
  private XMLTag basicChecks() {
    XMLTag tag;
    if (xml == null) core.error("Aucun fichier XML à parser...");
    tag = xml.getTag();
    if (tag == null) core.error("le fichier est vide...");
    if (!tag.getName().toLowerCase().equals("svg")) {
      core.error("La première balise d'un fichier SVG doit être 'svg'");
    }
    return tag;
  }

  public String toString() {
    return xml.toString();
  }

}
