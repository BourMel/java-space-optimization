public class SVGDocument {

  private XMLDocument xml;

  public SVGDocument(XMLDocument xml) {
    this.xml = xml;
  }

  private void parse() {

  }

  public String toString() {
    return xml.toString();
  }

}
