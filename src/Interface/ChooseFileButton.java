import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;


public class ChooseFileButton extends JButton {

  public ChooseFileButton() {
    super();

    this.setText("Ouvrir un fichier SVG");
    this.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        chooseFileAction();
      }
    });
  }

  public void chooseFileAction() {
    File choosedFile;
    String choosedFileName;
    Core core = Core.getInstance();

    core.debug("Instanciation de la boîte de dialogue");

    JFileChooser importFile = new JFileChooser();
    importFile.showOpenDialog(null); // affiche
    choosedFile = importFile.getSelectedFile();

    if (choosedFile != null) {
      choosedFileName = choosedFile.toString();
      core.debug("Fichier choisi : " + choosedFileName);
      core.setSvgUri(choosedFileName);
    }
  }
}
