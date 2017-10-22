import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class SVGParser {

  // variables
  private String url;
  private String content;

  // constructors
  public SVGParser() {
  }

  public SVGParser(String url) {
    this.url = url;
  }

  // methods
  public void parse() throws IOException {
    System.out.println("=== STARTED PARSING ===");
    fetchContent();
    System.out.println(content);
    System.out.println("==== ENDED PARSING ====");
  }

  public void fetchContent() throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    Files.lines(Paths.get(url))
         .map(s -> s.trim())
         .filter(s -> !s.isEmpty())
         .forEach(s -> contentBuilder.append(s).append(" "));
    content = contentBuilder.toString()
                            .replaceAll("<!--(.*?)-->", "")
                            .replaceAll("  ", " ")
                            .replaceAll("> <", "><");
  }

  // getters / setters
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

