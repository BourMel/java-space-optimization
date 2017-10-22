import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

class SVGParser {

  // variables
  private String url;
  private String content;
  private int contentLength;
  private int cursor;
  private char lastChar;

  private Vector<ParserAttribute> xmlTag;

  // constructors
  public SVGParser() {
  }

  public SVGParser(String url) {
    this.url = url;
  }

  // methods
  public void parse() throws IOException {
    System.out.println("=== STARTED PARSING ===");
    cursor = 0;
    fetchContent();
    parseXMLTag();
    System.out.println(content);
    System.out.println("==== ENDED PARSING ====");
    for (ParserAttribute a: xmlTag) {
      System.out.println(a);
    }
  }

  public void fetchContent() throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    Files.lines(Paths.get(url))
         .map(s -> s.trim())
         .filter(s -> !s.isEmpty())
         .forEach(s -> contentBuilder.append(s).append(" "));
    content = contentBuilder.toString()
                            .replaceAll("<!--(.*?)-->", "")
                            .replaceAll("  *", " ")
                            .replaceAll("> <", "><")
                            .trim();
    contentLength = content.length();
  }

  public void parseXMLTag() {
    xmlTag = new Vector<ParserAttribute>();
    ParserAttribute attr;
    if (read_string("<?xml")) {
      read_spaces();
      while ((attr = read_attribute()) != null) {
        xmlTag.addElement(attr);
        read_spaces();
      }
      if (!read_string("?>")) error("Bad XML prolog.");
    }
  }

  private boolean isContent() {
    return content != null && !content.isEmpty();
  }

  private void error(String str) {
    System.out.println("ERROR: " + str);
    System.exit(1);
  }

  private void error() {
    error("An error occured!");
  }

  // on incrémente le curseur pour chaque "espace" rencontré
  private void read_spaces() {
    if (!isContent()) error("No content!");
    while (cursor < contentLength
      && (content.charAt(cursor) == ' '
      || content.charAt(cursor) == ' ')) cursor++;
  }

  private boolean read_char(char c) {
    if (!isContent()) error("No content!");
    if (cursor < contentLength && content.charAt(cursor) == c) {
      lastChar = c;
      cursor++;
      return true;
    }
    return false;
  }

  private boolean read_string(String str) {
    if (!isContent()) error("No content!");
    int initCursor = cursor;
    int strLen = str.length();
    if ((cursor + strLen) >= contentLength) return false;
    for (int i = 0; i < strLen; i++) {
      if (!read_char(str.charAt(i))) {
        cursor = initCursor;
        return false;
      }
    }
    return true;
  }

  private boolean isCharForAttr() {
    char currentChar = content.charAt(cursor);
    return ((currentChar >= 'a' && currentChar <= 'z')
      || (currentChar >= 'A' && currentChar <= 'Z')
      || (currentChar >= '0' && currentChar <= '9')
      || currentChar == ':' || currentChar == '-');
  }

  private boolean isCharForAttrValue(boolean allowSpaces, char separator) {
    char currentChar = content.charAt(cursor);
    if (allowSpaces) return currentChar != separator;
    else return currentChar != ' ' && currentChar != ' ';
  }

  private ParserAttribute read_attribute() {
    if (!isContent()) error("No content!");
    ParserAttribute attr = null;
    StringBuilder attrName = new StringBuilder();
    while (cursor < contentLength && isCharForAttr()) {
      attrName.append(content.charAt(cursor));
      cursor++;
    }
    if (attrName.length() > 0) {
      attr = new ParserAttribute(attrName.toString());
      if (read_char('=')) {
        boolean hasQuote = read_char('"') || read_char('\'');
        char separator = lastChar;
        StringBuilder attrValue = new StringBuilder();
        while (cursor < contentLength && isCharForAttrValue(hasQuote, separator)) {
          attrValue.append(content.charAt(cursor));
          cursor++;
        }
        if (hasQuote && !read_char(separator)) {
          error("Missing '" + separator + "' for one attribute.");
        }
        if (hasQuote) attr.setSeparator(separator);
        attr.setValue(attrValue.toString());
      }
    }
    return attr;
  }

  // getters / setters
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

