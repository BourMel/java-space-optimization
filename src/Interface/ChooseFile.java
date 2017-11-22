import javax.swing.JFileChooser;

public class ChooseFile {
  public ChooseFile () {
    System.out.println("Instanciation de la boîte de dialogue");

    JFileChooser importFile = new JFileChooser();
    importFile.showOpenDialog(null); //affiche
    System.out.println(importFile.getSelectedFile());
  }
}
