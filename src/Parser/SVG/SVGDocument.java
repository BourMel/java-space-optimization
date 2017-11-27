public class SVGDocument {

  private XMLDocument xml;

  public SVGDocument(XMLDocument xml) {
    this.xml = xml;
  }

  public String toString() {
    return xml.toString();
  }

}
