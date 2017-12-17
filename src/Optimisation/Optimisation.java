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
    int count = 0;
    Vector<SVGPathCollection> groups = new Vector<SVGPathCollection>();
    document = svg;
    groups = svg.getCollections();
    double height;
    double heightBefore = 0;
    int sizeVect = groups.size();
    SVGPathCollection lastGroup = groups.get(0);

    //pour chaque groupe
    for(SVGPathCollection group : groups) {
      //indique le groupe actif
      count++;

      height = group.getHeight();
      //place l'ensemble des chemins à gauche du document
      while(group.getBoundsX() > 0) {
        group.translate(-1, 0);
      }
      //place l'ensemble des chemins en haut du document
      while(group.getBoundsY() > 0) {
        group.translate(0, -1);
      }

      //on laisse le premier à son emplacement
      if(count > 1) {
        //pour chaque groupe < au courant, on le décale de la hauteur des précédents
        group.translate(0, heightBefore);
      }

      heightBefore += group.getHeight();
      lastGroup = group;
      }

    return document;
  }
}
