import java.util.Vector;

public class Optimisation {

  private SVGDocument document;

  /**
   * Constructeur
   */
  public Optimisation() {
    //
  }

  /**
   * Récupère le svg à traiter et renvoie le résultat
   * @param svg le document à traiter
   * @param svg le document traité
   */
  public SVGDocument getResult(SVGDocument svg) {

    boolean first = true;
    int count;
    Vector<SVGPathCollection> groups = new Vector<SVGPathCollection>();
    document = svg;
    groups = svg.getCollections();
    double height;
    int sizeVect = groups.size();

    for(SVGPathCollection group : groups) {
      count = 0;

      height = group.getHeight();
      //place l'ensemble des chemins à gauche du document
      while(group.getBoundsX() > 0) {
        group.translate(-1, 0);
      }
      //place l'ensemble des chemins en haut du document
      while(group.getBoundsY() > 0) {
        group.translate(0, -1);
      }

      if(!first) {
        // System.out.println("i'm working here");
        //déplace vers le bas

        for(SVGPathCollection group2 : groups) {

          if(group.intersect(group2)) {


            if(count < sizeVect-1) {
              group.translate(0, group2.getHeight());

            } else {
              while(group.intersect(group2)) {
                  group.translate(0, 1);
              }
            }
            count++;

          }

          // System.out.println("intersect ?");
          // while(group.intersect(group2)) {
          //   group.translate(0, 1);
          //   System.out.println("it does ! i translate");
          // }
        }

      } else {
        first = false;
      }
    }





    return document;
  }
}
