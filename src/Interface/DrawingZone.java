import java.awt.Dimension;
import java.awt.geom.QuadCurve2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Path2D;

import java.awt.BasicStroke;
import java.awt.RenderingHints;

import java.util.Vector;


public class DrawingZone extends JPanel {

  private static DrawingZone instance;
  private JPanel drawingZone;
  private SVGDocument svg;
  private Vector<SVGPathCollection> collections;

  private DrawingZone() {
    super();
    setBackground(Color.white);
    resizeZone(8000, 6000);
  }

  public static DrawingZone getInstance() {
    if (instance == null) {
      synchronized (Core.class) {
        if (instance == null) {
          instance = new DrawingZone();
        }
      }
    }
    return instance;
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D gg = (Graphics2D) g;

    // on améliore les traits (on les rends plus arrondis)
    gg.setStroke(
      new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
    );

    // on active l'antialiasing
    gg.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );

    Core core = Core.getInstance();
    SVGDocument svgCore = core.getSVG();
    if (svgCore != null) {
      svg = svgCore;
      collections = svg.getCollections();
      for (SVGPathCollection c : collections) {
        for (SVGPath p : c.getPaths()) {
          gg.draw(p.getPath());
        }
      }
    }
  }

  public void resizeZone(int x, int y) {
    setPreferredSize(new Dimension(x, y));
  }
}
