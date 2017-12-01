// attribute container

public class Attribute {
  private XMLAttribute inner;

  public Attribute(String name) {
    inner = new XMLAttribute(name);
  }

  public Attribute(String name, String value) {
    inner = new XMLAttribute(name, value);
  }

  public void setValue(String value) {
    inner.setValue(value);
  }

  public void setSeparator(char separator) {
    inner.setSeparator(separator);
  }

  public String getName() {
    return inner.getName();
  }

  public String getValue() {
    return inner.getValue();
  }

  public String toString() {
    return inner.toString();
  }
}
