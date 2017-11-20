import java.io.IOException;

class Parser {
  public Parser() throws IOException {
    SVGParser parser = new SVGParser();
    parser.setUrl("./examples/dessin_simple.svg");
    parser.parse();
  }
}
