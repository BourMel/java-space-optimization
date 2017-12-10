import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class RunButton extends JButton {

  private boolean started = false;

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
    core.debug("clic sur le bouton effectué !");
    if (!started) {
      DrawingZone d = DrawingZone.getInstance();
      String currentFile = core.getSvgUri();

      if (!currentFile.isEmpty()) {
        started = true;
        core.parse(currentFile);
        d.repaint();
        core.disableComponents();
        core.debug("Contenu parsé :\n" + core.getParsedContent());
      }
    } else {
      core.enableComponents();
      started = false;
    }
  }
}
