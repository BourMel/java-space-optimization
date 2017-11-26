import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.WindowConstants;


public class Interface extends JFrame {

  private RunButton actionBtn;
  private JScrollPane drawingPane;
  private ChooseFileButton fileChooserBtn;
  private JScrollPane logPane;
  private JTextPane logText;
  private JLabel uriFileLabel;
  private JTextField uriFileText;

  private String interfaceTitle = "Optimisation de découpe de formes";
  private int interfaceWidth = 650;
  private int interfaceHeight = 450;

  public Interface() {
    initComponents();
  }

  private void initComponents() {
    fileChooserBtn = new ChooseFileButton();
    uriFileLabel = new JLabel();
    uriFileText = new JTextField();
    actionBtn = new RunButton();
    drawingPane = new JScrollPane();
    logPane = new JScrollPane();
    logText = new JTextPane();

    setTitle(interfaceTitle);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.orange);
    setMinimumSize(new Dimension(interfaceWidth, interfaceHeight));
    setPreferredSize(new Dimension(interfaceWidth, interfaceHeight));

    uriFileLabel.setText("URI du fichier SVG :");
    uriFileText.setFont(new Font("Cantarell", 0, 12));

    logText.setEditable(false);
    logPane.setViewportView(logText);

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
          .addComponent(actionBtn,
            GroupLayout.DEFAULT_SIZE,
            GroupLayout.DEFAULT_SIZE,
            Short.MAX_VALUE)
          .addComponent(uriFileText)
          .addComponent(fileChooserBtn,
            Alignment.LEADING,
            GroupLayout.DEFAULT_SIZE,
            200,
            Short.MAX_VALUE)
          .addComponent(uriFileLabel,
            Alignment.LEADING,
            GroupLayout.DEFAULT_SIZE,
            GroupLayout.DEFAULT_SIZE,
            Short.MAX_VALUE)
          .addComponent(logPane))
        .addPreferredGap(ComponentPlacement.RELATED)
        .addComponent(drawingPane,
          GroupLayout.DEFAULT_SIZE,
          414,
          Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(Alignment.LEADING)
          .addComponent(drawingPane)
          .addGroup(layout.createSequentialGroup()
            .addComponent(fileChooserBtn,
              GroupLayout.PREFERRED_SIZE,
              50,
              GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(uriFileLabel)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(uriFileText,
              GroupLayout.PREFERRED_SIZE,
              GroupLayout.DEFAULT_SIZE,
              GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(logPane,
              GroupLayout.DEFAULT_SIZE,
              224,
              Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(actionBtn,
              GroupLayout.PREFERRED_SIZE,
              60,
              GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );

    pack();
  }

  public void setCurrentURI(String uri) {
    uriFileText.setText(uri);
  }

  public String getCurrentURI() {
    return uriFileText.getText();
  }

}
