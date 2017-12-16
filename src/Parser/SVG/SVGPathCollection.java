import java.util.Vector;
import java.awt.geom.Area;

/**
 * Groupe de paths (collection)
 */
public class SVGPathCollection {

  private Vector<SVGPath> paths;

  /**
   * Constructeur
   * Ici, collection vide
   */
  public SVGPathCollection() {
    paths = new Vector<SVGPath>();
  }

  /**
   * Constructeur
   * Ici, collection avec un path
   * @param chemin à ajouter au groupe
   */
  public SVGPathCollection(SVGPath path) {
    paths = new Vector<SVGPath>();
    addPath(path);
  }

  /**
   * Retourne la liste des paths de la collection
   * @return vecteur de paths
   */
  public Vector<SVGPath> getPaths() {
    return paths;
  }

  /**
   * Ajoute un path à la collection
   * @param chemin à ajouter au groupe
   */
  public void addPath(SVGPath path) {
    if (path != null) {
      paths.add(path);
    }
  }

  /**
   * Ajouter une collection de paths à celle-ci pour n'en former qu'une
   * @param vecteur de paths à ajouter
   */
  public void merge(SVGPathCollection c) {
    for (SVGPath path : c.getPaths()) {
      addPath(path);
    }
  }

  /**
   * Retourne la collection de path sous forme de chaîne de caractères
   * @return chaine de caractères représentant le groupe
   */
  public String toString() {
    StringBuilder r = new StringBuilder();
    if (paths.size() == 0) return r.toString();
    r.append("  <g>\n");
    for (SVGPath path : paths) {
      r.append("    <path").append(path).append("/>\n");
    }
    r.append("  </g>\n");
    return r.toString();
  }

  /**
   * Translation d'un groupe de chemin
   */
  public void translate(double xTransform, double yTransform) {
    for (SVGPath path : paths) {
      path.translateX(xTransform);
      path.translateY(yTransform);
    }
  }

  /**
   * Récupère le point le plus à gauche du groupe
   * @return valeur x la plus faible
   */
   public double getBoundsX() {
     double minX = 9999999;

     for(SVGPath path : paths) {
       if(path.getPath().getBounds().x < minX) {
         minX = path.getPath().getBounds().x;
       }
     }

     return minX;
   }

  /**
   * Récupère le point le plus haut du groupe
   * @return valeur y la plus faible
   */
   public double getBoundsY() {
     double minY = 9999999;

     for(SVGPath path : paths) {
       if(path.getPath().getBounds().y < minY) {
         minY = path.getPath().getBounds().y;
       }
     }

     return minY;
   }

   /**
    * Récupère la hauteur du groupe
    * @result double
    */
    public double getHeight() {
      Area total = new Area();
      Area one;

      for(SVGPath path : paths) {
        one = new Area(path.getPath());
        total.add(one);
      }
      
      return total.getBounds().height;
    }


   /**
    * Détecte l'intersection d'un des chemins du groupe avec un autre
    * @return boolean
    */
    public boolean intersect(SVGPathCollection c) {
      Vector<SVGPath> others = c.getPaths();
      Area otherA, pathA;

      for(SVGPath other : others) {
        for(SVGPath path : paths) {
          otherA = new Area(other.getPath());
          pathA = new Area(path.getPath());

          otherA.intersect(pathA);
          return !otherA.isEmpty();
        }
      }
      return false;
    }

  // /**
  //  * Mise à l'échelle d'un groupe de chemin
  //  */
  // public void scale(double pathScale) {
  //   for (SVGPath path : paths) {
  //     path.scale(pathScale);
  //   }
  // }
}
