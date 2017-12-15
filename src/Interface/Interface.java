import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;


public class Interface extends JFrame {

  private RunButton actionBtn;
  private DrawingPane drawingPane;
  private ChooseFileButton fileChooserBtn;
  private JScrollPane logPane;
  private JTextPane logText;
  private JLabel uriFileLabel;
  private JTextField uriFileText;
  private JLabel zoomLabel;
  private JSlider zoom;
  private SliderListener zoomListener;
  private JLabel leftLabel;
  private JSlider left;
  private JLabel topLabel;
  private JSlider top;

  private final String INTERFACE_TITLE = "Optimisation de découpe de formes";
  private final int INTERFACE_WIDTH = 650;
  private final int INTERFACE_HEIGHT = 450;
  private final String URI_FILE_LABEL = "URI du fichier SVG :";
  private final String ZOOM_LABEL = "Niveau de zoom";
  private final String LEFT_LABEL = "Décalage à gauche";
  private final String TOP_LABEL = "Décalage en haut";

  /**
   * Constructeur de l'interface utilisateur dans son ensemble
   */
  public Interface() {
    initComponents();
  }

  /**
   * Choix du niveau de zoom par l'utilisateur
   * @param zoom dont la valeur par défaut est à 1
   */
  public void changeZoom(double zoom) {
    if (zoomLabel != null) {
      zoomLabel.setText(ZOOM_LABEL + " (" + zoom + "x)");
    }
  }

  /**
   * Initialisation de l'ensemble des composants de la fenêtre
   */
  private void initComponents() {
    fileChooserBtn = new ChooseFileButton();
    uriFileLabel = new JLabel();
    uriFileText = new JTextField();
    actionBtn = new RunButton();
    drawingPane = new DrawingPane();
    logPane = new JScrollPane();
    logText = new JTextPane();
    zoomLabel = new JLabel();
    zoom = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
    zoomListener = new SliderListener();

    leftLabel = new JLabel();
    left = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
    topLabel = new JLabel();
    top = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

    setTitle(INTERFACE_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(INTERFACE_WIDTH, INTERFACE_HEIGHT));
    setPreferredSize(new Dimension(INTERFACE_WIDTH, INTERFACE_HEIGHT));

    uriFileLabel.setText(URI_FILE_LABEL);
    uriFileText.setFont(new Font("Cantarell", 0, 12));
    zoomLabel.setText(ZOOM_LABEL);
    leftLabel.setText(LEFT_LABEL);
    topLabel.setText(TOP_LABEL);

    zoom.addChangeListener(zoomListener);

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
          .addComponent(uriFileText,
            GroupLayout.DEFAULT_SIZE,
            200,
            200)
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
          .addComponent(logPane)
          .addComponent(zoomLabel,
            GroupLayout.DEFAULT_SIZE,
            200,
            200)
          .addComponent(zoom)
          .addComponent(topLabel,
            GroupLayout.DEFAULT_SIZE,
            200,
            200)
          .addComponent(top)
          .addComponent(leftLabel,
            GroupLayout.DEFAULT_SIZE,
            200,
            200)
          .addComponent(left))
        .addPreferredGap(ComponentPlacement.RELATED)
        .addComponent(drawingPane,
          GroupLayout.DEFAULT_SIZE,
          426,
          Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(Alignment.LEADING)
      .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
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
        .addComponent(zoomLabel)
        .addComponent(zoom)
        .addComponent(actionBtn,
          GroupLayout.PREFERRED_SIZE,
          60,
          GroupLayout.PREFERRED_SIZE)
        .addContainerGap()
        .addComponent(topLabel)
        .addComponent(top)
        .addComponent(leftLabel)
        .addComponent(left))
      .addComponent(drawingPane)
    );

    pack();
  }

  /**
   * Définit l'URL correspondant au fichier actuellement traité
   * @param url à traiter
   */
  public void setCurrentURI(String uri) {
    uriFileText.setText(uri);
  }

  /**
   * Récupérer l'url du SVG actuellement traité
   * @return url sous forme de chaine de caractères
   */
  public String getCurrentURI() {
    return uriFileText.getText();
  }

  /**
   * Empêche l'utilisateur d'interagir avec les éléments de la fenêtre
   */
  public void disableComponents() {
    fileChooserBtn.setEnabled(false);
    uriFileText.setEnabled(false);
    zoom.setEnabled(false);
    actionBtn.setText(">> Recommencer <<");
  }

  /**
   * Permet à l'utilisateur d'interagir avec les éléments de la fenêtre
   */
  public void enableComponents() {
    fileChooserBtn.setEnabled(true);
    uriFileText.setEnabled(true);
    zoom.setEnabled(true);
    actionBtn.setText(">> GO <<");
  }

}
