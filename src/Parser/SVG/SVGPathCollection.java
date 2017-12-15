import java.util.Vector;

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

  // /**
  //  * Translation d'un groupe de chemin
  //  */
  // public void translate(double xTransform, double yTransform) {
  //   for (SVGPath path : paths) {
  //     path.translateX(xTransform);
  //     path.translateY(yTransform);
  //   }
  // }

  // /**
  //  * Mise à l'échelle d'un groupe de chemin
  //  */
  // public void scale(double pathScale) {
  //   for (SVGPath path : paths) {
  //     path.scale(pathScale);
  //   }
  // }
}
