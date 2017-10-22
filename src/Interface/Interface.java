import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;

public class Interface extends JFrame {
  static int width = 800;
  static int height = 500;
  static String titre = "Optimisation de découpe de formes";

  public Interface() {
    //mise en place de la fenêtre
    setTitle(titre);
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

    // ChooseFile importFile = new ChooseFile();
    // getContentPane().add(importFile);
  }
}
