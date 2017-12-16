import java.util.Vector;

public class Optimisation {

  private SVGDocument document;

  /**
   * Constructeur
   */
  public Optimisation() {
System.out.println("Hello world !");
  }

  /**
   * Récupère le svg à traiter et renvoie le résultat
   * @param svg le document à traiter
   * @param svg le document traité
   */
  public SVGDocument getResult(SVGDocument svg) {
System.out.println("LETS GOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

    Vector<SVGPathCollection> groups = new Vector<SVGPathCollection>();
    document = svg;
    groups = svg.getCollections();

    for(SVGPathCollection group : groups) {

System.out.println("GROUP *********************");
System.out.println(group.getBoundsX());

      while(group.getBoundsX() > 0) {
        group.translate(-1, -1);
      }

System.out.println(group.getBoundsX());
System.out.println("****************************");
    }

    return document;
  }
}
