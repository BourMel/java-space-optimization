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
   * @param maxHeight la hauteur du rouleau
   * @return svg le document traité
   */
  public SVGDocument getResult(SVGDocument svg, double maxHeight) throws HeightException {
    Vector<SVGPathCollection> groups = new Vector<SVGPathCollection>();
    groups = svg.getCollections();

    boolean first = true;
    int count = 0; //tests
    double height;
    double heightBefore = 0;
    double widthBefore = 0;
    double colWidth = 0;
    int sizeVect = groups.size();
    SVGPathCollection lastGroup = groups.get(0);

    if (maxHeight <= 0) throw new HeightException("La hauteur doit être strictement positive !");
    for (SVGPathCollection group : groups) {
      // si l'un des groupes ne rentre pas dans la taille indiquée
      if (group.getHeight() > maxHeight) {
        throw new HeightException();
      }
    }

    //pour chaque groupe
    for(SVGPathCollection group : groups) {

      count++;
      System.out.println("**************************");
      System.out.println("count");
      System.out.println(count);

      height = group.getHeight();
      System.out.println("sa hauteur :");
      System.out.println(height);

      //place l'ensemble des chemins à gauche du document
      while(group.getBoundsX() > 0) {
        group.translate(-1, 0);
      }
      //place l'ensemble des chemins en haut du document
      while(group.getBoundsY() > 0) {
        group.translate(0, -1);
      }

      //définit s'il s'agit du premier groupe d'une colonne
      first = (heightBefore == 0) || (heightBefore + height > maxHeight);
      System.out.println("premier chemin ?");
      System.out.println(first);


      System.out.println("largeur courante");
      System.out.println(colWidth);

      if(first) {
        heightBefore = 0;
      }

      System.out.println("translation  de largeur et hauteur avant");
      System.out.println("hauteur avant");
      System.out.println(heightBefore);
      System.out.println("largeur avant");
      System.out.println(widthBefore);
      group.translate(0, heightBefore);

      lastGroup = group;

      if(first) {
        heightBefore = height;
        widthBefore += colWidth;
        colWidth = group.getWidth();
      } else {
        heightBefore += height;
      }
      group.translate(widthBefore, 0);

      System.out.println("hauteur avant :");
      System.out.println(heightBefore);
      System.out.println("largeur avant");
      System.out.println(widthBefore);

      // if(first) { //1er de chaque col
      //   System.out.println("premier d'une colonne");
      //   heightBefore = height;
      //   colWidth = group.getWidth();
      //   widthBefore += group.getWidth();
      //   first = false;
      //
      // } else {
      //   //changement de colonne
      //   if(heightBefore + group.getHeight() > maxHeight) {
      //     System.out.println("décalage de largeur");
      //     // group.translate((widthBefore - colWidth), 0);
      //     first = true;
      //   }
      //
      //   //widthBefore
      //   group.translate(0, heightBefore);
      //   //se rapproche du groupe précédent le plus possible
      //   while(!group.intersect(lastGroup)) {
      //     System.out.println("- 5 de hauteur");
      //     group.translate(0, -5);
      //   }
      //   while(group.intersect(lastGroup)) {
      //     System.out.println("+1 de hauteur");
      //     group.translate(0, 1);
      //   }
      // }
      //
      // lastGroup = group;
      // heightBefore += group.getHeight();
      // }
}
    return svg;
  }
}
