import java.util.Vector;


public class XMLDocument {

  private Vector<XMLAttribute> prolog; // le prologue xml
  private Tag tag; // tag principal

  // constructeur
  public XMLDocument(Vector<XMLAttribute> prolog, Tag tag) {
    this.prolog = prolog;
    this.tag = tag;
  }

  public Tag getTag() {
    return tag;
  }

  // retourne le document parsé au format texte
  public String toString() {
    StringBuilder r = new StringBuilder("<?xml");
    if (prolog != null) for (XMLAttribute a: prolog) r.append(a);
    r.append("?>\n");
    if (tag != null) r.append(tag);
    return r.toString();
  }

}
