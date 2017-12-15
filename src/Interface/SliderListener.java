import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;


public class SliderListener implements ChangeListener {

  private Core core;

  /**
   * Constructeur du listener placé sur le curseur de zoom
   */
  public SliderListener() {
    core = Core.getInstance();
  }

  /**
   * Définit le comportement à chaque évolution du curseur de zoom
   * Ici, envoi de cette information au noyau (Core)
   */
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider) e.getSource();

    if (source.getValueIsAdjusting()) {
      double value = (double) source.getValue();
      double zoom = 1;
      if (value < 0) {
        zoom = (value + 100) / 100;
        if (zoom < 0.01) zoom = 0.01;
      } else if (value > 0) {
        zoom = value / 10;
        if (zoom < 1) zoom = 1;
      }
      core.debug("zoom=" + zoom);
      core.setZoom(zoom);
    }
  }
}
