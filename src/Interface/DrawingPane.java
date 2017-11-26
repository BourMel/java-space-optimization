import java.awt.Dimension;
import java.awt.geom.QuadCurve2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;

public class DrawingPane extends JScrollPane {

  public DrawingPane() {
    super();

    setHorizontalScrollBarPolicy(
      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
    );
    setVerticalScrollBarPolicy(
      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
    );
    setViewportBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D gg = (Graphics2D) g;
    QuadCurve2D quadcurve = new QuadCurve2D.Float(50, 300, 320, 300, 350, 500);
    gg.draw(quadcurve);
    // updateSize(40000, 500);
  }

  public void updateSize(int x, int y) {
    JScrollBar yBar = this.getVerticalScrollBar();
    yBar.setPreferredSize(new Dimension(x, 10));
  }
}
