import java.awt.Dimension;
import java.awt.geom.QuadCurve2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Path2D;


public class DrawingZone extends JPanel {

  private JPanel drawingZone;

  public DrawingZone() {
    super();
    setBackground(Color.white);
    resizeZone(800, 600);
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D gg = (Graphics2D) g;
    // QuadCurve2D quadcurve = new QuadCurve2D.Float(50, 300, 320, 300, 350, 500);
    // gg.draw(quadcurve);
    Path2D path = new Path2D.Double();

    boolean isFirst = true;
    for (int i = 0; i < Math.round(Math.random() * 200); i++) {
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
  }

  public void resizeZone(int x, int y) {
    setPreferredSize(new Dimension(x, y));
  }
}
