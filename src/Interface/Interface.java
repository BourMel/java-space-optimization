import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;

public class Interface extends JFrame {
  static int width = 800;
  static int height = 500;
  static String title = "Optimisation de découpe de formes";
  static String textButton = "Sélectionner un fichier SVG";

  public Interface() {
    //mise en place de la fenêtre
    setTitle(title);
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    //mise en place du contenant
    JPanel content = new JPanel();
    setContentPane(content);
    content.setBackground(Color.orange);

    //mise en place du contenu
    JLabel label = new JLabel("Hello world!");
    getContentPane().add(label);

    JButton showChooseFile = new ChooseFileButton(textButton);
    getContentPane().add(showChooseFile);
  }
}
