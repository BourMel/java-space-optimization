import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Vector;

class SVGParser {

  // variables
  private String url;
  private String content;
  private int contentLength;
  private int cursor;
  private char lastChar;

  private Vector<ParserAttribute> xmlTag;
  private ParserTag svgTag;

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
    read_svgTag();
    System.out.println("==== ENDED PARSING ====");
    for (ParserAttribute a: xmlTag) {
      System.out.println(a);
    }
    System.out.println(" => cursor = " + cursor);

    System.out.println("======= RESULTS: ======");
    System.out.println(svgTag);
  }

  private Stream<String> fetchStream() throws IOException {
    if (url.startsWith("http")) {
      InputStream is = new URL(url).openConnection().getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      return reader.lines();
    } else {
      return Files.lines(Paths.get(url));
    }
  }

  private void fetchContent() throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    Stream<String> stream = fetchStream();
    stream.map(s -> s.trim())
          .filter(s -> !s.isEmpty())
          .forEach(s -> contentBuilder.append(s).append(" "));
    content = contentBuilder.toString()
                            .replaceAll("  *", " ")
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
    while (cursor < contentLength && content.charAt(cursor) == ' ') cursor++;
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
    else return currentChar != ' ';
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
      attr = new ParserAttribute(attrName.toString().toLowerCase());
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

  private void read_svgTag() {
    svgTag = read_tag("svg");
    if (svgTag == null) error("No SVG tag found.");
  }

  private ParserTag read_tag() {
    return read_tag("");
  }

  private String read_tagString() {
    read_spaces();
    lastChar = content.charAt(cursor++);
    StringBuilder s = new StringBuilder();
    while (cursor < contentLength && lastChar != '<') {
      s.append(lastChar);
      lastChar = content.charAt(cursor++);
    }
    if (lastChar == '<') cursor--;
    return s.toString().trim();
  }

  private ParserTag read_tag(String tag) {
    ParserTag resTag, t;
    StringBuilder tagName = new StringBuilder();
    read_spaces();

    if (!read_char('<')) return null;
    if (tag.isEmpty()) {
      while (cursor < contentLength && isCharForAttr()) {
        tagName.append(content.charAt(cursor));
        cursor++;
      }
      if (tagName.length() == 0) {
        cursor--; // on décrémente le curseur car on a lu un '<' qu'il ne fallait pas
        return null;
      }
    } else {
      if (!read_string(tag)) error("Cannot find " + tag + " tag.");
      tagName.append(tag.toLowerCase());
    }
    resTag = new ParserTag(tagName.toString());

    read_spaces();

    ParserAttribute attr;
    while ((attr = read_attribute()) != null) {
      resTag.addAttribute(attr);
      read_spaces();
    }
    if (read_string("/>")) {
      resTag.setAutoClose();
      return resTag;
    }
    if (!read_char('>')) error("Missing '>' character for " + tagName + " tag.");
    while ((t = read_tag()) != null) {
      resTag.addChild(t);
    }
    resTag.setContent(read_tagString());
    if (!read_string("</")) error("Missing end tag for " + tagName + " tag.");
    read_spaces();
    if (!read_string(tagName.toString())) error("Missing end tag for " + tagName + " tag.");
    read_spaces();
    if (!read_char('>')) error("Missing '>' character for closing " + tagName + " tag.");
    return resTag;
  }

  // getters / setters
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // @TODO: extern this in an other object.
  public String toString() {
    StringBuilder r = new StringBuilder("<?xml ");
    for (ParserAttribute a: xmlTag) r.append(a);
    r.append("?>\n");
    return r.toString();
  }
}

