import java.util.Vector;


public class SVGDocument {

  private XMLDocument xml;
  private Core core;
  private Vector<SVGPathCollection> collections;

  public SVGDocument(XMLDocument xml) {
    collections = new Vector<SVGPathCollection>();
    this.xml = xml;
    core = Core.getInstance();
    parse();
  }

  private void parse() {
    XMLTag tag = basicChecks();
    core.debug("Le fichier semble OK.");
    Vector<XMLTag> childs = tag.getChilds();

    // si la balise SVG est vide, on a fini.
    if (childs.size() == 0) {
      core.debug("la balise SVG est vide.");
      return;
    }

    // si le premier 'g' est un calque principal, on l'ignore
    if (countTag(childs, "path") == 0 && countTag(childs, "g") == 1) {
      core.debug("le fichier SVG est composé d'un calque principal");
      XMLTag layer = firstTagWithName(childs, "g");
      if (layer == null) core.error("une erreur est survenue pour trouver <g>");
      else parseRightLevel(layer.getChilds());
    } else {
      core.debug("le fichier SVG n'est pas composé d'un calque principal");
      parseRightLevel(childs);
    }
  }

  // effectue les vérifications de base
  private XMLTag basicChecks() {
    XMLTag tag;
    if (xml == null) core.error("Aucun fichier XML à parser...");
    tag = xml.getTag();
    if (tag == null) core.error("le fichier est vide...");
    if (!tag.getLowerName().equals("svg")) {
      core.error("La première balise d'un fichier SVG doit être 'svg'");
    }
    return tag;
  }

  // compte le nombre de fois qu'apparaît une balise dans le même niveau
  private int countTag(Vector<XMLTag> tags, String name) {
    int count = 0;
    for (XMLTag tag : tags) {
      if (tag.getLowerName().equals(name)) count++;
    }
    return count;
  }

  // retourne le premier tag de ce type trouvé
  private XMLTag firstTagWithName(Vector<XMLTag> tags, String name) {
    for (XMLTag tag : tags) {
      if (tag.getLowerName().equals(name)) {
        return tag;
      }
    }
    return null;
  }

  private void parseRightLevel(Vector<XMLTag> tags) {
    for (XMLTag tag : tags) {
      if (tag.getLowerName().equals("path")) {
        core.debug("on traite une balise path");
        if (tag.getChilds().size() != 0) {
          core.debug("une balise path ne doit pas avoir de fils; on zappe.");
        } else {
          collections.add(parsePath(tag));
        }
      } else if (tag.getLowerName().equals("g")) {
        core.debug("on traite une balise g");
        if (tag.getChilds().size() == 0) {
          core.debug("...mais on ne fait rien puisqu'elle est vide");
        } else {
          parseFirstLevel(tag.getChilds());
        }
      }
    }
  }

  private SVGPathCollection parsePath(XMLTag tag) {
    SVGPathCollection c = new SVGPathCollection();
    if (!tag.getLowerName().equals("path")) return c;
    core.debug("  --on traite un path");
    if (tag.getChilds().size() != 0) {
      core.debug(" !! une balise path ne doit pas avoir de fils; on ignore.");
    }

    return c;
  }

  private SVGPathCollection parseFirstLevel(Vector<XMLTag> tags) {
    SVGPathCollection c = new SVGPathCollection();
    for (XMLTag tag : tags) {
      if (tag.getLowerName().equals("path")) {
        c.merge(parsePath(tag));
      }
    }
    return c;
  }

  public String toString() {
    return xml.toString();
  }

}
