import java.io.IOException;

class Main {
  public static void main (String [] args) throws IOException {
    SVGParser parser = new SVGParser();
    parser.setUrl("./examples/dessin_simple.svg");
    parser.parse();
  }
}
