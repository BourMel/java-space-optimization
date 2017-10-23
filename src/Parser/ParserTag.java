import java.util.Vector;

class ParserTag {
  private String name;
  private Vector<ParserAttribute> attrs;
  private boolean autoClose = false;
  private Vector<ParserTag> childs;
  private String content;

  public ParserTag(String name) {
    this.name = name;
    attrs = new Vector<ParserAttribute>();
    childs = new Vector<ParserTag>();
  }

  public void addAttribute(ParserAttribute attr) {
    attrs.addElement(attr);
  }

  public void addAttribute(Vector<ParserAttribute> a) {
    for (ParserAttribute attr: a) addAttribute(attr);
  }

  public void addAttributes(Vector<ParserAttribute> a) {
    addAttribute(a);
  }

  public void addChild(ParserTag tag) {
    childs.addElement(tag);
  }

  public void setAutoClose() {
    autoClose = true;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String toString() {
    StringBuilder r = new StringBuilder("<");
    r.append(name);
    for (ParserAttribute attr: attrs) r.append(attr.toString());
    if (autoClose) {
      r.append("/>");
      return r.toString();
    }
    r.append(">").append(content);
    for (ParserTag tag: childs) r.append(tag.toString());
    r.append("</").append(name).append(">\n");
    return r.toString();
  }
}
