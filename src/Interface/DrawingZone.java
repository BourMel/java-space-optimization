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


public class DrawingZone extends JPanel {

  private static DrawingZone instance;
  private JPanel drawingZone;
  private SVGDocument svg;

  private DrawingZone() {
    super();
    setBackground(Color.white);
    resizeZone(800, 600);
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

    Path2D path = new Path2D.Double();

    boolean isFirst = true;
    for (int i = 0; i < Math.round(Math.random() * 200) + 1; i++) {
      double x = Math.random() * 800;
      double y = Math.random() * 800;

      if (isFirst) {
        path.moveTo(x, y);
        isFirst = false;
      } else {
        path.lineTo(x, y);
      }
    }

    path.closePath();

    gg.draw(path);

    System.out.println("-------------------------------------------");
    Core core = Core.getInstance();
    SVGDocument svgCore = core.getSVG();
    if (svgCore != svg) {
      svg = svgCore;
      System.out.println(svg.toLightString());
    }
  }

  public void resizeZone(int x, int y) {
    setPreferredSize(new Dimension(x, y));
  }
}
