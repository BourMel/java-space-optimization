import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import java.awt.geom.AffineTransform;


public class SVGPath extends XMLAttribute {

  private String content;
  private int contentLength;
  private int cursor;
  private char lastChar;
  private double lastDouble;
  private Path2D path;
  private Point lastPoint;

  public SVGPath(String value) {
    super("d");
    setValue("upgraded");
    content = value.trim();
    contentLength = content.length();
    cursor = 0;
    lastDouble = 0;
    parse();

    AffineTransform at = new AffineTransform();
    at.translate(0,2490.9448*.2);
    at.scale(.2, .2);
    if (path != null) {
      path.transform(at);
    }
  }

  public void doRightMove(Path2D path, char action) {
    Point p1, p2, p3, tmp = lastPoint;
    switch(action) {
      case 'm':
        p1 = read_rel_point();
        path.moveTo(p1.getX(), p1.getY());
        break;
      case 'l':
        p1 = read_rel_point();
        path.lineTo(p1.getX(), p1.getY());
        break;
      case 'q':
        p1 = read_rel_point();
        lastPoint = tmp;
        read_separators();
        p2 = read_rel_point();
        path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        break;
      case 'c':
        p1 = read_rel_point();
        lastPoint = tmp;
        read_separators();
        p2 = read_rel_point();
        lastPoint = tmp;
        read_separators();
        p3 = read_rel_point();
        path.curveTo(
          p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY()
        );
        break;
      case 'z':
        path.closePath();
        break;
      case 'M':
        p1 = read_point();
        path.moveTo(p1.getX(), p1.getY());
        break;
      case 'L':
        p1 = read_point();
        path.lineTo(p1.getX(), p1.getY());
        break;
      case 'Q':
        p1 = read_point();
        read_separators();
        p2 = read_point();
        path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        break;
      case 'C':
        p1 = read_point();
        read_separators();
        p2 = read_point();
        read_separators();
        p3 = read_point();
        path.curveTo(
          p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY()
        );
        break;
      case 'Z':
        path.closePath();
        break;
    }
  }

  public void parse() {
    path = new Path2D.Double();
    if (!isContent()) return;
    if (!read_char_insensitive('m')) return;
    char lastAction = lastChar, tmpAction, currentChar;
    read_spaces();
    Point p = read_point();
    path.moveTo(p.getX(), p.getY());

    boolean continueToParse = true;

    while (continueToParse && cursor < contentLength) {
      read_spaces();

      currentChar = content.charAt(cursor);
      if (read_action()) {
        tmpAction = lastChar;
        read_spaces();
        doRightMove(path, tmpAction);
        lastAction = tmpAction;
      } else if (currentChar == '-'
        || (currentChar >= '0' && currentChar <= '9')) {
        if (lastAction == 'm') lastAction = 'l';
        else if (lastAction == 'M') lastAction = 'L';
        doRightMove(path, lastAction);
      } else {
        continueToParse = false;
      }
      read_spaces();
    }
  }

  // booléen pour dire s'il y a du contenu à parser ou non
  private boolean isContent() {
    return content != null && !content.isEmpty();
  }

  // on laisse le "noyau" gérer les erreurs
  private void error(String str) {
    Core core = Core.getInstance();
    core.error(str);
  }

  // on incrémente le curseur pour chaque "espace" rencontré
  private void read_spaces() {
    if (!isContent()) error("Contenu vide !");
    while (read_char(' '));
  }


  private void read_separators() {
    read_spaces();
    read_char(',');
    read_spaces();
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

  private boolean read_char_insensitive(char c) {
    return read_char(Character.toLowerCase(c))
      || read_char(Character.toUpperCase(c));
  }

  private boolean read_number() {
    return read_char('1') || read_char('2') || read_char('3') || read_char('4')
      || read_char('5') || read_char('6') || read_char('7') || read_char('8')
      || read_char('9') || read_char('0');
  }

  private boolean read_double() {
    int c = cursor; // on sauvegarde l'état du curseur
    int multiplier = 1;
    StringBuilder str = new StringBuilder();
    if (read_char('-')) multiplier = -1;
    while (read_number()) str.append(lastChar);
    if (read_char('.')) {
      str.append(lastChar);
      while (read_number()) str.append(lastChar);
    }
    if (read_char('e')) {
      str.append(lastChar);
      if (read_double()) {
        str.append((int) lastDouble);
      }
    }
    if (str.length() > 0) {
      lastDouble = Double.parseDouble(str.toString()) * multiplier;
      return true;
    } else {
      cursor = c;
      return false;
    }
  }

  private Point read_point() {
    double x, y;

    if (!read_double()) error("Un x est attendu (pos=" + cursor + ")");
    x = lastDouble;
    read_spaces();
    if (!read_char(',') && lastChar != ' ') {
      // cas particulier des svg optimisés, sans virgule s'il y a un nb négatif
      if (content.charAt(cursor) != '-') {
        error("Une virgule est attendue (pos=" + cursor + ")");
      }
    }
    read_spaces();
    if (!read_double()) error("Un y est attendu (pos=" + cursor + ")");
    y = lastDouble;

    lastPoint = new Point(x, y);
    return lastPoint;
  }

  private Point read_rel_point() {
    double x, y, xm = 0, ym = 0;

    if (lastPoint != null) { // pour modifier si coordonées relatives
      xm = lastPoint.getX();
      ym = lastPoint.getY();
    }

    Point abs = read_point();
    lastPoint = new Point(abs.getX() + xm, abs.getY() + ym);
    return lastPoint;
  }

  public boolean read_action() {
    return read_char_insensitive('m') || read_char_insensitive('l')
      || read_char_insensitive('q') || read_char_insensitive('c')
      || read_char_insensitive('z');
  }

//// NON IMPLEMENTÉ :
// H = horizontal lineto
// V = vertical lineto
// S = smooth curveto
// T = smooth quadratic Bézier curveto
// A = elliptical Arc
  public String getValue() {
    StringBuilder str = new StringBuilder();
    float[] coords = new float[6];
    for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()) {
      int type = i.currentSegment(coords);
      switch (type) {
        case PathIterator.SEG_MOVETO:
          str.append(" M ").append(coords[0]).append(",").append(coords[1]);
          break;
        case PathIterator.SEG_LINETO:
          str.append(" L ").append(coords[0]).append(",").append(coords[1]);
          break;
        case PathIterator.SEG_QUADTO:
          str.append(" Q ").append(coords[0]).append(",").append(coords[1])
            .append(" ").append(coords[2]).append(",").append(coords[3]);
          break;
        case PathIterator.SEG_CUBICTO:
          str.append(" C ").append(coords[0]).append(",").append(coords[1])
            .append(" ").append(coords[2]).append(",").append(coords[3])
            .append(" ").append(coords[4]).append(",").append(coords[5]);
          break;
        case PathIterator.SEG_CLOSE:
          str.append(" Z");
          break;
        }

    }
    return str.toString().trim();
  }

  public Path2D getPath() {
    return path;
  }
}
