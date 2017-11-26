import java.awt.Dimension;
import java.awt.geom.QuadCurve2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JPanel;


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
    QuadCurve2D quadcurve = new QuadCurve2D.Float(50, 300, 320, 300, 350, 500);
    gg.draw(quadcurve);
  }

  public void resizeZone(int x, int y) {
    setPreferredSize(new Dimension(x, y));
  }
}
