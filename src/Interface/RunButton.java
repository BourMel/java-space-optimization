import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class RunButton extends JButton {

  public RunButton() {
    super();

    this.setText(">> GO <<");
    this.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        runAction();
      }
    });
  }

  public void runAction() {
    Core core = Core.getInstance();
    DrawingZone d = DrawingZone.getInstance();
    core.debug("clic sur 'Go' effectué !");
    String currentFile = core.getSvgUri();

    if (!currentFile.isEmpty()) {
      core.parse(currentFile);
      d.repaint();
      core.debug("Contenu parsé :\n" + core.getParsedContent());
    }
  }
}
