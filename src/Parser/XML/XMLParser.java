import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Vector;
import java.util.regex.Pattern;


public class XMLParser {

  private String url;
  private String content;
  private int contentLength;
  private int cursor;
  private char lastChar;
  private int deep;

  private Vector<XMLAttribute> xmlProlog;
  private XMLTag svgTag; // car la balise prncipale devrait être un SVG

  // constructeurs
  public XMLParser() {
  }

  public XMLParser(String url) {
    this.url = url;
  }

  // méthode principale
  public XMLDocument parse() throws IOException {
    cursor = 0;
    contentLength = 0;
    content = null;
    fetchContent(); // on récupère le contenu
    parseXMLProlog(); // on parse le prologue XML
    read_svgTag(); // on parse tout le contenu
    return new XMLDocument(xmlProlog, svgTag);
  }

  // récupère un Stream du contenu (local ou depuis une URL d'un fichier web)
  private Stream<String> fetchStream() throws IOException {
    if (url.startsWith("http")) { // s'il s'agit d'une URL
      InputStream is = new URL(url).openConnection().getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      return reader.lines();
    } else { // sinon c'est un fichier classique
      return Files.lines(Paths.get(url));
    }
  }

  // récupère le contenu, le met en une seule ligne, et le nettoie
  private void fetchContent() throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    Stream<String> stream = fetchStream();
    stream.map(s -> s.trim())
      .filter(s -> !s.isEmpty())
      .forEach(s -> contentBuilder.append(s).append(" "));
    content = contentBuilder.toString()
      .replaceAll("(?i)" + Pattern.quote("<!DOCTYPE") + "[^>]*>", "")
      .replaceAll("  *", " ")
      .replaceAll("<!--(.*?)-->", "")
      .replaceAll("  *", " ")
      .replaceAll("> <", "><")
      .trim();
    contentLength = content.length();
  }

  // parse le prologue XML
  public void parseXMLProlog() {
    xmlProlog = new Vector<XMLAttribute>();
    XMLAttribute attr;
    if (read_string("<?xml")) {
      read_spaces();
      while ((attr = read_attribute()) != null) {
        xmlProlog.addElement(attr);
        read_spaces();
      }
      if (!read_string("?>")) error("Mauvais prologue XML.");
    }
  }

  // booléen pour dire s'il y a du contenu à parser ou non
  private boolean isContent() {
    return content != null && !content.isEmpty();
  }

  private void error(String str) {
    System.out.println("ERREUR: " + str);
    System.exit(1);
  }

  private void error() {
    error("Une erreur est survenue !");
  }

  // on incrémente le curseur pour chaque "espace" rencontré
  private void read_spaces() {
    if (!isContent()) error("Contenu vide !");
    while (cursor < contentLength && content.charAt(cursor) == ' ') cursor++;
  }

  // renvoie vrai si on a pu lire le caractère souhaité, faux sinon
  private boolean read_char(char c) {
    if (!isContent()) error("Contenu vide !");
    if (cursor < contentLength && content.charAt(cursor) == c) {
      lastChar = c;
      cursor++;
      return true;
    }
    return false;
  }

  // renvoie vrai si on a pu lire la chaîne de caractères souhaitée, faux sinon
  private boolean read_string(String str) {
    if (!isContent()) error("Contenu vide !");
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

  // version insensible à la casse
  private boolean read_string_insensitive(String str) {
    if (!isContent()) error("Contenu vide !");
    int initCursor = cursor;
    int strLen = str.length();
    if ((cursor + strLen) >= contentLength) return false;
    for (int i = 0; i < strLen; i++) {
      if (!read_char(Character.toLowerCase(str.charAt(i)))
        && !read_char(Character.toUpperCase(str.charAt(i)))) {
        cursor = initCursor;
        return false;
      }
    }
    return true;
  }

  // indique si le caractère peut appartenir à un nom d'attribut
  private boolean isCharForAttr() {
    char currentChar = content.charAt(cursor);
    return ((currentChar >= 'a' && currentChar <= 'z')
      || (currentChar >= 'A' && currentChar <= 'Z')
      || (currentChar >= '0' && currentChar <= '9')
      || currentChar == ':' || currentChar == '-'
      || currentChar == '_');
  }

  // indique si le caractère peut appartenir à la valeur d'un attribut
  private boolean isCharForAttrValue(boolean allowSpaces, char separator) {
    char currentChar = content.charAt(cursor);
    if (allowSpaces) return currentChar != separator;
    else return currentChar != ' ';
  }

  // permet de lire un attribut
  private XMLAttribute read_attribute() {
    if (!isContent()) error("Contenu vide !");
    XMLAttribute attr = null;
    StringBuilder attrName = new StringBuilder();
    while (cursor < contentLength && isCharForAttr()) {
      attrName.append(content.charAt(cursor));
      cursor++;
    }
    if (attrName.length() > 0) {
      attr = new XMLAttribute(attrName.toString().toLowerCase());
      if (read_char('=')) {
        boolean hasQuote = read_char('"') || read_char('\'');
        char separator = lastChar;
        StringBuilder attrValue = new StringBuilder();
        while (cursor < contentLength
          && isCharForAttrValue(hasQuote, separator)) {
          attrValue.append(content.charAt(cursor));
          cursor++;
        }
        if (hasQuote && !read_char(separator)) {
          error("Séparateur '" + separator + "' manquant pour un attribut.");
        }
        if (hasQuote) attr.setSeparator(separator);
        attr.setValue(attrValue.toString());
      }
    }
    return attr;
  }

  // permet de lire une chaîne de caractère entre deux balises
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

  // permet de lire le tag SVG (tout le fichier SVG est englobé dedans)
  private void read_svgTag() {
    deep = 0;
    svgTag = read_tag("svg");
    if (svgTag == null) error("Aucune balise SVG trouvée.");
  }

  // permet de lire une balise quelconque (alias sans argument)
  private XMLTag read_tag() {
    return read_tag("");
  }

  // permet de lire une balise quelconque
  private XMLTag read_tag(String tag) {
    XMLTag resTag, t;
    StringBuilder tagName = new StringBuilder();
    read_spaces();

    // une balise commence toujours par un '<'
    if (!read_char('<')) return null;
    while (cursor < contentLength && isCharForAttr()) {
      tagName.append(content.charAt(cursor++));
    }

    if (tagName.length() == 0) {
      cursor--; // décrémente le curseur car on a lu un '<' qu'il ne fallait pas
      return null;
    }

    // si on souhaitait s'attendre à un tag précis, mais que ce n'est pas le cas
    if (!tag.isEmpty()
      && !tag.toLowerCase().equals(tagName.toString().toLowerCase())) {
      error("Impossible de trouver la balise '" + tag + "'.");
    }

    // on a donc déjà un nom à notre balise !
    resTag = new XMLTag(tagName.toString().toLowerCase());
    read_spaces();

    // on récupère les attributs
    XMLAttribute attr;
    while ((attr = read_attribute()) != null) {
      resTag.addAttribute(attr);
      read_spaces();
    }
    resTag.setDeep(deep++); // on entre dans une balise, on augmente le lvl
    if (read_string("/>")) {
      resTag.setAutoClose();
      deep--; // si c'était une balise auto-fermante, on le rabaisse à nouveau
      return resTag;
    }
    if (!read_char('>')) {
      error("Caractère '>' manquant pour la balise '" + tagName + "'.");
    }

    // on lit toutes les balises enfant
    while ((t = read_tag()) != null) resTag.addChild(t);

    // si jamais le contenu de la balise était une chaîne de caratcère..
    resTag.setContent(read_tagString());

    // les petites vérifications de fin
    if (!read_string("</")) {
      error("Balise de fermeture manquante pour '" + tagName + "'.");
    }
    read_spaces();
    if (!read_string_insensitive(tagName.toString())) {
      error("Balise de fin manquante pour '" + tagName + "'.");
    }
    read_spaces();
    if (!read_char('>')) {
      error("Caractère '>' manquant pour la fermeture de '" + tagName + "'.");
    }

    deep--; // on sort d'une balise, on baisse d'un niveau hiérarchique
    return resTag;
  }

  // permet de définir une URL
  public void setUrl(String url) {
    this.url = url;
  }
}

