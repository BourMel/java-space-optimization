import java.util.Vector;

class ParserTag {
  private String name;
  private Vector<ParserAttribute> attrs;
  private boolean autoClose = false;
  private Vector<ParserTag> childs;
  private String content;
  private int deep = 0;

  private final static String TABULATION = "  ";

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

  public void setDeep(int deep) {
    this.deep = deep;
  }

  // permet d'indenter
  private String indent() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < deep; i++) s.append(TABULATION);
    return s.toString();
  }

  public String toString() {
    StringBuilder r = new StringBuilder();
    if (!name.equals("svg")) r.append("\n");
    r.append(indent()).append("<").append(name);
    for (ParserAttribute attr: attrs) r.append(attr.toString());
    if (autoClose) return r.append("/>").toString();
    r.append(">").append(content);
    for (ParserTag tag: childs) r.append(tag.toString());
    if (content.isEmpty()) r.append("\n").append(indent());
    r.append("</").append(name).append(">");
    return r.toString();
  }
}
