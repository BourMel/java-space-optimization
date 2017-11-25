import javax.swing.JFileChooser;
import java.io.File;

public class ChooseFile {
  public ChooseFile () {
    File choosedFile;
    String choosedFileName;
    Core core = Core.getInstance();

    core.debug("Instanciation de la boîte de dialogue");

    JFileChooser importFile = new JFileChooser();
    importFile.showOpenDialog(null); //affiche
    choosedFile = importFile.getSelectedFile();
    choosedFileName = choosedFile.toString();

    core.debug("Fichier choisi : " + choosedFileName);

    if (choosedFile != null) {
      core.parse(choosedFileName);
      core.debug("Contenu parsé :\n" + core.getParsedContent());
    }
  }
}
