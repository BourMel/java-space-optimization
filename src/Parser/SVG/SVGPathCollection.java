import java.util.Vector;


// groupe de paths
public class SVGPathCollection {

  private Vector<SVGPath> paths;

  // construit une nouvelle collection vide
  public SVGPathCollection() {
    paths = new Vector<SVGPath>();
  }

  // construit une nouvelle collection avec un path
  public SVGPathCollection(SVGPath path) {
    paths = new Vector<SVGPath>();
    addPath(path);
  }

  // retourne la liste des paths de la collection
  public Vector<SVGPath> getPaths() {
    return paths;
  }

  // ajoute un path à la collection
  public void addPath(SVGPath path) {
    if (path != null) {
      paths.add(path);
    }
  }

  // permet de fusionner une autre collection avec celle-ci
  public void merge(SVGPathCollection c) {
    for (SVGPath path : c.getPaths()) {
      addPath(path);
    }
  }

}
