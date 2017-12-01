import java.util.Vector;


public class XMLTag {

  private String name;
  private Vector<Attribute> attrs;
  private boolean autoClose = false;
  private Vector<Tag> childs;
  private String content = "";
  private int deep = 0;

  private final static String TABULATION = "  ";

  public XMLTag(String name) {
    this.name = name;
    attrs = new Vector<Attribute>();
    childs = new Vector<Tag>();
  }

  public void addAttribute(Attribute attr) {
    attrs.addElement(attr);
  }

  public void addAttribute(Vector<Attribute> a) {
    for (Attribute attr: a) addAttribute(attr);
  }

  public void addAttributes(Vector<Attribute> a) {
    addAttribute(a);
  }

  public void addChild(Tag tag) {
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

  public int getDeep() {
    return deep;
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

  public Vector<Tag> getChilds() {
    return childs;
  }

  public Vector<Attribute> getAttributes() {
    return attrs;
  }

  public String toString() {
    StringBuilder r = new StringBuilder();
    if (!name.equals("svg")) r.append("\n");
    r.append(indent()).append("<").append(name);
    for (Attribute attr : attrs) r.append(attr.toString());
    if (autoClose) return r.append("/>").toString();
    r.append(">").append(content);
    for (Tag tag : childs) r.append(tag.toString());
    if (content.isEmpty() && childs.size() > 0) r.append("\n").append(indent());
    r.append("</").append(name).append(">");
    return r.toString();
  }
}
