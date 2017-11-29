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
    core.debug("clic sur 'Go' effectué !");
    String currrentFile = core.getSvgUri();

    // if (!currrentFile.isEmpty()) {
      // core.parse(currrentFile);
      core.parse("./examples/dessin_simple.svg");
      core.debug("Contenu parsé :\n" + core.getParsedContent());
    // }
  }
}
