import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class RunButton extends JButton {

  private boolean started = false;

  /**
   * Constructeur du bouton permettant de lancer le parseur
   */
  public RunButton() {
    super();

    this.setText(">> GO <<");
    this.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        runAction();
      }
    });
  }

  /**
   * Définit l'action au clic
   * Ici, lancer le parseur et remplir la fenêtre avec le SVG
   * Ou au contraire, permettre de réactiver les composants de la fenêtre
   */
  public void runAction() {
    Core core = Core.getInstance();
    core.debug("clic sur le bouton effectué !");
    if (!started) {
      DrawingZone d = DrawingZone.getInstance();
      String currentFile = core.getSvgUri();

      if (!currentFile.isEmpty()) {
        core.log("Lancement...");
        started = true;
        core.parse(currentFile);
        d.repaint();
        core.disableComponents();
        core.log("parsing OK");
        core.debug("Contenu parsé :\n" + core.getParsedContent());
      }
    } else {
      core.log("Arrêt.");
      core.enableComponents();
      started = false;
    }
  }
}
