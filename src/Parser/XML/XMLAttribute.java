public class XMLAttribute {

  private String name;
  private String value;
  private char separator = '\0'; // ' or "

  // un attribut doit avoir au moins un nom
  public XMLAttribute(String name) {
    this.name = name;
  }

  public XMLAttribute(String name, String value) {
    this.name = name;
    this.value = value;
    if (value.contains("\"")) separator = '\'';
    else separator = '"';
  }

  // dans le cas où l'on souhaite modifier la valeur d'un attribut
  public void setValue(String value) {
    this.value = value;
  }

  public void setSeparator(char separator) {
    this.separator = separator;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String toString() {
    if (value.isEmpty()) return " " + name;
    return " " + name + "=" + separator + value + separator;
  }
}
