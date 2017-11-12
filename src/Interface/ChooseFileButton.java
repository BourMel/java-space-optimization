import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChooseFileButton extends JButton implements MouseListener {
  private String name;

  public ChooseFileButton(String name) {
    super(name);
    this.name = name;

    this.addMouseListener(this);
  }

  public void mouseClicked(MouseEvent event) {
    ChooseFile importFile = new ChooseFile();
  }

  public void mouseEntered(MouseEvent event) {}

  public void mouseExited(MouseEvent event) {}

  public void mousePressed(MouseEvent event) {}

  public void mouseReleased(MouseEvent event) {}
}
