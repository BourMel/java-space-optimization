import java.util.Vector;


public class XMLTag {

  private String name;
  private Vector<XMLAttribute> attrs;
  private boolean autoClose = false;
  private Vector<XMLTag> childs;
  private String content;
  private int deep = 0;

  private final static String TABULATION = "  ";

  public XMLTag(String name) {
    this.name = name;
    attrs = new Vector<XMLAttribute>();
    childs = new Vector<XMLTag>();
  }

  public void addAttribute(XMLAttribute attr) {
    attrs.addElement(attr);
  }

  public void addAttribute(Vector<XMLAttribute> a) {
    for (XMLAttribute attr: a) addAttribute(attr);
  }

  public void addAttributes(Vector<XMLAttribute> a) {
    addAttribute(a);
  }

  public void addChild(XMLTag tag) {
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

  public String getName() {
    return name;
  }

  public String getLowerName() {
    return getName().toLowerCase();
  }

  public Vector<XMLTag> getChilds() {
    return childs;
  }

  public String toString() {
    StringBuilder r = new StringBuilder();
    if (!name.equals("svg")) r.append("\n");
    r.append(indent()).append("<").append(name);
    for (XMLAttribute attr: attrs) r.append(attr.toString());
    if (autoClose) return r.append("/>").toString();
    r.append(">").append(content);
    for (XMLTag tag: childs) r.append(tag.toString());
    if (content.isEmpty()) r.append("\n").append(indent());
    r.append("</").append(name).append(">");
    return r.toString();
  }
}
